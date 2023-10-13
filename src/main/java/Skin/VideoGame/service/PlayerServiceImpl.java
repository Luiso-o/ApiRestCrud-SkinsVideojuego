package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.PlayerDto;
import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import Skin.VideoGame.exceptions.BadUUIDException;
import Skin.VideoGame.exceptions.DocumentNotFoundByIdException;
import Skin.VideoGame.helper.Converter;
import Skin.VideoGame.repositories.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private Converter converter;

    public PlayerDto createNewPlayer(String nombre, PlayerType type) {
        PlayerDocument playerDocument = playerRepository.save(PlayerDocument.builder()
                        .idPlayer(converter.generateUUIDRandom())
                        .nombre(nombre)
                        .tipo(type)
                        .level(Level.PRINCIPIANTE)
                        .mySkins(new HashSet<>())
                        .build());
        log.info("Jugador creado correctamente");
        return converter.fromPlayerDocumentToDto(playerDocument);
    }

    public void deletePlayer(String idPlayer) throws BadUUIDException {
    converter.validateUUID(idPlayer);
    PlayerDocument playerDocument = findPlayerById(idPlayer);
    log.info(playerDocument + " eliminado correctamente");
    playerRepository.delete(playerDocument);
    }

    public PlayerDto updatePLayer(String id, String nombre, PlayerType tipo, Level level) throws BadUUIDException {
        converter.validateUUID(id);
        PlayerDocument playerToUpdate = findPlayerById(id);

        playerToUpdate.setNombre(nombre);
        playerToUpdate.setTipo(tipo);
        playerToUpdate.setLevel(level);

        playerRepository.save(playerToUpdate);
        log.info("Jugador actualizado correctamente");
        return converter.fromPlayerDocumentToDto(playerToUpdate);
    }

    public Set<PlayerDto> getAllPlayers() {
        Set<PlayerDocument> players = new HashSet<>(playerRepository.findAll());
        if (players.isEmpty()) {
            log.info("La lista de jugadores está vacía.");
            return Collections.emptySet();
        }
        return players.stream().map(playerDocument -> converter.fromPlayerDocumentToDto(playerDocument))
                .collect(Collectors.toSet());
    }

    public PlayerDocument findPlayerById(String idPlayer) {
        return playerRepository.findById(idPlayer)
                .orElseThrow(() -> new DocumentNotFoundByIdException(idPlayer + " No se encontró o no pertenece a ningún jugador de la base de datos."));

    }
}
