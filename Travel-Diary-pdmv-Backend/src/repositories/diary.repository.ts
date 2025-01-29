import { DataSource } from "typeorm";
import { AppDataSource } from "../database/data-source";
import { Diary } from "../models/diary.model";

export class DiaryRepository {
    private dataSource: DataSource;

    constructor() {
        this.dataSource = AppDataSource;
    }

    async save(diaryData: Partial<Diary>): Promise<Diary> {
        const diaryRepository = this.dataSource.getRepository(Diary);
        const diary = diaryRepository.create(diaryData);
        return await diaryRepository.save(diary);
    }

    async findById(id: string): Promise<Diary | null> {
        const diaryRepository = this.dataSource.getRepository(Diary);
        return await diaryRepository.findOne({
            where: { id },
            relations:  ["photos", "user"], // Inclui as fotos relacionadas
        });
    }

    async findByStateAndUser(state: string, userId: string): Promise<Diary | null> {
        const diaryRepository = this.dataSource.getRepository(Diary);
        return await diaryRepository.findOne({
            where: { state, user: { id: userId } },
            relations: ["user", "photos"], // Adicione as relações necessárias
        });
    }
}
