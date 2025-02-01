import { MigrationInterface, QueryRunner } from "typeorm";

export class UpdateUsersTable1738360527627 implements MigrationInterface {
    name = 'UpdateUsersTable1738360527627'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`user\` DROP COLUMN \`password\``);
        await queryRunner.query(`ALTER TABLE \`user\` ADD \`profilePicture\` varchar(255) NULL`);
        await queryRunner.query(`ALTER TABLE \`user\` ADD \`birthDate\` date NULL`);
        await queryRunner.query(`ALTER TABLE \`user\` ADD \`bio\` varchar(500) NULL`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`user\` DROP COLUMN \`bio\``);
        await queryRunner.query(`ALTER TABLE \`user\` DROP COLUMN \`birthDate\``);
        await queryRunner.query(`ALTER TABLE \`user\` DROP COLUMN \`profilePicture\``);
        await queryRunner.query(`ALTER TABLE \`user\` ADD \`password\` varchar(255) NOT NULL`);
    }

}
