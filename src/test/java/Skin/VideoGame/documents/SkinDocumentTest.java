package Skin.VideoGame.documents;

import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkinDocumentTest {

    @Test
    public void testBuilder() {
        SkinDocument skin = SkinDocument.builder()
                .idSkin("1")
                .nombre("Skin1")
                .tipos(TipoSkin.ARMADURA)
                .color(ColorSkin.ROJO)
                .precio(19.99)
                .build();

        assertEquals("1", skin.getIdSkin());
        assertEquals("Skin1", skin.getNombre());
        assertEquals(TipoSkin.ARMADURA, skin.getTipos());
        assertEquals(ColorSkin.ROJO, skin.getColor());
        assertEquals(19.99, skin.getPrecio(), 0.01);
    }

    @Test
    public void testGetterAndSetterMethods() {
        SkinDocument skin = new SkinDocument();
        skin.setIdSkin("2");
        skin.setNombre("Skin2");
        skin.setTipos(TipoSkin.CASCO);
        skin.setColor(ColorSkin.AZUL);
        skin.setPrecio(29.99);

        assertEquals("2", skin.getIdSkin());
        assertEquals("Skin2", skin.getNombre());
        assertEquals(TipoSkin.CASCO, skin.getTipos());
        assertEquals(ColorSkin.AZUL, skin.getColor());
        assertEquals(29.99, skin.getPrecio(), 0.01);
    }


}