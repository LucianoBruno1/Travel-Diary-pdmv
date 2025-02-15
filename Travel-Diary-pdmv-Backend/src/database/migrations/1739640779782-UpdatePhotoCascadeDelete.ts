import { MigrationInterface, QueryRunner } from "typeorm";

export class UpdatePhotoCascadeDelete1739640779782 implements MigrationInterface {
    name = 'UpdatePhotoCascadeDelete1739640779782'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`photo\` DROP FOREIGN KEY \`FK_471e2254ef41025b18fad85d46d\``);
        await queryRunner.query(`ALTER TABLE \`photo\` ADD CONSTRAINT \`FK_471e2254ef41025b18fad85d46d\` FOREIGN KEY (\`diary_id\`) REFERENCES \`diario\`(\`id\`) ON DELETE CASCADE ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`photo\` DROP FOREIGN KEY \`FK_471e2254ef41025b18fad85d46d\``);
        await queryRunner.query(`ALTER TABLE \`photo\` ADD CONSTRAINT \`FK_471e2254ef41025b18fad85d46d\` FOREIGN KEY (\`diary_id\`) REFERENCES \`diario\`(\`id\`) ON DELETE NO ACTION ON UPDATE NO ACTION`);
    }

}
