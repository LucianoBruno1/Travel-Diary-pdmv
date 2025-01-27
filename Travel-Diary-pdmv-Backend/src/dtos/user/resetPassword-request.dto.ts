import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const resetPasswordSchema = z.object({
    token: z.string().min(1, "Token é obrigatório"),
    newPassword: z.string().min(6, "A nova senha deve ter no mínimo 6 caracteres"),
});

export class ResetPasswordRequestDto extends AbstractDTO<typeof resetPasswordSchema> {
    protected rules() {
        return resetPasswordSchema;
    }
}
