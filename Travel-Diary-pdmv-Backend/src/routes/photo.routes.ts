import { Router} from "express";
import { PhotoController } from '../controllers/photo.controller';
import upload from '../middlewares/upload';

const photoRoutes = Router();
const photoController = new PhotoController();


photoRoutes.post('/photos/upload/:id', upload.single('photo'), photoController.create.bind(photoController));

export default photoRoutes;
