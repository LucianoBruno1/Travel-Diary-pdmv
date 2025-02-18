import { DataSource } from "typeorm";
import { AppDataSource } from "../database/data-source";
import { FavoriteDiary } from "../models/favorite_diaries.model";


export class FavoriteDiaryRepository {
    private dataSource: DataSource;

    constructor() {
        this.dataSource = AppDataSource;
    }

    async save(favoriteData: Partial<FavoriteDiary>): Promise<FavoriteDiary> {
        const favoriteRepository = this.dataSource.getRepository(FavoriteDiary);
        const favorite = favoriteRepository.create(favoriteData);
        return await favoriteRepository.save(favorite);
    }

    async findByUser(userId: string): Promise<FavoriteDiary[]> {
        const favoriteRepository = this.dataSource.getRepository(FavoriteDiary);
        return await favoriteRepository.find({
            where: { user: { id: userId } },
            relations: ["diary"],
        });
    }

    async deleteFavorite(userId: string, diaryId: string): Promise<void> {
        const favoriteRepository = this.dataSource.getRepository(FavoriteDiary);
        await favoriteRepository.delete({ user: { id: userId }, diary: { id: diaryId } });
    }
}
