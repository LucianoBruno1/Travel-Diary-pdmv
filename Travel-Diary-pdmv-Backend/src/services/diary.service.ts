import { CreateDiaryRequestDto, DiaryResponseDto } from "../dtos/diary/diary.dto";
import { BadRequestError, NotFoundError } from "../helpers/api-erros";
import { Diary } from "../models/diary.model";
import { DiaryRepository } from "../repositories/diary.repository";
import { UserRepository } from "../repositories/user.repository";
import { GeocodingService } from "./geocoding.service";

export class DiaryService {
    private diaryRepository: DiaryRepository;
    private userRepository: UserRepository;
    private geocodingService: GeocodingService;
    

    constructor() {
        this.diaryRepository = new DiaryRepository();
        this.userRepository = new UserRepository();
        this.geocodingService = new GeocodingService(process.env.GEOCODING_API_KEY);
    }

    async getDiaryWithPhotos(diaryId: string): Promise<Diary> {
        const diary = await this.diaryRepository.findDiaryWithPhotos(diaryId);
    
        if (!diary) {
            throw new NotFoundError(`Diário com ID ${diaryId} não foi encontrado.`);
        }
    
        return diary;
    }

     /**
     * Retorna todos os diários de um usuário
     * @param userId ID do usuário
     */
     async getDiariesForUser(userId: string) {
        const diaries = await this.diaryRepository.findAllByUserId(userId);
        if (diaries.length === 0) {
            throw new NotFoundError("Nenhum diário encontrado para este usuário.");
        }
        return diaries;
    }

    async createDiaryByPhoto(latitude: number, longitude: number, city: string, state: string, id: string): Promise<Diary> {

        const existingDiary = await this.diaryRepository.findByStateAndUser(state, id);
        if (existingDiary) {
            return existingDiary
        }

        const user = await this.userRepository.findById(id);
        if (!user) {
            throw new BadRequestError(`Usuário com ID ${id} não foi encontrado.`);
        }

        // Definir o nome do diário
        const diaryName = `Viagem_à_${state}`;

        const diary = new Diary();
        Object.assign(diary, { city, state, latitude, longitude });
        diary.name = diaryName;
        diary.user = user;
        diary.travel_date = new Date();
        const savedDiary = await this.diaryRepository.save(diary);
        return savedDiary;
    }

    async createManualDiary(request: CreateDiaryRequestDto): Promise<DiaryResponseDto> {
        const { id: userId, latitude, longitude, name, description, travel_date } = request.getAll();
    
        const user = await this.userRepository.findById(userId);
        if (!user) {
            throw new BadRequestError(`Usuário com ID ${userId} não foi encontrado.`);
        }
    
        // Calcular cidade e estado com base na latitude e longitude
        const { city, state } = await this.geocodingService.getCityByCoordinates(latitude, longitude);

        const existingDiary = await this.diaryRepository.findByStateAndUser (state, userId);
        if (existingDiary) {
            if (!existingDiary.id || !existingDiary.user || !existingDiary.photos) {
                throw new Error("Diário existente está incompleto. Verifique o carregamento das relações.");
            }
            console.log(`Diário já criado para o estado ${state}`)
            return this.toDiaryResponseDto(existingDiary)
        }
    
        // Criar o nome do diário se não for fornecido
        const diaryName = name || `Viagem_à_${state}`;

        const diary = new Diary();
        Object.assign(diary, {
            name: diaryName,
            description: description || null,
            travel_date: travel_date ? new Date(travel_date) : new Date(), // Data atual se não fornecida
            city,
            state,
            latitude,
            longitude,
            user,
        });

        const savedDiary = await this.diaryRepository.save(diary);
        return this.toDiaryResponseDto(savedDiary);
    }

    async findById(id: string) {
        const diary = await this.diaryRepository.findById(id);
        if (!diary) throw new NotFoundError(`Diário com ID ${id} não encontrado`);
        return this.toDiaryResponseDto(diary);
    }


    private toDiaryResponseDto(diary: Diary): DiaryResponseDto {
        const { id = null,
            name = null,
            description = null,
            travel_date = null,
            city = null,
            state = null,
            latitude = null,
            longitude = null, user, photos = [] } = diary;

        return {
            id,
            name,
            description,
            travel_date,
            city,
            state,
            latitude,
            longitude,
            user: { id: user.id },
            photos: Array.isArray(photos) ? photos.map((photo) => ({
                id: photo.id || null,
                file_path: photo.file_path || null,
            })) : [],
        };
    }
}
