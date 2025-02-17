import { MapPointRepository } from "../repositories/map_point.repository";
import { MapPointResponseDto } from "../dtos/map_point/map_point.dto";
import { MapPoint } from "../models/map_point.model";
import { User } from "../models/user.model";
import { Diary } from "../models/diary.model";

export class MapPointService {
    private mapPointRepository: MapPointRepository;

    constructor() {
        this.mapPointRepository = new MapPointRepository();
    }

    async getMapPointsByUser(userId: string): Promise<MapPointResponseDto[]> {
        const mapPoints = await this.mapPointRepository.findByUser(userId);

        return mapPoints.map((point) => ({
            id: point.id,
            latitude: point.latitude,
            longitude: point.longitude,
            diary: { id: point.diary.id, name: point.diary.name },
        }));
    }

    async create(latitude: number, longitude: number, user: User, diary: Diary): Promise<MapPoint> {
        try {
            const mapPoint = new MapPoint();
            mapPoint.latitude = latitude;
            mapPoint.longitude = longitude;
            mapPoint.user = user;
            mapPoint.diary = diary;

             // Garantir que o ID do MapPoint é tratado como novo
            if (mapPoint.id) {
                mapPoint.id = undefined; // Forçar como novo (sem ID existente)
            }
    
            console.log("Tentando salvar MapPoint:", mapPoint);
    
            const savedMapPoint = await this.mapPointRepository.save(mapPoint);
            
            console.log("MapPoint salvo com sucesso:", savedMapPoint);
            return savedMapPoint;
        } catch (error) {
            console.error("Erro ao salvar MapPoint:", error);
            throw error;
        }
    }
}
