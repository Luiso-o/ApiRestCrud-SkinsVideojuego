package Skin.VideoGame.Dtos;

import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SkinDto {
    @JsonProperty(value = "id_Skin", index = 0)
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
