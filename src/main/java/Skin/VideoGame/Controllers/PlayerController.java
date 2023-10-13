package Skin.VideoGame.Controllers;

import Skin.VideoGame.Dtos.PlayerDto;
import Skin.VideoGame.enumeraciones.Level;
import Skin.VideoGame.enumeraciones.PlayerType;
import Skin.VideoGame.service.PlayerService;
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
    private PlayerService pLayerService;

    @Operation(summary = "Create a new Player in the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PLayer created"),
    })
    @PostMapping(value = "add")
    public ResponseEntity<Map<String,Object>> createNewPLayer(
            @RequestParam(required = false, defaultValue = "Nuevo Jugador") String nombre,
            @RequestParam PlayerType playerType
            ){
        PlayerDto playerDto = pLayerService.createNewPlayer(nombre,playerType);
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
        pLayerService.deletePlayer(idPLayer);
        return ResponseEntity.status(HttpStatus.OK).body("Player deleted successfully.");
    }

    @Operation(summary = "Update a player using for this id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successful"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PostMapping(value = "update")
    public ResponseEntity<Map<String,Object>>updatePlayerFromId(
            @RequestParam String idJugador,
            @RequestParam (required = false, defaultValue = "Nuevo Jugador") String nombre,
            @RequestParam PlayerType playerType,
            @RequestParam Level level
    ) throws Exception {
        PlayerDto player = pLayerService.updatePLayer(idJugador,nombre,playerType,level);
        Map<String,Object> response = new HashMap<>();
        response.put("Jugador modificado exitosamente", player);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Select all players of the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of players"),
    })
    @GetMapping(value = "getAll")
    public ResponseEntity<Set<PlayerDto>>getAllPlayers(){
        Set<PlayerDto> myPlayers = pLayerService.getAllPlayers();
        return ResponseEntity.status(HttpStatus.OK).body(myPlayers);
    }

}
