import { MigrationInterface, QueryRunner } from "typeorm";

export class UpdateUsersTable1738360654147 implements MigrationInterface {
    name = 'UpdateUsersTable1738360654147'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`user\` ADD \`password\` varchar(255) NOT NULL`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`user\` DROP COLUMN \`password\``);
    }

}
