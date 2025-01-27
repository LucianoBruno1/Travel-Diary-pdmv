import { MigrationInterface, QueryRunner } from "typeorm";

export class AddResetTokenToUser1737156956126 implements MigrationInterface {
    name = 'AddResetTokenToUser1737156956126'

    public async up(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`user\` ADD \`reset_token\` varchar(255) NULL`);
        await queryRunner.query(`ALTER TABLE \`user\` ADD \`token_expiration\` datetime NULL`);
    }

    public async down(queryRunner: QueryRunner): Promise<void> {
        await queryRunner.query(`ALTER TABLE \`user\` DROP COLUMN \`token_expiration\``);
        await queryRunner.query(`ALTER TABLE \`user\` DROP COLUMN \`reset_token\``);
    }

}
