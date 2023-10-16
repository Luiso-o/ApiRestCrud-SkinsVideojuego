package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.exceptions.BadUUIDException;
import Skin.VideoGame.exceptions.SkinNotFoundException;
import Skin.VideoGame.helper.Converter;
import Skin.VideoGame.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @InjectMocks
    private GameService gameService;
    @Mock
    private SkinServiceImpl skinService;
    @Mock
    private Converter converter;
    @Mock
    private PlayerServiceImpl playerService;
    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void viewAllAvailableSkins() {
        Set<SkinDto> sampleSkinSet = new HashSet<>();
        sampleSkinSet.add(new SkinDto());
        sampleSkinSet.add(new SkinDto());

        when(skinService.findListSkins()).thenReturn(sampleSkinSet);

        Set<SkinDto> result = skinService.findListSkins();
        assertEquals(sampleSkinSet, result);
    }

    @Test
    void buyNewSkinForAPlayer() throws BadUUIDException {
        String playerId = "bafca566-1684-4085-bccb-3de781a59ee1";
        String skinId = "bafca566-1684-4085-bccb-3de781a59ee2";

        doNothing().when(converter).validatePlayerUUID(playerId);
        doNothing().when(converter).validateSkinUUID(skinId);

        SkinDocument mockSkin = new SkinDocument("bafca566-1684-4085-bccb-3de781a59ee2","Alma de fuego", TipoSkin.AURA_DE_FUEGO, ColorSkin.NEGRO, 200.0);
        PlayerDocument mockPlayer = new PlayerDocument("bafca566-1684-4085-bccb-3de781a59ee1","Sandra", PlayerType.ESTRATEGA, Level.EXPERTO,new HashSet<>());

        when(skinService.findSkinById(skinId)).thenReturn(mockSkin);
        when(playerService.findPlayerById(playerId)).thenReturn(mockPlayer);
        when(converter.fromSkinDocumentToDto(mockSkin)).thenReturn(new SkinDto(
                mockSkin.getIdSkin(),
                mockSkin.getNombre(),
                mockSkin.getTipos(),
                mockSkin.getColor(),
                mockSkin.getPrecio()
        ));

        SkinDto result = gameService.buyNewSkinForAPLayer(playerId, skinId);

        verify(playerRepository).save(mockPlayer);
        assertTrue(mockPlayer.getMySkins().contains(mockSkin));
        assertEquals(mockSkin.getNombre(), result.getNombre());
    }

    @Test
    void viewAllThePlayersSkins() throws BadUUIDException {
        String playerId = "bafca566-1684-4085-bccb-3de781a59ee1";

        PlayerDocument mockPlayer = new PlayerDocument(playerId, "Sandra", PlayerType.ESTRATEGA, Level.EXPERTO, new HashSet<>());
        Set<SkinDocument> mockSkins = new HashSet<>();
        mockSkins.add(new SkinDocument("skinId1", "Skin1", TipoSkin.ARMADURA, ColorSkin.AZUL, 100.0));
        mockSkins.add(new SkinDocument("skinId2", "Skin2", TipoSkin.AURA_DE_FUEGO, ColorSkin.ROJO, 150.0));
        mockPlayer.setMySkins(mockSkins);

        when(converter.fromSkinDocumentToDto(any())).thenCallRealMethod();
        when(playerService.findPlayerById(playerId)).thenReturn(mockPlayer);

        Set<SkinDto> result = gameService.viewAllThePlayersSkins(playerId);

        assertEquals(2, result.size());
    }

    @Test
    void changePurchasedSkinColor() throws BadUUIDException, SkinNotFoundException {
        String playerId = "bafca566-1684-4085-bccb-3de781a59ee1";
        String skinId = "bafca566-1684-4085-bccb-3de781a59ee2";
        ColorSkin newColor = ColorSkin.ROJO;

        PlayerDocument mockPlayer = new PlayerDocument(playerId, "Sandra", PlayerType.ESTRATEGA, Level.EXPERTO, new HashSet<>());
        SkinDocument mockSkin = new SkinDocument(skinId, "Skin1", TipoSkin.CAPA, ColorSkin.AZUL, 100.0);
        mockPlayer.getMySkins().add(mockSkin);

        doNothing().when(converter).validatePlayerUUID(playerId);
        doNothing().when(converter).validateSkinUUID(skinId);
        when(playerService.findPlayerById(playerId)).thenReturn(mockPlayer);
        when(playerRepository.save(any())).thenReturn(mockPlayer);

        gameService.changePurchasedSkinColor(playerId, skinId, newColor);

        assertEquals(newColor, mockSkin.getColor());
    }

    @Test
    void removeAPurchasedSkinFromAPlayer() throws BadUUIDException, SkinNotFoundException {
        String playerId = "bafca566-1684-4085-bccb-3de781a59ee1";
        String skinId = "bafca566-1684-4085-bccb-3de781a59ee2";

        PlayerDocument mockPlayer = new PlayerDocument(playerId, "Sandra", PlayerType.ESTRATEGA, Level.EXPERTO, new HashSet<>());
        SkinDocument mockSkin = new SkinDocument(skinId, "Skin1", TipoSkin.CAPA, ColorSkin.AZUL, 100.0);
        mockPlayer.getMySkins().add(mockSkin);

        doNothing().when(converter).validatePlayerUUID(playerId);
        doNothing().when(converter).validateSkinUUID(skinId);
        when(playerService.findPlayerById(playerId)).thenReturn(mockPlayer);
        when(playerRepository.save(any())).thenReturn(mockPlayer);

        gameService.removeAPurchasedSkinFromAPlayer(playerId, skinId);

        assertFalse(mockPlayer.getMySkins().contains(mockSkin));
    }
}