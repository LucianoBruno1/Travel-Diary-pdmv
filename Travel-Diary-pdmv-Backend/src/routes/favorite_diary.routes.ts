import { Router } from "express";
import { FavoriteDiaryController } from "../controllers/favorite_diary.controller";

const favoriteDiaryRoutes = Router();
const favoriteDiaryController = new FavoriteDiaryController();

favoriteDiaryRoutes.get('/favorites/:userId', favoriteDiaryController.getFavorites.bind(favoriteDiaryController));
favoriteDiaryRoutes.post('/favorites', favoriteDiaryController.addFavorite.bind(favoriteDiaryController));
favoriteDiaryRoutes.delete('/favorites/:diaryId/:userId', favoriteDiaryController.removeFavorite.bind(favoriteDiaryController));

export default favoriteDiaryRoutes;
