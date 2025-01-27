import { MigrationInterface, QueryRunner } from "typeorm";

export class CreateDiaryTable1736889688462 implements MigrationInterface {
    name = 'CreateDiaryTable1736889688462'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`CREATE TABLE \`diario\` (\`id\` varchar(255) NOT NULL, \`created_at\` datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6), \`name\` varchar(255) NOT NULL, \`description\` text NULL, \`travel_date\` date NULL, \`city\` varchar(255) NOT NULL, \`latitude\` double NOT NULL, \`longitude\` double NOT NULL, \`user_id\` varchar(255) NULL, PRIMARY KEY (\`id\`)) ENGINE=InnoDB`);
        await queryRunner.query(`ALTER TABLE \`diario\` ADD CONSTRAINT \`FK_b0b02d2ef1dc49f39ada04013fc\` FOREIGN KEY (\`user_id\`) REFERENCES \`user\`(\`id\`) ON DELETE NO ACTION ON UPDATE NO ACTION`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`diario\` DROP FOREIGN KEY \`FK_b0b02d2ef1dc49f39ada04013fc\``);
        await queryRunner.query(`DROP TABLE \`diario\``);
    }

}
