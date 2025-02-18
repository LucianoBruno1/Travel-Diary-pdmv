import { MigrationInterface, QueryRunner } from "typeorm";

export class CreateTableFavoriteDiary1739837770768 implements MigrationInterface {
    name = 'CreateTableFavoriteDiary1739837770768'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE \`favorite_diaries\` (\`id\` varchar(255) NOT NULL, \`created_at\` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6), \`user_id\` varchar(255) NULL, \`diary_id\` varchar(255) NULL, PRIMARY KEY (\`id\`)) ENGINE=InnoDB`);
        await queryRunner.query(`ALTER TABLE \`favorite_diaries\` ADD CONSTRAINT \`FK_e6165dee55b66765017571f4580\` FOREIGN KEY (\`user_id\`) REFERENCES \`user\`(\`id\`) ON DELETE CASCADE ON UPDATE NO ACTION`);
        await queryRunner.query(`ALTER TABLE \`favorite_diaries\` ADD CONSTRAINT \`FK_af0c96c09439d0023b03e73b635\` FOREIGN KEY (\`diary_id\`) REFERENCES \`diario\`(\`id\`) ON DELETE CASCADE ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`favorite_diaries\` DROP FOREIGN KEY \`FK_af0c96c09439d0023b03e73b635\``);
        await queryRunner.query(`ALTER TABLE \`favorite_diaries\` DROP FOREIGN KEY \`FK_e6165dee55b66765017571f4580\``);
        await queryRunner.query(`DROP TABLE \`favorite_diaries\``);
    }

}
