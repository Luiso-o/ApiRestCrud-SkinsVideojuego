package Skin.VideoGame.helper;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Converter {

    public SkinDto toSkinDto(SkinDocument skin){
        return SkinDto.builder()
                .idSkin(skin.getIdSkin())
                .nombre(skin.getNombre())
                .tipos(skin.getTipos())
                .color(skin.getColor())
                .precio(skin.getPrecio())
                .build();
    }

    public String generateUUIDRandom(){
        UUID newUuid = UUID.randomUUID();
        return newUuid.toString();
    }
}
