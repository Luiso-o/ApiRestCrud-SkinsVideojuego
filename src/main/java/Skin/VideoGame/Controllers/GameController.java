package Skin.VideoGame.Controllers;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.exceptions.BadUUIDException;
import Skin.VideoGame.exceptions.SkinNotFoundException;
import Skin.VideoGame.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "game/skins")
public class GameController {

    @Autowired
    private GameService gameService;

    @Operation(summary = "See all available skins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of skins."),
    })
    @GetMapping(value = "available")
    public ResponseEntity<Set<SkinDto>>seeAllTheAvailableSkins() {
        Set<SkinDto> availableSkins = gameService.viewAllAvailableSkins();
        return ResponseEntity.status(HttpStatus.OK).body(availableSkins);
    }

    @Operation(summary = "Buy a new Skin for a player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Skin purchased."),
    })
    @PostMapping(value = "buy")
    public ResponseEntity<Map<String,Object>> buyANewSkinForAPlayer(
            @RequestParam String idPlayer,
            @RequestParam String idSkin
    ) throws BadUUIDException {
        SkinDto myNewSkin = gameService.buyNewSkinForAPLayer(idPlayer,idSkin);
        Map<String,Object> response = new HashMap<>();
        response.put("Nueva skin comprada! ", myNewSkin);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "See all the player's skins.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of player's skins"),
    })
    @GetMapping(value = "myskins")
    public ResponseEntity<Set<SkinDto>>seeAllThePlayersSkins(
            @RequestParam String idPlayer
    ) throws BadUUIDException {
        Set<SkinDto> mySkins = gameService.viewAllThePlayersSkins(idPlayer);
        return ResponseEntity.status(HttpStatus.OK).body(mySkins);
    }

    @Operation(summary = "Change the color of a player's skin.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful color change."),
            @ApiResponse(responseCode = "500", description = "Internal error, check the console."),
    })
    @PutMapping(value = "color")
    public ResponseEntity<String>changeTheColorOfAPlayersSkin(
            @RequestParam String idPlayer,
            @RequestParam String idSkin,
            @RequestParam ColorSkin newColor
    ) throws BadUUIDException, SkinNotFoundException {
        gameService.changePurchasedSkinColor(idPlayer,idSkin,newColor);
        return ResponseEntity.status(HttpStatus.OK).body("Cambio de color exitoso!!!");
    }

    @Operation(summary = "Remove a purchased skin from a player.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin successfully removed."),
            @ApiResponse(responseCode = "500", description = "Internal error, check the console."),
    })
    @DeleteMapping(value = "delete")
    public ResponseEntity<String>removeAPurchasedSkinFromAPlayer(
            @RequestParam String idPlayer,
            @RequestParam String idSkin
    ) throws BadUUIDException, SkinNotFoundException {
        gameService.removeAPurchasedSkinFromAPlayer(idPlayer, idSkin);
        return ResponseEntity.status(HttpStatus.OK).body("Skin eliminada exitosamente!!!");
    }


}
