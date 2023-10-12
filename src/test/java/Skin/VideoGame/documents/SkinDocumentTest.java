package Skin.VideoGame.documents;

import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.helper.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkinDocumentTest {

    private Converter converter;

    @BeforeEach
    void setUp() {
        converter = new Converter();
    }

    @Test
    public void testBuilder() {
        String idRandom = converter.generateUUIDRandom();

        SkinDocument skin = SkinDocument.builder()
                .idSkin(idRandom)
                .nombre("Skin1")
                .tipos(TipoSkin.ARMADURA)
                .color(ColorSkin.ROJO)
                .precio(19.99)
                .build();

        assertEquals(idRandom, skin.getIdSkin());
        assertEquals("Skin1", skin.getNombre());
        assertEquals(TipoSkin.ARMADURA, skin.getTipos());
        assertEquals(ColorSkin.ROJO, skin.getColor());
        assertEquals(19.99, skin.getPrecio(), 0.01);
    }

    @Test
    public void testGetterAndSetterMethods() {
        String idRandom = converter.generateUUIDRandom();

        SkinDocument skin = new SkinDocument();
        skin.setIdSkin(idRandom);
        skin.setNombre("Skin2");
        skin.setTipos(TipoSkin.CASCO);
        skin.setColor(ColorSkin.AZUL);
        skin.setPrecio(29.99);

        assertEquals(idRandom, skin.getIdSkin());
        assertEquals("Skin2", skin.getNombre());
        assertEquals(TipoSkin.CASCO, skin.getTipos());
        assertEquals(ColorSkin.AZUL, skin.getColor());
        assertEquals(29.99, skin.getPrecio(), 0.01);
    }


}