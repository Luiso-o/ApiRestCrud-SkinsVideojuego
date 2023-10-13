package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.exceptions.BadUUIDException;
import Skin.VideoGame.exceptions.DocumentNotFoundByIdException;
import Skin.VideoGame.helper.Converter;
import Skin.VideoGame.repositories.SkinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Luis
 */
@Service
public class SkinServiceImpl implements SkinService {
    private static final Logger log = LoggerFactory.getLogger(SkinServiceImpl.class);
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
      log.info("Skin creada correctamente");
      return converter.fromSkinDocumentToDto(savedSkinDocument);
    }

    @Override
    public void deleteSkin(String id) throws BadUUIDException {
        converter.validateUUID(id);
        SkinDocument skin = findSkinById(id);
        log.info(skin + " Eliminada con éxito");
        skinRepository.delete(skin);
    }

    @Override
    public SkinDto updateSkin(SkinDocument existingSkin) throws BadUUIDException {
        converter.validateUUID(existingSkin.getIdSkin());
        SkinDocument skinToUpdate = findSkinById(existingSkin.getIdSkin());

        skinToUpdate.setNombre(existingSkin.getNombre());
        skinToUpdate.setTipos(existingSkin.getTipos());
        skinToUpdate.setColor(existingSkin.getColor());
        skinToUpdate.setPrecio(existingSkin.getPrecio());

        skinRepository.save(skinToUpdate);
        log.info("Skin actualizada correctamente");
        return converter.fromSkinDocumentToDto(skinToUpdate);
    }

    @Override
    public Set<SkinDto> findListSkins() {
        Set<SkinDocument> mySkins = new HashSet<>(skinRepository.findAll());
        if (mySkins.isEmpty()) {
           log.info("La lista de skins está vacía.");
            return Collections.emptySet();
        }
        return mySkins.stream().map(skinDocument -> converter.fromSkinDocumentToDto(skinDocument))
                .collect(Collectors.toSet());
    }

    public SkinDocument findSkinById(String id){
        return skinRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundByIdException(id + " No se encontró o no pertenece a ningún Skin de la base de datos."));
    }

}
