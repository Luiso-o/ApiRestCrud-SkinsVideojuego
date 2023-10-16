package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.exceptions.BadUUIDException;
import Skin.VideoGame.exceptions.SkinNotFoundException;
import Skin.VideoGame.helper.Converter;
import Skin.VideoGame.repositories.PlayerRepository;
import Skin.VideoGame.repositories.SkinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Luis
 */
@Service
public class GameService {
    private static final Logger log = LoggerFactory.getLogger(GameService.class);
    @Autowired
    private SkinServiceImpl skinService;
    @Autowired
    private PlayerServiceImpl playerService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SkinRepository skinRepository;
    @Autowired
    private Converter converter;

    public Set<SkinDto>viewAllAvailableSkins(){
        return skinService.findListSkins();
    }

    public SkinDto buyNewSkinForAPLayer(String idPlayer, String idSkin) throws BadUUIDException {
        converter.validatePlayerUUID(idPlayer);
        converter.validateSkinUUID(idSkin);
        SkinDocument myNewSkin = skinService.findSkinById(idSkin);
        PlayerDocument player = playerService.findPlayerById(idPlayer);

        if (player.getMySkins() == null) {
            player.setMySkins(new HashSet<>());
        }

        player.getMySkins().add(myNewSkin);
        playerRepository.save(player);
        log.info("Nueva Skin adquirida para " + player.getNombre() + " " + myNewSkin.getNombre());
        return converter.fromSkinDocumentToDto(myNewSkin);
    }

    public Set<SkinDto> viewAllThePlayersSkins(String idPLayer) throws BadUUIDException {
        converter.validatePlayerUUID(idPLayer);
        PlayerDocument player = playerService.findPlayerById(idPLayer);
        return player.getMySkins().stream()
                .map(converter::fromSkinDocumentToDto)
                .collect(Collectors.toSet());
    }

    public void changePurchasedSkinColor(String idPlayer,String idSkin, ColorSkin newColor) throws BadUUIDException, SkinNotFoundException {
     converter.validateSkinUUID(idSkin);
     converter.validatePlayerUUID(idPlayer);

     PlayerDocument player = playerService.findPlayerById(idPlayer);

     Optional<SkinDocument> matchingSkin = player.getMySkins().stream()
             .filter(skin -> skin.getIdSkin().equals(idSkin))
             .findFirst();

        if (matchingSkin.isPresent()) {
            SkinDocument skinToUpdate = matchingSkin.get();
            skinToUpdate.setColor(newColor);
            playerRepository.save(player);
        } else {
           log.error("El skin especificado no pertenece al jugador.");
            throw new SkinNotFoundException("El skin especificado no pertenece al jugador.");
        }
    }

    public void removeAPurchasedSkinFromAPlayer(String idPlayer, String idSkin) throws BadUUIDException, SkinNotFoundException {
        converter.validateSkinUUID(idSkin);
        converter.validatePlayerUUID(idPlayer);

        PlayerDocument player = playerService.findPlayerById(idPlayer);
        Optional<SkinDocument> matchingSkin = player.getMySkins().stream()
                .filter(skin -> skin.getIdSkin().equals(idSkin))
                .findFirst();

        if (matchingSkin.isPresent()) {
            SkinDocument skinToDelete = matchingSkin.get();
            player.getMySkins().remove(skinToDelete);
            playerRepository.save(player);
        } else {
            log.error("El skin especificado no pertenece al jugador.");
            throw new SkinNotFoundException("El skin especificado no pertenece al jugador.");
        }
    }

}
