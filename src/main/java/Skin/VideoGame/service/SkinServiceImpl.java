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
public class SkinServiceImpl implements SkinService {
    @Autowired
    private SkinRepository skinRepository;
    @Autowired
    private Converter converter;

    @Override
    public SkinDto createNewSkin(String nombre, TipoSkin tipoSkin, ColorSkin colorSkin, double precio) {
      SkinDocument savedSkinDocument = skinRepository.save(SkinDocument.builder()
                .idSkin(converter.generateUUIDRandom())
                .nombre(nombre)
                .tipos(tipoSkin)
                .color(colorSkin)
                .precio(precio)
                .build());
      return converter.toSkinDto(savedSkinDocument);
    }

}
