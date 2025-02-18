import { Router, Request, Response, NextFunction } from "express";
import { DiaryController } from "../controllers/diary.controller";
import { authenticateJWT } from "../middlewares/authenticate-jwt";

const diaryRoutes = Router();
const diaryController = new DiaryController();

diaryRoutes.post("/diaries", diaryController.createManual.bind(diaryController));
diaryRoutes.get("/diaries/feed/:id", diaryController.getFeed.bind(diaryController));
diaryRoutes.get("/getfavorites/:id", diaryController.getDiaryById.bind(diaryController));
diaryRoutes.get('/diaries/:id', ((req: Request, res: Response, next: NextFunction) => {
         authenticateJWT(req, res, next)
     }), diaryController.getDiaryDetails.bind(diaryController));
diaryRoutes.delete('/diaries/:id', ((req: Request, res: Response, next: NextFunction) => {
         authenticateJWT(req, res, next)
     }), diaryController.remove.bind(diaryController));

diaryRoutes.patch('/diaries/:id', ((req: Request, res: Response, next: NextFunction) => {
        authenticateJWT(req, res, next)
    }), diaryController.update.bind(diaryController));

export default diaryRoutes;
