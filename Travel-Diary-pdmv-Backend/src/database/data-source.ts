import "reflect-metadata";
import { DataSource } from "typeorm";
import * as dotenv from "dotenv";
import { User } from "../models/user.model";
import { Diary } from "../models/diary.model";
import { Photo } from "../models/photo.model";
import { MapPoint } from "../models/map_point.model";

dotenv.config();

const { DB_HOST, DB_PORT, DB_USERNAME, DB_PASSWORD, DB_DATABASE, NODE_ENV } = process.env;

export const AppDataSource = new DataSource({
    type: "mysql",
    host: DB_HOST,
    port: parseInt(DB_PORT || "3306"),
    username: DB_USERNAME,
    password: DB_PASSWORD,
    database: DB_DATABASE,

    // Somente use synchronize em ambiente de desenvolvimento, desabilite em produção
    synchronize: false,
    logging: NODE_ENV === "dev" ? true : false,
    entities: [User, Diary, Photo, MapPoint],
    migrations: ["src/database/migrations/*.ts"],
    subscribers: []
});