import { NextFunction, Request, Response } from "express";
import { BaseController } from "./base/base.controller";
import { FavoriteDiaryService } from "../services/favorite_diary.service";
import { CreateFavoriteDiaryRequestDto } from "../dtos/favorite_diary/favorite_diary.dto";

export class FavoriteDiaryController extends BaseController<FavoriteDiaryService> {
    constructor() {
        super(new FavoriteDiaryService());
    }

    async getFavorites(req: Request, res: Response, next: NextFunction): Promise<void> {
        const { id } = req.params;

        return this.handleRequest(req, res, next, async () => await this.service.getFavoritesByUser(id), "Sucesso", 200);
    }

    async addFavorite(req: Request, res: Response, next: NextFunction): Promise<void> {

         const dto = new CreateFavoriteDiaryRequestDto({
                    ...req.body
        });

        console.log("Recebendo requisição para favoritar:", req.body);

        return this.handleRequest(req, res, next, async () => await this.service.addFavorite(dto), "Adicionado com sucesso", 201);
    }

    async removeFavorite(req: Request, res: Response, next: NextFunction): Promise<void> {
        const { userId, diaryId } = req.params;

        return this.handleRequest(req, res, next, async () => {
            await this.service.removeFavorite(userId, diaryId);
            return { message: "Removido com sucesso" };
        }, "Removido com sucesso", 200);
    }
}
