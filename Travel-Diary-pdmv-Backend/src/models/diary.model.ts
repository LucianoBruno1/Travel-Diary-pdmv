import { Column, Entity, Index, JoinColumn, ManyToOne, OneToMany } from "typeorm";
import { BaseEntity } from "./base.model";
import { User } from "./user.model";
import { Photo } from "./photo.model";
import { MapPoint } from "./map_point.model";

@Entity("diario")
@Index(["user", "city", "travel_date"]) // Índices para consultas frequentes
export class Diary extends BaseEntity {
    @Column({ type: 'varchar', length: 255 })
    name: string;

    @Column({ type: "text", nullable: true })
    description: string;

    @Column({ type: "date", nullable: true })
    travel_date: Date;

    @Column({ type: 'varchar'})
    city: string;

    @Column({ type: "varchar", length: 255 })
    state: string;

    @Column({ type: "double" })
    latitude: number;

    @Column({ type: "double" })
    longitude: number;

    @ManyToOne(() => User)
    @JoinColumn({ name: "user_id" })
    user: User;

    @OneToMany(() => Photo, (photo) => photo.diary, { cascade: true })
    photos: Photo[];

    @OneToMany(() => MapPoint, (mapPoint) => mapPoint.diary, { cascade: ["insert", "remove"] })
    mapPoints: MapPoint[];
}