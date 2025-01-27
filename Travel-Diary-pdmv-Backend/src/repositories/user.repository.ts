import { DataSource } from "typeorm";
import { AppDataSource } from "../database/data-source";
import { User } from "../models/user.model";

export class UserRepository {
    private dataSource: DataSource;

    constructor() {
        this.dataSource = AppDataSource;
    }

    async findByToken(token: string): Promise<User | null> {
        return this.dataSource.getRepository(User).findOne({ where: { reset_token: token } });
    }

    async save(user: User): Promise<User> {
        return this.dataSource.getRepository(User).save(user);
    }

    async create(user: User) {
        const queryRunner = this.dataSource.createQueryRunner();
        await queryRunner.connect();
        await queryRunner.startTransaction();

        try {
            const createdUser = await queryRunner.manager.save(User, user);
            await queryRunner.commitTransaction();
            return createdUser;
        } catch (error) {
            await queryRunner.rollbackTransaction();
            throw error;
        } finally {
            await queryRunner.release();
        }
    }

    async update(id: string, user: Partial<User>) {
        const queryRunner = this.dataSource.createQueryRunner();
        await queryRunner.connect();
        await queryRunner.startTransaction();

        try {
            await queryRunner.manager.update(User, id, user);
            await queryRunner.commitTransaction();
            return this.findById(id);
        } catch (error) {
            await queryRunner.rollbackTransaction();
            throw error;
        } finally {
            await queryRunner.release();
        }
    }

    async findAll() {
        return this.dataSource.getRepository(User).find();
    }

    async findOne(email: string) {
        return this.dataSource.getRepository(User).findOne({ where: { email } });
    }

    async findById(id: string) {
        return this.dataSource.getRepository(User).findOne({ where: { id } });
    }

    async remove(id: string) {
        const queryRunner = this.dataSource.createQueryRunner();
        await queryRunner.connect();
        await queryRunner.startTransaction();

        try {
            await queryRunner.manager.delete(User, id);
            await queryRunner.commitTransaction();
        } catch (error) {
            await queryRunner.rollbackTransaction();
            throw error;
        } finally {
            await queryRunner.release();
        }
    }
}
