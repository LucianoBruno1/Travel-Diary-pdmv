import { Column, Entity, JoinColumn, ManyToOne } from "typeorm";
import { BaseEntity } from "./base.model";
import { Diary } from "./diary.model";
import { User } from "./user.model";

@Entity("map_point")
export class MapPoint extends BaseEntity {

    @Column({ type: "double" })
    latitude: number;

    @Column({ type: "double" })
    longitude: number;

    @ManyToOne(() => Diary)
    @JoinColumn({ name: "diary_id" })
    diary: Diary;

    @ManyToOne(() => User)
    @JoinColumn({ name: "user_id" })
    user: User;
}