import { MigrationInterface, QueryRunner } from "typeorm";

export class AddLatLonToPhotoAndArrayPhothosToDiary1737506409142 implements MigrationInterface {
    name = 'AddLatLonToPhotoAndArrayPhothosToDiary1737506409142'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`photo\` ADD \`latitude\` double NULL`);
        await queryRunner.query(`ALTER TABLE \`photo\` ADD \`longitude\` double NULL`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`photo\` DROP COLUMN \`longitude\``);
        await queryRunner.query(`ALTER TABLE \`photo\` DROP COLUMN \`latitude\``);
    }

}
