import { NextFunction, Request, Response } from "express";
import { BaseController } from "./base/base.controller";
import { MapPointService } from "../services/map_point.service";

export class MapPointController extends BaseController<MapPointService> {
    constructor() {
        super(new MapPointService());
    }

    async getMapPoints(req: Request, res: Response, next: NextFunction): Promise<void> {
        const { id } = req.params;
    
        return this.handleRequest(req, res, next, async () => await this.service.getMapPointsByUser(id), "Sucesso", 200);
    }

}
