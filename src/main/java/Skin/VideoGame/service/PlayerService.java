package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.PlayerDto;
import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import Skin.VideoGame.exceptions.BadUUIDException;

import java.util.Set;

public interface PlayerService {
    PlayerDto createNewPlayer(String nombre, PlayerType playerType);
    void deletePlayer(String idPlayer) throws BadUUIDException;
    PlayerDto updatePLayer(String id, String nombre, PlayerType tipo, Level level) throws BadUUIDException;
    Set<PlayerDto> getAllPlayers();
    PlayerDocument findPlayerById(String idPlayer);
}
