package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.helper.Converter;
import Skin.VideoGame.repositories.SkinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author Luis
 */
@Service
public class SkinService {
    @Autowired
    private SkinRepository skinRepository;

    public SkinDto createNewSkin(String nombre, TipoSkin tipoSkin, ColorSkin colorSkin, double precio) {
     return Converter.toSkinDto(skinRepository.save(SkinDocument.builder()
               .nombre(nombre)
               .tipos(tipoSkin)
               .color(colorSkin)
               .precio(precio)
               .build()));
    }
}
