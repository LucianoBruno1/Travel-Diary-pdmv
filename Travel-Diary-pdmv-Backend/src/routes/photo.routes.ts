import { Router} from "express";
import { PhotoController } from '../controllers/photo.controller';
import upload from '../middlewares/upload';

const photoRoutes = Router();
const photoController = new PhotoController();


photoRoutes.post('/photos/uploadPhoto/:id', upload.single('photo'), photoController.create.bind(photoController));
photoRoutes.post('/photos/uploadPhotoDiary/:id', upload.array('photos'), photoController.addPhotoToDiary.bind(photoController));

export default photoRoutes;
