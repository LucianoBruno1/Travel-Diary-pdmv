import { CreateUserRequestDto } from "../dtos/user/create-user-request.dto";
import { LoginUserRequestDto } from "../dtos/user/login-user-request.dto";
import { UserLoginResponseDto } from "../dtos/user/user-response-login.dto";
import { UserResponseDto } from "../dtos/user/user-response.dto";
import { BadRequestError, NotFoundError } from "../helpers/api-erros";
import { User } from "../models/user.model";
import { UserRepository } from "../repositories/user.repository";
import bcrypt from "bcrypt";
import jwt from 'jsonwebtoken';
import crypto from "crypto";
import { ResetPasswordRequestDto } from "../dtos/user/resetPassword-request.dto";
import { ForgotPasswordRequestDto } from "../dtos/user/forgotPassword-request.dto";
import transporter from "../utils/emailTransporter";

export class UserService {
    private repository: UserRepository;

    constructor() {
        this.repository = new UserRepository();
    }

    async create(dto: CreateUserRequestDto) {
        const data = dto.getAll();

        // Verifica se o email já existe
        const userEmailVerify = await this.repository.findOne(data.email);

        if (userEmailVerify) {
            throw new BadRequestError("Este email já está em uso.");
        }

        const hashPassword = await bcrypt.hash(data.password, 10)

        const user = new User();
        Object.assign(user, data);
        user.password = hashPassword
        const userCreate = await this.repository.create(user);
        return this.toUserResponseDto(userCreate);
    }

    async login(dto: LoginUserRequestDto) {
        const data = dto.getAll();

        // Verifica se o email existe
        const user = await this.repository.findOne(data.email);

        if (!user) {
            throw new BadRequestError("Email não cadastrado em nossos dados.");
        }

        const verifyPass = await bcrypt.compare(data.password, user.password);

        if (!verifyPass) {
            throw new BadRequestError("Senha inválida.");
        }

        const token = jwt.sign({ id: user.id }, process.env.JWT_SECRET, {
            expiresIn: process.env.JWT_EXPIRATION,
        });

        const toUserReturn = this.toUserLoginResponseDto(user);

        return {
            user: toUserReturn,
            token: token,
        }

    }

    // async update(id: string, dto: UpdateUserRequestDto) {


    // }

    async getProfile(id: string) {
        const user = await this.repository.findById(id);
        if (!user) {
            throw new NotFoundError(`Usuário com ID ${id} não encontrado`);
        }
        
        const userDto = this.toUserResponseDto(user);
        return userDto;
    }

    async remove(id: string) {

    }

    private toUserResponseDto({ id, created_at, name, email }: User): UserResponseDto {
        return { id, created_at, name, email };
    }

    private toUserLoginResponseDto({ id, name, email }: User): UserLoginResponseDto {
        return { id, name, email };
    }



    // sessão recuperar senha 

    async forgotPassword(dto: ForgotPasswordRequestDto) {
        const { email } = dto.getAll();
        const user = await this.repository.findOne(email);
        if (!user) throw new NotFoundError("Usuário não encontrado");
        
        const token = crypto.randomBytes(32).toString('hex');
        const expiration = new Date(Date.now() + 3600000); // 1 hora a partir de agora EX.: 21:02 + 1 = 22:02 < 21:25

        user.reset_token = token;
        user.token_expiration = expiration;
        await this.repository.save(user);

        await this.sendResetEmail(email, token);
        return { message: "Token enviado para o email" };
    }

    async resetPassword(dto: ResetPasswordRequestDto) {
        const { token, newPassword } = dto.getAll();
        const user = await this.repository.findByToken(token);
        if (!user || user.token_expiration < new Date()) {
            throw new BadRequestError("Token inválido ou expirado");
        }
    
        user.password = await bcrypt.hash(newPassword, 10);
        user.reset_token = null;
        user.token_expiration = null;
        await this.repository.save(user);
    
        return { message: "Senha atualizada com sucesso" };
    }

    private async sendResetEmail(email: string, token: string) {
        await transporter.sendMail({
            from: process.env.EMAIL_USER,
            to: email,
            subject: 'Recuperação de Senha',
            text: `Use este token para redefinir sua senha: ${token}`,
        });
    }
}