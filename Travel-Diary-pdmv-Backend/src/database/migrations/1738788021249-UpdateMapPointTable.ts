import { MigrationInterface, QueryRunner } from "typeorm";

export class UpdateMapPointTable1738788021249 implements MigrationInterface {
    name = 'UpdateMapPointTable1738788021249'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`map_point\` ADD \`user_id\` varchar(255) NULL`);
        await queryRunner.query(`ALTER TABLE \`map_point\` ADD CONSTRAINT \`FK_9c5403e41ad6fb87a5dc56aeb09\` FOREIGN KEY (\`user_id\`) REFERENCES \`user\`(\`id\`) ON DELETE NO ACTION ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`map_point\` DROP FOREIGN KEY \`FK_9c5403e41ad6fb87a5dc56aeb09\``);
        await queryRunner.query(`ALTER TABLE \`map_point\` DROP COLUMN \`user_id\``);
    }

}
