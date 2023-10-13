package Skin.VideoGame.documents;

import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PlayerDocumentTest {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlayerDocumentInstance() {
        new PlayerDocument();
        PlayerDocument playerDocument = PlayerDocument.builder()
                .idPlayer("1")
                .nombre("Player1")
                .tipo(PlayerType.ESTRATEGA)
                .level(Level.AVANZADO)
                .mySkins(new HashSet<>())
                .build();

        playerDocument.getMySkins().add(new SkinDocument());

        assertEquals("1", playerDocument.getIdPlayer());
        assertEquals("Player1", playerDocument.getNombre());
        assertEquals(PlayerType.ESTRATEGA, playerDocument.getTipo());
        assertEquals(Level.AVANZADO, playerDocument.getLevel());
        assertNotNull(playerDocument.getMySkins());
    }

}