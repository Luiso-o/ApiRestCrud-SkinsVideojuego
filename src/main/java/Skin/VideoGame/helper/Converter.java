package Skin.VideoGame.helper;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public static SkinDto toSkinDto(SkinDocument skin){
        return SkinDto.builder()
                .idSkin(skin.getIdSkin())
                .nombre(skin.getNombre())
                .tipos(skin.getTipos())
                .color(skin.getColor())
                .build();
    }

}
