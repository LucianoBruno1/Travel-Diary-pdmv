import { Column, Entity, OneToMany } from "typeorm";
import { BaseEntity } from "./base.model"
import { Diary } from "./diary.model";

@Entity("user")
export class User extends BaseEntity {

    @Column({ type: 'varchar', length: 255 })
    name: string;

    @Column({ type: 'varchar', length: 100, unique: true })
    email: string;
    
    @Column({ type: "varchar" })
    password: string;

    @Column({ type: 'varchar', nullable: true })
    profilePicture?: string; // Agora é opcional

    @Column({ type: "date", nullable: true })
    birthDate?: Date; // Agora é opcional

    @Column({ type: 'varchar', length: 500, nullable: true })
    bio?: string; // Agora é opcional e tem um limite de caracteres

    @Column({ type: 'varchar', nullable: true })
    reset_token?: string;  // Token de redefinição de senha

    @Column({ type: 'datetime', nullable: true })
    token_expiration?: Date; // Data de expiração do token

    @OneToMany(() => Diary, (diary) => diary.user)
    diaries: Diary[];
}