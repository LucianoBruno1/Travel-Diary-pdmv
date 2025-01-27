import { NextFunction, Request, Response } from "express";
import { BaseController } from "./base/base.controller";
import { DiaryService } from "../services/diary.service";
import { CreateDiaryRequestDto } from "../dtos/diary/diary.dto";

export class DiaryController extends BaseController<DiaryService> {
    constructor() {
        super(new DiaryService());
    }

    async createManual(req: Request, res: Response, next: NextFunction): Promise<void> {
        const dto = new CreateDiaryRequestDto({
            ...req.body,
        });

        return this.handleRequest(
            req,
            res,
            next,
            async () => this.service.createManualDiary(dto),
            "Diário criado com sucesso!",
            201
        );
    }
}
