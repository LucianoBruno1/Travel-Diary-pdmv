import { NextFunction, Request, Response } from "express";
import { BaseController } from "./base/base.controller";
import { DiaryService } from "../services/diary.service";
import { CreateDiaryRequestDto } from "../dtos/diary/diary.dto";
import { UpdateDiaryRequestDto } from "../dtos/diary/diary-update.dto";

export class DiaryController extends BaseController<DiaryService> {
    constructor() {
        super(new DiaryService());
    }

    async getDiaryDetails(req: Request, res: Response, next: NextFunction): Promise<void> {
        const { id } = req.params;
    
        return this.handleRequest(req, res, next, async () => await this.service.getDiaryWithPhotos(id), "Sucesso", 200);
    }

    async getFeed(req: Request, res: Response, next: NextFunction) {
        const { id } = req.params;
        
        return this.handleRequest(req, res, next, async () => this.service.getDiariesForUser(id), "Sucesso", 200);
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

    async remove(req: Request, res: Response, next: NextFunction) {
        const id = req.params.id;
        return this.handleRequest(req, res, next, async () => {
            await this.service.remove(id);
        }, "Diário removido com sucesso", 200);
    }

    async update(req: Request, res: Response, next: NextFunction) {
        const dto = new UpdateDiaryRequestDto({
            ...req.body
        });
        const id = req.params.id;
        return this.handleRequest(req, res, next, async () => this.service.update(id, dto), "Diário atualizado com sucesso", 200);
    }

}
