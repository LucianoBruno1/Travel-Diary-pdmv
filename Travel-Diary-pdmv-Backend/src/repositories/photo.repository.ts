import { DataSource } from "typeorm";
import { AppDataSource } from "../database/data-source";
import { Photo } from "../models/photo.model";

export class PhotoRepository {
    private dataSource: DataSource;

    constructor() {
        this.dataSource = AppDataSource;
    }

    async save(photoData: Partial<Photo>): Promise<Photo> {
        const photoRepository = this.dataSource.getRepository(Photo);
        const photo = photoRepository.create(photoData);
        return await photoRepository.save(photo);
    }
}
