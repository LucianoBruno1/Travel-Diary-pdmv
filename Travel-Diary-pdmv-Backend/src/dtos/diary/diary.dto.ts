import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const createDiarySchema = z.object({
    name: z.string().optional(),
    description: z.string().optional(),
    travel_date: z.string().optional().refine(
        (value) => !value || !isNaN(Date.parse(value)),
        "A data de viagem deve ser válida."
    ),
    city: z.string().optional(),
    state: z.string().optional(),
    latitude: z.number().min(-90).max(90, "Latitude deve estar entre -90 e 90."),
    longitude: z.number().min(-180).max(180, "Longitude deve estar entre -180 e 180."),
    id: z.string().uuid("O ID do usuário deve ser um UUID válido."),
});

export class CreateDiaryRequestDto extends AbstractDTO<typeof createDiarySchema> {
    protected rules() {
        return createDiarySchema;
    }
}

export type DiaryResponseDto = {
    id: string;
    name: string;
    description?: string;
    travel_date?: Date | string;
    city: string;
    state: string;
    latitude: number;
    longitude: number;
    user: { id: string };
    photos: { id: string; file_path: string }[];
};
