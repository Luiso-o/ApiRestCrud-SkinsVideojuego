package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.exceptions.BadUUIDException;

import java.util.Set;

public interface SkinService {
    SkinDto createNewSkin(String nombre, TipoSkin tipoSkin, ColorSkin colorSkin, double precio);
    void deleteSkin(String id) throws BadUUIDException;
    SkinDto updateSkin(SkinDocument skinDocument) throws BadUUIDException;
    SkinDocument findSkinById(String id);
    Set<SkinDto> findListSkins();

}
