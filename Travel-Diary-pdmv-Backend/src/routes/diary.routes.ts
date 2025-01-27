import { Router } from "express";
import { DiaryController } from "../controllers/diary.controller";

const diaryRoutes = Router();
const diaryController = new DiaryController();

diaryRoutes.post("/diaries", diaryController.createManual.bind(diaryController));

export default diaryRoutes;
