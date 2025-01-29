import z from "zod";
import { AbstractDTO } from "../abstract.dto";

// Validação dos dados recebidos no request
const createPhotoSchema = z.object({
    latitude: z.preprocess(
        (value) => parseFloat(value as string), // Converte para número
        z.number().min(-90, "A latitude deve ser maior ou igual a -90.").max(90, "A latitude deve ser menor ou igual a 90.")
    ),
    longitude: z.preprocess(
        (value) => parseFloat(value as string), // Converte para número
        z.number().min(-180, "A longitude deve ser maior ou igual a -180.").max(180, "A longitude deve ser menor ou igual a 180.")
    ),
    file_path: z.string().nonempty("O caminho do arquivo é obrigatório."),
});

export class CreatePhotoRequestDto extends AbstractDTO<typeof createPhotoSchema> {
    protected rules() {
        return createPhotoSchema;
    }
}

const createAddPhotoToDiarySchema = z.object({
    diary: z.string().uuid("O ID do diário deve ser um UUID válido").optional(),
    file_paths: z
        .array(z.string())
        .nonempty("Pelo menos um caminho de arquivo deve ser fornecido."),
});

export class CreateAddPhotoToDiaryRequestDto extends AbstractDTO<typeof createAddPhotoToDiarySchema> {
    protected rules() {
        return createAddPhotoToDiarySchema;
    }
}


// DTO para resposta
export type PhotoResponseDto = {
    id: string;
    created_at: Date;
    file_path: string;
    diary: { id: string }; 
    latitude: number;
    longitude: number;
};

export type PhotosArrayResponseDto = {
    photos: {
        id: string;
        created_at: Date;
        file_path: string;
        diary: { id: string };
    }[];
};