import z from "zod";
import { AbstractDTO } from "../abstract.dto";

const updateDiarySchema = z.object({
    name: z.string().optional(),
    description: z.string().optional(),
    travel_date: z.string().optional().refine(
        (value) => !value || !isNaN(Date.parse(value)),
        "A data de viagem deve ser válida."
    ).transform((str) => new Date(str)).optional(),
    city: z.string().optional(),
    state: z.string().optional()
});

export class UpdateDiaryRequestDto extends AbstractDTO<typeof updateDiarySchema> {
    protected rules() {
        return updateDiarySchema;
    }
}


export type DiaryUpdateResponseDto = {
    id: string;
    name: string;
    description?: string;
    travel_date?: Date | string;
    city: string;
    state: string;
    latitude: number;
    longitude: number;
    user: { id: string };
};

