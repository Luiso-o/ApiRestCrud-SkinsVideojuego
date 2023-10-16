package Skin.VideoGame.Controllers;

import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.*;
import Skin.VideoGame.repositories.PlayerRepository;
import Skin.VideoGame.repositories.SkinRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SkinRepository skinRepository;

    @BeforeEach
    public void setUp(){
        PlayerDocument player1 =  new PlayerDocument("550e8400-e29b-41d4-a716-446655440000","Ana",PlayerType.ESTRATEGA,Level.PRINCIPIANTE, new HashSet<>());
        PlayerDocument player2 = new PlayerDocument("2ed52b0a-b68c-4e36-8a92-16d8944c79d8","Marcos",PlayerType.MEDICO,Level.AVANZADO, new HashSet<>());
        PlayerDocument player3 = new PlayerDocument("8c7c77e4-d539-4b4a-a440-ee652db3a665","Sara",PlayerType.AVENTURERO,Level.EXPERIMENTADO, new HashSet<>());

        SkinDocument skin1 = new SkinDocument("0ea45c3a-1a18-4745-9c52-9536986d4a98","Vuelo Real",TipoSkin.ALAS,ColorSkin.BLANCO,100.10);
        SkinDocument skin2 = new SkinDocument("1e62d7a8-5a50-4962-93b1-31c8fc041b4c","Caparazón de tortuga",TipoSkin.ARMADURA,ColorSkin.AMARILLO,200.0);
        SkinDocument skin3 = new SkinDocument("1e62d7a8-5a50-4962-9c52-16d8944c79d8","Brazo de zombie",TipoSkin.CINTURON,ColorSkin.VERDE,250.0);

        playerRepository.saveAll(List.of(player1,player2,player3));
        skinRepository.saveAll(List.of(skin1,skin2,skin3));
    }

    @AfterEach
    public void deleteAllDatabase(){
        playerRepository.deleteAll();
        skinRepository.deleteAll();
    }

    @Test
    void seeAllTheAvailableSkins() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/game/skins/available") // Reemplaza "/your-base-path" con la ruta de tu controlador
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void buyANewSkinForAPlayer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/game/skins/buy")
                        .param("idPlayer", "550e8400-e29b-41d4-a716-446655440000")
                        .param("idSkin", "1e62d7a8-5a50-4962-93b1-31c8fc041b4c")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['Nueva skin comprada! ']").isNotEmpty());
    }

    @Test
    void seeAllThePlayersSkins() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/game/skins/myskins")
                        .param("idPlayer", "2ed52b0a-b68c-4e36-8a92-16d8944c79d8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void changeTheColorOfAPlayersSkin() throws Exception {
        PlayerDocument player = playerRepository.findById("8c7c77e4-d539-4b4a-a440-ee652db3a665").orElse(null);
        SkinDocument skin = skinRepository.findById("1e62d7a8-5a50-4962-9c52-16d8944c79d8").orElse(null);

        assert player != null;
        player.getMySkins().add(skin);
        playerRepository.save(player);

        mockMvc.perform(MockMvcRequestBuilders.put("/game/skins/color")
                        .param("idPlayer", "8c7c77e4-d539-4b4a-a440-ee652db3a665")
                        .param("idSkin", "1e62d7a8-5a50-4962-9c52-16d8944c79d8")
                        .param("newColor", String.valueOf(ColorSkin.AZUL))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Cambio de color exitoso!!!"));
    }

    @Test
    void removeAPurchasedSkinFromAPlayer() throws Exception {
        PlayerDocument player = playerRepository.findById("8c7c77e4-d539-4b4a-a440-ee652db3a665").orElse(null);
        SkinDocument skin = skinRepository.findById("1e62d7a8-5a50-4962-9c52-16d8944c79d8").orElse(null);

        assert player != null;
        player.getMySkins().add(skin);
        playerRepository.save(player);
        mockMvc.perform(MockMvcRequestBuilders.delete("/game/skins/delete")
                        .param("idPlayer", "8c7c77e4-d539-4b4a-a440-ee652db3a665")
                        .param("idSkin", "1e62d7a8-5a50-4962-9c52-16d8944c79d8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}