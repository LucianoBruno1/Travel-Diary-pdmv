import { MigrationInterface, QueryRunner } from "typeorm";

export class UpdateDiary1737866151862 implements MigrationInterface {
    name = 'UpdateDiary1737866151862'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`diario\` ADD \`state\` varchar(255) NOT NULL`);
        await queryRunner.query(`CREATE INDEX \`IDX_e43fc452c28c08be9dccfcb6c2\` ON \`diario\` (\`user_id\`, \`city\`, \`travel_date\`)`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`DROP INDEX \`IDX_e43fc452c28c08be9dccfcb6c2\` ON \`diario\``);
        await queryRunner.query(`ALTER TABLE \`diario\` DROP COLUMN \`state\``);
    }

}
