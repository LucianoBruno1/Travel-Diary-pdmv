import { FavoriteDiaryRepository } from "../repositories/favorite_diary.repository";
import { CreateFavoriteDiaryRequestDto, FavoriteDiaryResponseDto } from "../dtos/favorite_diary/favorite_diary.dto";
import { FavoriteDiary } from "../models/favorite_diaries.model";
import { DiaryRepository } from "../repositories/diary.repository";
import { UserRepository } from "../repositories/user.repository";
import { NotFoundError } from "../helpers/api-erros";

export class FavoriteDiaryService {
    private favoriteDiaryRepository: FavoriteDiaryRepository;
    private diaryRepository: DiaryRepository;
    private userRepository: UserRepository;

    constructor() {
        this.diaryRepository = new DiaryRepository();
        this.userRepository = new UserRepository();
        this.favoriteDiaryRepository = new FavoriteDiaryRepository();
    }

    async getFavoritesByUser(userId: string): Promise<FavoriteDiaryResponseDto[]> {
        const favorites = await this.favoriteDiaryRepository.findByUser(userId);

        return favorites.map((favorite) => ({
            id: favorite.id,
            diary: { id: favorite.diary.id, name: favorite.diary.name },
        }));
    }

    async addFavorite(dto: CreateFavoriteDiaryRequestDto): Promise<FavoriteDiary> {
        const data = dto.getAll();
        console.log("Favoritando diário ID:", data.diaryId, "para usuário:", data.userId);

        const diaryFind = await this.diaryRepository.findById(data.diaryId);
        if (!diaryFind) {
            throw new NotFoundError("Nenhum diário encontrado.");
        }

        const userFind = await this.userRepository.findById(data.userId);
        if (!userFind) {
            throw new NotFoundError(`Usuário com ID ${data.userId} não foi encontrado.`);
        }

        const favorite = new FavoriteDiary();
        favorite.user = userFind;
        favorite.diary = diaryFind;

        return await this.favoriteDiaryRepository.save(favorite);
    }

    async removeFavorite(userId: string, diaryId: string): Promise<void> {
        await this.favoriteDiaryRepository.deleteFavorite(userId, diaryId);
    }
}
