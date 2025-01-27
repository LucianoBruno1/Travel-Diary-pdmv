import { PhotoRepository } from "../repositories/photo.repository";
import { CreatePhotoRequestDto, PhotoResponseDto } from "../dtos/photo/photo.dto";
import { Photo } from "../models/photo.model";
import { GeocodingService } from "./geocoding.service";
import { BadRequestError } from "../helpers/api-erros";
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

        const { latitude, longitude, file_path, diary: diaryId, ...rest } = request.getAll();

        const user = await this.userRepository.findById(id);
        if (!user) {
            throw new BadRequestError(`Usuário com ID ${id} não foi encontrado.`);
        }

        // 1. Obter cidade e estado pelas coordenadas
        const { city, state } = await this.geocodingService.getCityByCoordinates(latitude, longitude);

        let diary;
        if (!diaryId) {
            // 2a. Verificar se o diário fornecido existe
            diary = await this.diaryService.createDiaryByPhoto(latitude, longitude, city, state, id);
            if (!diary) {
                throw new BadRequestError("Diário fornecido não existe.");
            }
        } else {
            throw new Error("Lógica para diário específico ainda não implementada.");
        }
        
        const photo = await this.savePhoto({ latitude, longitude, file_path, ...rest }, diary);
        return this.toPhotoResponseDto(photo);
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
