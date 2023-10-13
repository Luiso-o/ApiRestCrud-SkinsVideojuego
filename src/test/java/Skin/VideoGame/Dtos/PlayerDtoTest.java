package Skin.VideoGame.Dtos;

import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDtoTest {
    @Test
    public void testPlayerDtoConstruction() {
        PlayerDto playerDto = PlayerDto.builder()
                .nombre("Player1")
                .tipo(PlayerType.ESTRATEGA)
                .level(Level.AVANZADO)
                .build();

        assertEquals("Player1", playerDto.getNombre());
        assertEquals(PlayerType.ESTRATEGA, playerDto.getTipo());
        assertEquals(Level.AVANZADO, playerDto.getLevel());
    }

    @Test
    public void testJsonSerialization() throws Exception {
        PlayerDto playerDto = PlayerDto.builder()
                .nombre("Player1")
                .tipo(PlayerType.ESTRATEGA)
                .level(Level.AVANZADO)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(playerDto);

        assertNotNull(json);
        assertEquals("{\"name\":\"Player1\",\"tipo\":\"ESTRATEGA\",\"level\":\"AVANZADO\"}", json);
    }

    @Test
    public void testJsonDeserialization() throws Exception {
        String json = "{\"name\":\"Player1\",\"tipo\":\"ESTRATEGA\",\"level\":\"AVANZADO\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        PlayerDto playerDto = objectMapper.readValue(json, PlayerDto.class);

        assertEquals("Player1", playerDto.getNombre());
        assertEquals(PlayerType.ESTRATEGA, playerDto.getTipo());
        assertEquals(Level.AVANZADO, playerDto.getLevel());
    }

}