package Skin.VideoGame.Controllers;

import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.repositories.SkinRepository;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SkinControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SkinRepository skinRepository;

    @BeforeEach
    public void deleteAllDatabase(){
        skinRepository.deleteAll();
    }

    @Test
    public void testCreateNewSkin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/skins/add")
                        .param("nombre", "Piel de reptil")
                        .param("tipoSkin", String.valueOf(TipoSkin.CAPA))
                        .param("colorSkin", String.valueOf(ColorSkin.VERDE))
                        .param("precio", String.valueOf(34.54))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteSkinEndpoint() throws Exception {
        String idToDelete = "df9eb543-0715-4871-89c2-f247119a0e51";
        SkinDocument mySkin = SkinDocument.builder()
                .idSkin(idToDelete)
                .build();
        skinRepository.save(mySkin);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/skins/delete")
                        .param("idSkin", idToDelete)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Skin deleted"));
    }

    @Test
    public void testUpdateSkinFromId() throws Exception {
        String id = "7d33913d-32fe-470d-beb2-32854c5c4a2a";
        String nombre = "Nuevo nombre";
        TipoSkin tipoSkin = TipoSkin.ARMADURA;
        ColorSkin colorSkin = ColorSkin.NARANJA;
        double precio = 99.99;

        skinRepository.save(new SkinDocument(id,nombre,tipoSkin,colorSkin,precio));

        mockMvc.perform(MockMvcRequestBuilders.post("/skins/update")
                        .param("id", id)
                        .param("nombre", nombre)
                        .param("tipoSkin", tipoSkin.toString())
                        .param("colorSkin", colorSkin.toString())
                        .param("precio", String.valueOf(precio))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['Nueva skin creada'].nombre").value(nombre));

    }

    @Test
    public void testGetAllSkinsEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/skins/getAll"))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/skins/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }

}