import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const loginSchema = z.object({
    email: z.string().email("E-mail inválido"),
    password: z.string().min(1, "Senha é obrigatória"),
});

export class LoginUserRequestDto extends AbstractDTO<typeof loginSchema> {
    protected rules() {
        return loginSchema;
    }
}
