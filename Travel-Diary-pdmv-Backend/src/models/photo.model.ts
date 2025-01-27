import { Column, Entity, JoinColumn, ManyToOne } from "typeorm";
import { BaseEntity } from "./base.model";
import { Diary } from "./diary.model";

@Entity("photo")
export class Photo extends BaseEntity {

    @Column({ type: 'varchar' })
    file_path: string;

    @Column({ type: "double", nullable: true })
    latitude: number;

    @Column({ type: "double", nullable: true })
    longitude: number;

    @ManyToOne(() => Diary, (diary) => diary.photos)
    @JoinColumn({ name: "diary_id" })
    diary: Diary;
} 