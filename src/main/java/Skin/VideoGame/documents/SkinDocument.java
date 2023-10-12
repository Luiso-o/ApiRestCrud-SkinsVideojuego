package Skin.VideoGame.documents;

import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

/**
 * @author Luis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Skins")
public class SkinDocument {

    @MongoId
    @Field(name = "idSkin")
    private String idSkin;
    @Field(name = "nombre")
    private String nombre;
    @Field(name = "tipoSkin")
    private TipoSkin tipos;
    @Field(name = "color")
    private ColorSkin color;
    @Field(name = "precio")
    private Double precio;

}
