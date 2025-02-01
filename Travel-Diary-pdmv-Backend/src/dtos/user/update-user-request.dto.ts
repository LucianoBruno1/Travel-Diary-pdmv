import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const updateSchema = z.object({
    name: z.string().min(3, "O nome deve ter no mínimo 5 caracteres").optional(),
    email: z.string().email("E-mail inválido").optional(),
    password: z.string().min(6, "A senha deve ter pelo menos 6 caracteres").optional(),
    profilePicture: z.string().optional(), 
    birthDate: z.string().optional().refine(
        (value) => !value || !isNaN(Date.parse(value)),
        "A data de nascimento deve ser válida."
    ).optional(),
    bio: z.string().max(500, "A bio deve ter no máximo 500 caracteres").optional()
});

export class UpdateUserRequestDto extends AbstractDTO<typeof updateSchema> {
    protected rules() {
        return updateSchema;
    }
}
