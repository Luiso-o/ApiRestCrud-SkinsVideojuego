package Skin.VideoGame.Controllers;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.service.SkinServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * @author Luis
 */
@RestController
@RequestMapping(value = "skins")
public class SkinController {
    @Autowired
    private SkinServiceImpl skinService;

    @Operation(summary = "Create a new skin in the game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Skin created"),
    })
    @PostMapping(value = "add")
    public ResponseEntity<Map<String,Object>>createNewSkin(
            @RequestParam (required = false, defaultValue = "Mi nuevo implemento") String nombre,
            @RequestParam TipoSkin tipoSkin,
            @RequestParam ColorSkin colorSkin,
            @RequestParam double precio
            ){
        SkinDto mySkin = skinService.createNewSkin(nombre,tipoSkin,colorSkin,precio);
        Map<String,Object> newSkin = new HashMap<>();
        newSkin.put("Nueva skin creada", mySkin);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSkin);
    }

    @Operation(summary = "Delete a skin of the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin deleted"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @DeleteMapping(value = "delete")
    public ResponseEntity<String>deleteSkin(@RequestParam String idSkin) throws Exception{
        skinService.deleteSkin(idSkin);
        return ResponseEntity.status(HttpStatus.OK).body("Skin deleted");
    }

    @Operation(summary = "Update a skin using for this id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successful"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    @PutMapping (value = "update")
    public ResponseEntity<Map<String,Object>>updateSkinFromId(
            @RequestParam String idSkin,
            @RequestParam (required = false, defaultValue = "Mi nuevo implemento") String nombre,
            @RequestParam TipoSkin tipoSkin,
            @RequestParam ColorSkin colorSkin,
            @RequestParam double precio
    ) throws Exception {
        SkinDto updateSkin = skinService.updateSkin(new SkinDocument(idSkin,nombre,tipoSkin,colorSkin,precio));
        Map<String,Object> response = new HashMap<>();
        response.put("Nueva skin creada", updateSkin);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Select all Skins of the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of skins"),
    })
    @GetMapping(value = "getAll")
    public ResponseEntity<Set<SkinDto>>getAllSkins(){
        Set<SkinDto> mySkins = skinService.findListSkins();
        return ResponseEntity.status(HttpStatus.OK).body(mySkins);
    }

}
