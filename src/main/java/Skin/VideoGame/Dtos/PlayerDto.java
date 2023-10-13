package Skin.VideoGame.Dtos;

import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PlayerDto {
    @JsonProperty(value = "name", index = 0)
    private String nombre;
    @JsonProperty(index = 1)
    private PlayerType tipo;
    @JsonProperty(index = 2)
    private Level level;
}
