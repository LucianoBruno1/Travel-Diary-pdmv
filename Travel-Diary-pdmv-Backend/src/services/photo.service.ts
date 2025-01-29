import { PhotoRepository } from "../repositories/photo.repository";
import { CreateAddPhotoToDiaryRequestDto, CreatePhotoRequestDto, PhotoResponseDto, PhotosArrayResponseDto } from "../dtos/photo/photo.dto";
import { Photo } from "../models/photo.model";
import { GeocodingService } from "./geocoding.service";
import { BadRequestError, NotFoundError } from "../helpers/api-erros";
import { DiaryService } from "./diary.service";
import { UserRepository } from "../repositories/user.repository";
import { Diary } from "../models/diary.model";

export class PhotoService {
    private photoRepository: PhotoRepository;
    private diaryService: DiaryService;
    private geocodingService: GeocodingService;
    private userRepository: UserRepository;

    constructor() {
        this.photoRepository = new PhotoRepository();
        this.diaryService = new DiaryService();
        
        this.userRepository = new UserRepository();
        this.geocodingService = new GeocodingService(process.env.GEOCODING_API_KEY);
    }

    async uploadPhoto(request: CreatePhotoRequestDto, id: string) {

        const { latitude, longitude, file_path, ...rest } = request.getAll();

        const user = await this.userRepository.findById(id);
        if (!user) {
            throw new BadRequestError(`Usuário com ID ${id} não foi encontrado.`);
        }

        const { city, state } = await this.geocodingService.getCityByCoordinates(latitude, longitude);

        const diary = await this.diaryService.createDiaryByPhoto(latitude, longitude, city, state, id);
        
        const photo = await this.savePhoto({ latitude, longitude, file_path, ...rest }, diary);
        return this.toPhotoResponseDto(photo);
    }

    async addPhotoToDiary(id: string, request: CreateAddPhotoToDiaryRequestDto): Promise<PhotosArrayResponseDto> {
        const { file_paths, diary: diaryId } = request.getAll();

        const user = await this.userRepository.findById(id);
        if (!user) {
            throw new BadRequestError(`Usuário com ID ${id} não foi encontrado.`);
        }
    
        // Validar se o diário existe
        const diary = await this.diaryService.findById(diaryId);
        if (!diary) {
            throw new NotFoundError(`Diário com ID ${diaryId} não foi encontrado.`);
        }

        const d = new Diary();
        Object.assign(d, diary);
    

        // Criar e salvar todas as fotos
        const photos = await Promise.all(
            file_paths.map(filePath => this.savePhoto({ file_path: filePath }, d))
        );
        
        return {
            photos: photos.map(photo => ({
                id: photo.id,
                created_at: photo.created_at,
                file_path: photo.file_path,
                diary: { id: photo.diary.id },
            })),
        };
    }

    private async savePhoto(data: Partial<Photo>, diary: Diary): Promise<Photo> {
        const photo = new Photo();
        Object.assign(photo, data, { diary });
        return this.photoRepository.save(photo);
    }

    private toPhotoResponseDto({ id, created_at, file_path, diary, latitude, longitude }: Photo): PhotoResponseDto {
        return { id, created_at, file_path, diary: { id: diary.id }, latitude, longitude };
    }

}
