import { CreateDateColumn, PrimaryColumn } from "typeorm";
import { v4 as uuid } from 'uuid';

export class BaseEntity {

    @PrimaryColumn()
    id: string;

    @CreateDateColumn({ type: "datetime" })
    created_at: Date;

    constructor() {
        if (!this.id) {
            this.id = uuid();
        }
    }
}