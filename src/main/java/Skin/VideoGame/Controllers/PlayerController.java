package Skin.VideoGame.Controllers;

import Skin.VideoGame.Dtos.PlayerDto;
import Skin.VideoGame.documents.PlayerDocument;
import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import Skin.VideoGame.service.PlayerServiceImpl;
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
@RequestMapping(value = "players")
public class PlayerController {
    @Autowired
    private PlayerServiceImpl playerService;

    @Operation(summary = "Create a new Player in the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PLayer created"),
    })
    @PostMapping(value = "add")
    public ResponseEntity<Map<String,Object>> createNewPLayer(
            @RequestParam(required = false, defaultValue = "Nuevo Jugador") String nombre,
            @RequestParam PlayerType playerType
            ){
        PlayerDto playerDto = playerService.createNewPlayer(nombre,playerType);
        Map<String,Object> newSkin = new HashMap<>();
        newSkin.put("Nuevo jugador Listo para la aventura! ",playerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSkin);
    }

    @Operation(summary = "Delete a player of the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player deleted successfully."),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @DeleteMapping(value = "delete")
    public ResponseEntity<String>deletePlayer(@RequestParam String idPLayer) throws Exception{
        playerService.deletePlayer(idPLayer);
        return ResponseEntity.status(HttpStatus.OK).body("Player deleted successfully.");
    }

    @Operation(summary = "Update a player using for this id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successful"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PutMapping(value = "update")
    public ResponseEntity<Map<String,Object>>updatePlayerFromId(
            @RequestParam String idJugador,
            @RequestParam (required = false, defaultValue = "Nuevo Jugador") String nombre,
            @RequestParam PlayerType playerType,
            @RequestParam Level level
    ) throws Exception {
        PlayerDto player = playerService.updatePLayer(idJugador,nombre,playerType,level);
        Map<String,Object> response = new HashMap<>();
        response.put("Jugador modificado exitosamente", player);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Select a player from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful process."),
            @ApiResponse(responseCode = "500", description = "Internal error"),
    })
    @GetMapping(value = "getOne")
    public ResponseEntity<PlayerDocument>getAPlayer(
            @RequestParam String idPlayer
    ){
        PlayerDocument player = playerService.findPlayerById(idPlayer);
        return ResponseEntity.status(HttpStatus.OK).body(player);
    }

    @Operation(summary = "Select all players of the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of players"),
    })
    @GetMapping(value = "getAll")
    public ResponseEntity<Set<PlayerDto>>getAllPlayers(){
        Set<PlayerDto> myPlayers = playerService.getAllPlayers();
        return ResponseEntity.status(HttpStatus.OK).body(myPlayers);
    }

}
