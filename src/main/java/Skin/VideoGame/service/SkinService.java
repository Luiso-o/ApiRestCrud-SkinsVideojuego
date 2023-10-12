package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;

public interface SkinService {

    SkinDto createNewSkin(String nombre, TipoSkin tipoSkin, ColorSkin colorSkin, double precio);

    //puedes implementar los metodos que creas necesarios....
}
