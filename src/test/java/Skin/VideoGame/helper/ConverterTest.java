package Skin.VideoGame.helper;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConverterTest {

    private Converter converter;

    @BeforeEach
    public void setUp() {
        converter = new Converter();
    }

    @Test
    public void testToSkinDtoConversion() {
        SkinDocument skinDocument = new SkinDocument();
        skinDocument.setIdSkin("1");
        skinDocument.setNombre("Ejemplo Skin");
        skinDocument.setTipos(TipoSkin.ALAS);
        skinDocument.setColor(ColorSkin.AZUL);
        skinDocument.setPrecio(19.99);

        SkinDto skinDto = converter.toSkinDto(skinDocument);

        assertEquals(skinDocument.getIdSkin(), skinDto.getIdSkin());
        assertEquals(skinDocument.getNombre(), skinDto.getNombre());
        assertEquals(skinDocument.getTipos(), skinDto.getTipos());
        assertEquals(skinDocument.getColor(), skinDto.getColor());
        assertEquals(skinDocument.getPrecio(), skinDto.getPrecio(), 0.001);
    }

    @Test
    public void testGenerateUUIDRandom() {
        String uuid = converter.generateUUIDRandom();
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new AssertionError("La cadena generada no es un UUID válido.");
        }
    }
}