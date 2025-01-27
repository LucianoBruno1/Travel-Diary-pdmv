import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const forgotPasswordSchema = z.object({
    email: z.string().email("E-mail inválido"),
});

export class ForgotPasswordRequestDto extends AbstractDTO<typeof forgotPasswordSchema> {
    protected rules() {
        return forgotPasswordSchema;
    }
}
