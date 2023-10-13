package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.PlayerDto;
import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import Skin.VideoGame.exceptions.BadUUIDException;
import Skin.VideoGame.helper.Converter;
import Skin.VideoGame.repositories.PlayerRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PlayerServiceImplTest {
    @InjectMocks
    private PlayerServiceImpl playerService;
    @Mock
    private Converter converter;
    @Mock
    private PlayerRepository playerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewPlayer() {
        String nombre = "Luis";
        PlayerType tipoJugador = PlayerType.MAGO;

        PlayerDocument savedPlayerDocument = new PlayerDocument();
        when(playerRepository.save(any(PlayerDocument.class))).thenReturn(savedPlayerDocument);

        PlayerDto savedPlayerDto = new PlayerDto();
        when(converter.fromPlayerDocumentToDto(savedPlayerDocument)).thenReturn(savedPlayerDto);

        PlayerDto result = playerService.createNewPlayer(nombre,tipoJugador);

        verify(playerRepository).save(any(PlayerDocument.class));
        verify(converter).fromPlayerDocumentToDto(savedPlayerDocument);

        Assertions.assertEquals(savedPlayerDto, result);
    }

    @Test
    public void testDeletePlayerSuccess() throws BadUUIDException {
        String validUUID = "550e8400-e29b-41d4-a716-446655440000";
        PlayerDocument player = new PlayerDocument();
        doNothing().when(converter).validateUUID(validUUID);
        when(playerRepository.findById(validUUID)).thenReturn(java.util.Optional.of(player));
        playerService.deletePlayer(validUUID);
        verify(playerRepository, Mockito.times(1)).delete(player);
    }

    @Test(expected = BadUUIDException.class)
    public void testDeletePlayerInvalidUUID() throws BadUUIDException {
        String invalidUUID = "invalid-uuid-format";
        Mockito.doThrow(new BadUUIDException("Invalid ID format")).when(converter).validateUUID(invalidUUID);
        playerService.deletePlayer(invalidUUID);
    }

    @Test
    public void testUpdateSkin() throws BadUUIDException {
        PlayerDocument jugadorExistente = PlayerDocument.builder()
                .idPlayer("7d33913d-32fe-470d-beb2-32854c5c4a2a")
                .nombre("Juan")
                .tipo(PlayerType.GUERRERO)
                .level(Level.EXPERTO)
                .mySkins(new HashSet<>())
                .build();
        doNothing().when(converter).validateUUID(jugadorExistente.getIdPlayer());
        when(playerRepository.findById(jugadorExistente.getIdPlayer())).thenReturn(Optional.of(jugadorExistente));
        PlayerDocument jugadorConActualizaciones = PlayerDocument.builder()
                .idPlayer("7d33913d-32fe-470d-beb2-32854c5c4a2a")
                .nombre("Pedro")
                .tipo(PlayerType.MEDICO)
                .level(Level.ELCRACKLOMODEPLATA)
                .mySkins(new HashSet<>())
                .build();
        when(converter.fromPlayerDocumentToDto(jugadorConActualizaciones)).thenReturn(new PlayerDto(
                jugadorConActualizaciones.getNombre(),
                jugadorConActualizaciones.getTipo(),
                jugadorConActualizaciones.getLevel()
        ));
        PlayerDto result = playerService.updatePLayer(jugadorConActualizaciones.getIdPlayer(),jugadorConActualizaciones.getNombre(), jugadorConActualizaciones.getTipo(), jugadorConActualizaciones.getLevel());
        assertEquals("Pedro", result.getNombre());
        verify(playerRepository).save(jugadorConActualizaciones);
    }


    @Test
    public void testGetAllPlayers() {
        PlayerDocument playerDocument1 = new PlayerDocument("1", "Player1", PlayerType.AVENTURERO, Level.PRINCIPIANTE,new HashSet<>());
        PlayerDocument playerDocument2 = new PlayerDocument("2", "Player2", PlayerType.GUERRERO, Level.EXPERIMENTADO,new HashSet<>());

        when(playerRepository.findAll()).thenReturn(new ArrayList<>(Set.of(playerDocument1, playerDocument2)));
        when(converter.fromPlayerDocumentToDto(playerDocument1)).thenReturn(new PlayerDto("Player1", PlayerType.AVENTURERO,Level.PRINCIPIANTE));
        when(converter.fromPlayerDocumentToDto(playerDocument2)).thenReturn(new PlayerDto("Player2", PlayerType.GUERRERO,Level.EXPERIMENTADO));

        Set<PlayerDto> players = playerService.getAllPlayers();

        assertEquals(2, players.size());
    }

    @Test
    public void testGetAllPlayersEmpty() {
        when(playerRepository.findAll()).thenReturn(Collections.emptyList());
        Set<PlayerDto> players = playerService.getAllPlayers();
        assertEquals(0, players.size());
    }

}