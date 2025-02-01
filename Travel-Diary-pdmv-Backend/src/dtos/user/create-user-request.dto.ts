import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const createUserSchema = z.object({
    name: z.string().min(3, "O nome é obrigatório. E deve ter no mínimo 5 caracteres"),
    email: z.string().email("E-mail inválido"),
    password: z.string().min(6, "A senha deve ter no mínimo 6 caracteres"),
})

export class CreateUserRequestDto extends AbstractDTO<typeof createUserSchema> {
    protected rules() {
        return createUserSchema;
    }
}