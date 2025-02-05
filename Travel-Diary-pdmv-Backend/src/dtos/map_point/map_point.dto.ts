import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const createMapPointSchema = z.object({
    userId: z.string().uuid("O ID do usuário deve ser um UUID válido."),
});

export class CreateMapPointRequestDto extends AbstractDTO<typeof createMapPointSchema> {
    protected rules() {
        return createMapPointSchema;
    }
}

export type MapPointResponseDto = {
    id: string;
    latitude: number;
    longitude: number;
    diary: { id: string; name: string };
};
