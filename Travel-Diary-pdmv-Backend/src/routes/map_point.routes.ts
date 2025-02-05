import { Router} from "express";
import { MapPointController } from "../controllers/map_point.controller";

const mapPointRoutes = Router();
const mapPointController = new MapPointController();


mapPointRoutes.get('/points/:id', mapPointController.getMapPoints.bind(mapPointController));

export default mapPointRoutes;
