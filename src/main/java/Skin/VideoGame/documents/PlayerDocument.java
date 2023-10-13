package Skin.VideoGame.documents;

import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "players")
public class PlayerDocument {
    @MongoId
    @Field(name = "idPlayer")
    private String idPlayer;
    @Field(name = "name")
    private String nombre;
    @Field(name = "Type")
    private PlayerType tipo;
    @Field(name = "Level")
    private Level level;
    @Field(name = "Skins")
    @Transient
    private Set<SkinDocument> mySkins;
}
