package Skin.VideoGame.Dtos;

import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SkinDtoTest {

    ObjectMapper objectMapper;

    {
        objectMapper = new ObjectMapper();
    }

    private SkinDto skinDto;

    @BeforeEach
    public void setUp() {
        skinDto = SkinDto.builder()
                .idSkin("31f64d3e-1aad-449f-9a11-a9f70cfc0452")
                .nombre("Ejemplo Skin")
                .tipos(TipoSkin.ALAS)
                .color(ColorSkin.AZUL)
                .precio(19.99)
                .build();
    }

    @Test
    public void testGetters() {
        assertEquals("31f64d3e-1aad-449f-9a11-a9f70cfc0452", skinDto.getIdSkin());
        assertEquals("Ejemplo Skin", skinDto.getNombre());
        assertEquals(TipoSkin.ALAS, skinDto.getTipos());
        assertEquals(ColorSkin.AZUL, skinDto.getColor());
        assertEquals(19.99, skinDto.getPrecio(), 0.001);
    }

    @Test
    public void testSerializationToJson() throws JsonProcessingException {
        String json = "{\"idSkin\":\"31f64d3e-1aad-449f-9a11-a9f70cfc0452\",\"nombre\":\"Ejemplo Skin\",\"tipos\":\"ALAS\",\"color\":\"AZUL\",\"precio\":19.99}";
        assertEquals(json, objectMapper.writeValueAsString(skinDto));
    }

    @Test
    public void testDeserializationFromJson() throws JsonProcessingException {
        String json = "{\"idSkin\":\"31f64d3e-1aad-449f-9a11-a9f70cfc0452\",\"nombre\":\"Otro Skin\",\"tipos\":\"ALAS\",\"color\":\"ROJO\",\"precio\":29.99}";
        SkinDto deserializedSkinDto = objectMapper.readValue(json, SkinDto.class);

        assertEquals("31f64d3e-1aad-449f-9a11-a9f70cfc0452", deserializedSkinDto.getIdSkin());
        assertEquals("Otro Skin", deserializedSkinDto.getNombre());
        assertEquals(TipoSkin.ALAS, deserializedSkinDto.getTipos());
        assertEquals(ColorSkin.ROJO, deserializedSkinDto.getColor());
        assertEquals(29.99, deserializedSkinDto.getPrecio(), 0.001);
    }

}