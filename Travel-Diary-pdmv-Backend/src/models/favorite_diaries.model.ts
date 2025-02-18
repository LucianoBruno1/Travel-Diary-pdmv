import { Entity, JoinColumn, ManyToOne } from "typeorm";
import { User } from "./user.model";
import { Diary } from "./diary.model";
import { BaseEntity } from "./base.model";

@Entity("favorite_diaries")
export class FavoriteDiary extends BaseEntity {

    @ManyToOne(() => User, { onDelete: "CASCADE" })
    @JoinColumn({ name: "user_id" })
    user: User;

    @ManyToOne(() => Diary, { onDelete: "CASCADE" })
    @JoinColumn({ name: "diary_id" })
    diary: Diary;
}
