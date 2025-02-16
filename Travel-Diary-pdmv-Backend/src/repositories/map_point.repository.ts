import { DataSource } from "typeorm";
import { AppDataSource } from "../database/data-source";
import { MapPoint } from "../models/map_point.model";
import { BadRequestError } from "../helpers/api-erros";

export class MapPointRepository {
    private dataSource: DataSource;

    constructor() {
        this.dataSource = AppDataSource;
    }

    async save(pointData: Partial<MapPoint>): Promise<MapPoint> {
        const mapPointRepository = this.dataSource.getRepository(MapPoint);
        const mapPoint = mapPointRepository.create(pointData);
        if (!mapPoint) throw new BadRequestError("Erro ao carregar MapPoint.");
        return await mapPointRepository.save(mapPoint);
    }

    async findByUser(userId: string): Promise<MapPoint[]> {
        const mapPointRepository = this.dataSource.getRepository(MapPoint);
        return await mapPointRepository.find({
            where: { user: { id: userId } },
            relations: ["diary"],
        });
    }
}
