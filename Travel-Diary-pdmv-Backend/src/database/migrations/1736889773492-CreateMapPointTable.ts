import { MigrationInterface, QueryRunner } from "typeorm";

export class CreateMapPointTable1736889773492 implements MigrationInterface {
    name = 'CreateMapPointTable1736889773492'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE \`map_point\` (\`id\` varchar(255) NOT NULL, \`created_at\` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6), \`latitude\` double NOT NULL, \`longitude\` double NOT NULL, \`diary_id\` varchar(255) NULL, PRIMARY KEY (\`id\`)) ENGINE=InnoDB`);
        await queryRunner.query(`ALTER TABLE \`map_point\` ADD CONSTRAINT \`FK_c28ae7563cc02627501ecc21268\` FOREIGN KEY (\`diary_id\`) REFERENCES \`diario\`(\`id\`) ON DELETE NO ACTION ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`map_point\` DROP FOREIGN KEY \`FK_c28ae7563cc02627501ecc21268\``);
        await queryRunner.query(`DROP TABLE \`map_point\``);
    }

}
