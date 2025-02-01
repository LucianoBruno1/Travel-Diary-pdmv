import { NextFunction, Request, Response } from "express";
import { CreateUserRequestDto } from "../dtos/user/create-user-request.dto";
import { UserService } from "../services/user.service";
import { BaseController } from "./base/base.controller";
import { LoginUserRequestDto } from "../dtos/user/login-user-request.dto";
import { ForgotPasswordRequestDto } from "../dtos/user/forgotPassword-request.dto";
import { ResetPasswordRequestDto } from "../dtos/user/resetPassword-request.dto";
import { UpdateUserRequestDto } from "../dtos/user/update-user-request.dto";

export class UserController extends BaseController<UserService> {
    constructor() {
        super(new UserService());
    }

    async forgotPassword(req: Request, res: Response, next: NextFunction) {
        const dto = new ForgotPasswordRequestDto(req.body);
        return this.handleRequest(req, res, next, async () => this.service.forgotPassword(dto), "Solicitação de redefinição enviada", 200);
    }
    
    async resetPassword(req: Request, res: Response, next: NextFunction) {
        const dto = new ResetPasswordRequestDto(req.body);
        return this.handleRequest(req, res, next, async () => this.service.resetPassword(dto), "Senha redefinida com sucesso", 200);
    }

    async create(req: Request, res: Response, next: NextFunction) {
        const dto = new CreateUserRequestDto({
            ...req.body
        });
        return this.handleRequest(req, res, next, async () => this.service.create(dto), "Usuário criado com sucesso", 201);
    }

    async login(req: Request, res: Response, next: NextFunction) {
        const dto = new LoginUserRequestDto({
            ...req.body
        });
        return this.handleRequest(req, res, next, async () => this.service.login(dto), "Usuário autenticado com sucesso", 200);
    }

    async getProfile(req: Request, res: Response, next: NextFunction) {
        const id = req.params.id;
        return this.handleRequest(req, res, next, async () => this.service.getProfile(id), "Usuário encontrado com sucesso", 200);
    }

    async update(req: Request, res: Response, next: NextFunction) {
        const dto = new UpdateUserRequestDto({
            ...req.body
        });
        const id = req.params.id;
        return this.handleRequest(req, res, next, async () => this.service.update(id, dto), "Usuário atualizado com sucesso", 200);
    }

    // async remove(req: Request, res: Response, next: NextFunction) {
    //     const id = req.params.id;
    //     return this.handleRequest(req, res, next, async () => this.service.remove(id), "Representante removido com sucesso", 200);
    // }

    // async findAll(req: Request, res: Response, next: NextFunction) {
    //     return this.handleRequest(req, res, next, async () => this.service.findAll(), "Esses são todos os representantes");

    // }

}