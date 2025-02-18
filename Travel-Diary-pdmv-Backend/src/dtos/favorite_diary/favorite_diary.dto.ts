import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const createFavoriteDiarySchema = z.object({
    userId: z.string().uuid("O ID do usuário deve ser um UUID válido."),
    diaryId: z.string().uuid("O ID do diário deve ser um UUID válido."),
});

export class CreateFavoriteDiaryRequestDto extends AbstractDTO<typeof createFavoriteDiarySchema> {
    protected rules() {
        return createFavoriteDiarySchema;
    }
}

export type FavoriteDiaryResponseDto = {
    id: string;
    diary: { id: string; name: string };
};
