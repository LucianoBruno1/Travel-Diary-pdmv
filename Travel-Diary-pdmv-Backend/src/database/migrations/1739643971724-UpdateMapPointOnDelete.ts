import { MigrationInterface, QueryRunner } from "typeorm";

export class UpdateMapPointOnDelete1739643971724 implements MigrationInterface {
    name = 'UpdateMapPointOnDelete1739643971724'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`map_point\` DROP FOREIGN KEY \`FK_c28ae7563cc02627501ecc21268\``);
        await queryRunner.query(`ALTER TABLE \`map_point\` ADD CONSTRAINT \`FK_c28ae7563cc02627501ecc21268\` FOREIGN KEY (\`diary_id\`) REFERENCES \`diario\`(\`id\`) ON DELETE CASCADE ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`map_point\` DROP FOREIGN KEY \`FK_c28ae7563cc02627501ecc21268\``);
        await queryRunner.query(`ALTER TABLE \`map_point\` ADD CONSTRAINT \`FK_c28ae7563cc02627501ecc21268\` FOREIGN KEY (\`diary_id\`) REFERENCES \`diario\`(\`id\`) ON DELETE NO ACTION ON UPDATE NO ACTION`);
    }

}
