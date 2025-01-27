import axios from "axios";
import { BadRequestError } from "../helpers/api-erros";

export class GeocodingService {
    private apiKey: string;

    constructor(apiKey: string) {
        this.apiKey = apiKey;
    }

    async getCityByCoordinates(latitude: number, longitude: number): Promise<{ city: string; state: string }> {
        try {
            const response = await axios.get("https://maps.googleapis.com/maps/api/geocode/json", {
                params: {
                    latlng: `${latitude},${longitude}`,
                    key: this.apiKey,
                },
            });
            
            console.log("Resposta da API do Google Maps:", JSON.stringify(response.data, null, 2));
            
            const results = response.data.results;
            if (!results || results.length === 0) {
                throw new BadRequestError("Nenhuma cidade encontrada para essas coordenadas.");
            }
    
            // Extrair os componentes do endereço
            const addressComponents = results[0].address_components;
    
            // Encontrar a cidade
            const cityComponent = addressComponents.find((component: any) =>
                component.types.includes("locality") || component.types.includes("sublocality") || component.types.includes("administrative_area_level_2")
            );

            const stateComponent = addressComponents.find((component: any) =>
                component.types.includes("administrative_area_level_1")
            );

            if (!cityComponent || !stateComponent) {
                console.error("Componentes do endereço:", addressComponents);
                throw new BadRequestError("Não foi possível determinar cidade ou estado com base nas coordenadas.");
            }
    
            const city = cityComponent.long_name;
            const state = stateComponent.short_name;
    
            return { city, state };
        } catch (error) {
            console.error("Erro ao obter cidade e estado por coordenadas:", error);
            throw new BadRequestError("Erro ao processar a localização.");
        }
    }
    
}
