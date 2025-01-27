import { MigrationInterface, QueryRunner } from "typeorm";

export class CreatePhotoTable1736889737721 implements MigrationInterface {
    name = 'CreatePhotoTable1736889737721'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE \`photo\` (\`id\` varchar(255) NOT NULL, \`created_at\` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6), \`file_path\` varchar(255) NOT NULL, \`diary_id\` varchar(255) NULL, PRIMARY KEY (\`id\`)) ENGINE=InnoDB`);
        await queryRunner.query(`ALTER TABLE \`photo\` ADD CONSTRAINT \`FK_471e2254ef41025b18fad85d46d\` FOREIGN KEY (\`diary_id\`) REFERENCES \`diario\`(\`id\`) ON DELETE NO ACTION ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`photo\` DROP FOREIGN KEY \`FK_471e2254ef41025b18fad85d46d\``);
        await queryRunner.query(`DROP TABLE \`photo\``);
    }

}
