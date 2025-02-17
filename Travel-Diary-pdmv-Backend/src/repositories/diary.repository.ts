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

    /**
     * Busca todos os diários associados a um usuário
     * @param userId ID do usuário
     * @returns Lista de diários
     */
    async findAllByUserId(userId: string): Promise<Diary[]> {
        const diaryRepository = this.dataSource.getRepository(Diary);
        return await diaryRepository.find({
            where: { user: { id: userId } },
            order: { created_at: "DESC" }, // Ordena por data de criação, mais recente primeiro
        });
    }

    async findDiaryWithPhotos(diaryId: string): Promise<Diary | null> {
        const diaryRepository = this.dataSource.getRepository(Diary);
        return await diaryRepository.findOne({
            where: { id: diaryId },
            relations: ['photos', 'user'],
        });
    }

    async remove(id: string) {
        return this.dataSource.getRepository(Diary).delete(id);
    }

    async update(id: string, diary: Diary) {
        const queryRunner = this.dataSource.createQueryRunner();
        await queryRunner.connect();
        await queryRunner.startTransaction();

        try{
            const { photos, ...diaryWithoutPhotos } = diary;

            await queryRunner.manager.update(Diary, id, diaryWithoutPhotos);

            const updatedDiary = await queryRunner.manager.findOne(Diary, {
                where: { id },
                relations: ["user"], 
            });

            await queryRunner.commitTransaction();
            return updatedDiary;
        } catch(error){
            await queryRunner.rollbackTransaction();
            throw error;
        } finally{
            await queryRunner.release();
        }
    }

}
