package Skin.VideoGame.helper;

import Skin.VideoGame.Dtos.PlayerDto;
import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.exceptions.BadUUIDException;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class Converter {

    private static final Pattern UUID_FORM = Pattern.compile("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$", Pattern.CASE_INSENSITIVE);
    private static final Logger log = LoggerFactory.getLogger(Converter.class);
    public SkinDto fromSkinDocumentToDto(SkinDocument skin){
        return SkinDto.builder()
                .idSkin(skin.getIdSkin())
                .nombre(skin.getNombre())
                .tipos(skin.getTipos())
                .color(skin.getColor())
                .precio(skin.getPrecio())
                .build();
    }

    public PlayerDto fromPlayerDocumentToDto(PlayerDocument playerDocument){
        return PlayerDto.builder()
                .nombre(playerDocument.getNombre())
                .tipo(playerDocument.getTipo())
                .level(playerDocument.getLevel())
                .build();
    }

    public String generateUUIDRandom(){
        UUID newUuid = UUID.randomUUID();
        return newUuid.toString();
    }

    public void validateUUID(String id) throws BadUUIDException {
        boolean validUUID = !StringUtils.isEmpty(id) && UUID_FORM.matcher(id).matches();
        if (!validUUID) {
            log.warn("Invalid ID format: {}", id);
            throw new BadUUIDException("Invalid ID format. Please indicate the correct format.");
        }
    }

}
