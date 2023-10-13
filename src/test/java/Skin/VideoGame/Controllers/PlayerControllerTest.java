package Skin.VideoGame.Controllers;

import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import Skin.VideoGame.repositories.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PlayerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void deleteAllDatabase(){
        playerRepository.deleteAll();
    }

    @Test
    public void testCreateNewPLayer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/players/add")
                        .param("nombre", "Luis")
                        .param("playerType", String.valueOf(PlayerType.ESTRATEGA))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeletePlayer() throws Exception {
        playerRepository.save(PlayerDocument.builder()
                .idPlayer("df9eb543-0715-4871-89c2-f247119a0e51")
                .tipo(PlayerType.AVENTURERO)
                .build());
        mockMvc.perform(delete("/players/delete")
                        .param("idPLayer", "df9eb543-0715-4871-89c2-f247119a0e51")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Player deleted successfully."));
    }

    @Test
    public void testUpdatePlayerFromId() throws Exception {
        String id = "7d33913d-32fe-470d-beb2-32854c5c4a2a";
        String nombre = "Nuevo nombre";
        PlayerType playerType = PlayerType.AVENTURERO;
        Level nivel = Level.EXPERIMENTADO;

        playerRepository.save(new PlayerDocument(id, nombre, playerType, nivel, new HashSet<>()));

        mockMvc.perform(post("/players/update")
                        .param("idJugador", id)
                        .param("nombre", nombre)
                        .param("playerType", playerType.toString())
                        .param("level", nivel.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllPLayers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/players/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/players/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }

}