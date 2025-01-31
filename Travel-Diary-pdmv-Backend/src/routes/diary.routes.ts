import { Router } from "express";
import { DiaryController } from "../controllers/diary.controller";

const diaryRoutes = Router();
const diaryController = new DiaryController();

diaryRoutes.post("/diaries", diaryController.createManual.bind(diaryController));
diaryRoutes.get("/diaries/feed/:id", diaryController.getFeed.bind(diaryController));
diaryRoutes.get('/diaries/:id', diaryController.getDiaryDetails.bind(diaryController));

export default diaryRoutes;
