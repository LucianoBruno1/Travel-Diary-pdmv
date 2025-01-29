import { NextFunction, Request, Response } from "express";
import { BaseController } from "./base/base.controller";
import { PhotoService } from "../services/photo.service";
import { CreateAddPhotoToDiaryRequestDto, CreatePhotoRequestDto } from "../dtos/photo/photo.dto";
import { BadRequestError } from "../helpers/api-erros";

export class PhotoController extends BaseController<PhotoService> {
    constructor() {
        super(new PhotoService());
    }

    async create(req: Request, res: Response, next: NextFunction): Promise<void>  {
        if (!req.file) {
            throw new BadRequestError("Nenhuma foto enviada.");
        }
    
        const filePath = req.file.path;

        const dto = new CreatePhotoRequestDto({
            ...req.body,
            file_path: filePath,
        });

        const { id } = req.params;
        console.log("ID recebido da URL:", id);

        return this.handleRequest(req, res, next, async () => this.service.uploadPhoto(dto, id), "Foto salva com sucesso!", 201);
    }

    async addPhotoToDiary(req: Request, res: Response, next: NextFunction): Promise<void>  {
        
        if (!req.files || !Array.isArray(req.files) || req.files.length === 0) {
            throw new BadRequestError("Nenhuma foto enviada.");
        }
        
        const filePaths = (req.files as Express.Multer.File[]).map(file => file.path);

        
        const { id } = req.params;
        console.log("ID recebido da URL:", id);

        const dto = new CreateAddPhotoToDiaryRequestDto({
            ...req.body,
            file_paths: filePaths,
        });


        return this.handleRequest(req, res, next, async () => this.service.addPhotoToDiary(id, dto), "Foto salva com sucesso!", 201);
    }

}