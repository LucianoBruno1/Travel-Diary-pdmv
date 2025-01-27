import { NextFunction, Request, Response } from "express";
import { verify } from "jsonwebtoken";
import dotenv from "dotenv";
import { UnauthorizedError } from "../helpers/api-erros";
import { UserRepository } from "../repositories/user.repository";

dotenv.config();

type TokenPayload = {
    id: string;
    iat: number;
    exp: number;
}

export async function authenticateJWT(req: Request, res: Response, next: NextFunction) {
    const { authorization } = req.headers;

    if (!authorization) {
        return next(new UnauthorizedError("Token not provided")); 
    }

    const [, token] = authorization.split(" ");

    try {
        const decoded = verify(token, process.env.JWT_SECRET as string);
        const { id } = decoded as TokenPayload;

        const repository = new UserRepository();
        const user = await repository.findById(id);

        if (!user) {
            return next(new UnauthorizedError("Não autorizado"));
        }

        req.headers["userId"] = id;
        next();
    } catch (error) {
        next(new UnauthorizedError("Token invalid")); 
    }
}
