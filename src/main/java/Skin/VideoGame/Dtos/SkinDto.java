package Skin.VideoGame.Dtos;

import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SkinDto {
    @JsonProperty(value = "idSkin", index = 0)
    private String idSkin;
    @JsonProperty(index = 1)
    private String nombre;
    @JsonProperty(index = 2)
    private TipoSkin tipos;
    @JsonProperty(index = 3)
    private ColorSkin color;
    @JsonProperty(index = 4)
    private Double precio;
}
