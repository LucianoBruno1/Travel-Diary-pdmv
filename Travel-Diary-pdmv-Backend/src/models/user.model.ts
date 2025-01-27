import { Column, Entity } from "typeorm";
import { BaseEntity } from "./base.model"

@Entity("user")
export class User extends BaseEntity {

    @Column({ type: 'varchar', length: 255 })
    name: string;

    @Column({ type: 'varchar', length: 100, unique: true })
    email: string;
    
    @Column({ type: "varchar" })
    password: string;

    @Column({ type: 'varchar', nullable: true })
    reset_token?: string;  // Token de redefinição de senha

    @Column({ type: 'datetime', nullable: true })
    token_expiration?: Date; // Data de expiração do token
}