package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.exceptions.BadUUIDException;
import Skin.VideoGame.helper.Converter;
import Skin.VideoGame.repositories.SkinRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SkinServiceImplTest {

    @InjectMocks
    private SkinServiceImpl skinService;
    @Mock
    private SkinRepository skinRepository;
    @Mock
    private Converter converter;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testCreateNewSkin() {
        String nombre = "TestSkin";
        TipoSkin tipoSkin = TipoSkin.ALAS;
        ColorSkin colorSkin = ColorSkin.AMARILLO;
        double precio = 29.99;

        SkinDocument savedSkinDocument = new SkinDocument();
        when(skinRepository.save(any(SkinDocument.class))).thenReturn(savedSkinDocument);

        SkinDto expectedSkinDto = new SkinDto();
        when(converter.toSkinDto(savedSkinDocument)).thenReturn(expectedSkinDto);

        SkinDto result = skinService.createNewSkin(nombre, tipoSkin, colorSkin, precio);

        verify(skinRepository).save(any(SkinDocument.class));
        verify(converter).toSkinDto(savedSkinDocument);

        Assert.assertEquals(expectedSkinDto, result);
    }

    @Test
    public void testDeleteSkinSuccess() throws BadUUIDException {
        String validUUID = "550e8400-e29b-41d4-a716-446655440000";
        SkinDocument skin = new SkinDocument();
        doNothing().when(converter).validateUUID(validUUID);
        when(skinRepository.findById(validUUID)).thenReturn(java.util.Optional.of(skin));
        skinService.deleteSkin(validUUID);
        verify(skinRepository, Mockito.times(1)).delete(skin);
    }

    @Test(expected = BadUUIDException.class)
    public void testDeleteSkinInvalidUUID() throws BadUUIDException {
        String invalidUUID = "invalid-uuid-format";
        Mockito.doThrow(new BadUUIDException("Invalid ID format")).when(converter).validateUUID(invalidUUID);
       skinService.deleteSkin(invalidUUID);
    }

    @Test
    public void testUpdateSkin() throws BadUUIDException {
        SkinDocument existingSkin = new SkinDocument("7d33913d-32fe-470d-beb2-32854c5c4a2a", "Roca ambulante", TipoSkin.ARMADURA, ColorSkin.NARANJA, 80.2);

        doNothing().when(converter).validateUUID(existingSkin.getIdSkin());

        when(skinRepository.findById(existingSkin.getIdSkin())).thenReturn(Optional.of(existingSkin));

        SkinDocument updatedSkin = new SkinDocument("7d33913d-32fe-470d-beb2-32854c5c4a2a", "Vuelo de halcón", TipoSkin.ALAS, ColorSkin.NEGRO, 700.0);

        when(converter.toSkinDto(updatedSkin)).thenReturn(new SkinDto(
                updatedSkin.getIdSkin(),
                updatedSkin.getNombre(),
                updatedSkin.getTipos(),
                updatedSkin.getColor(),
                updatedSkin.getPrecio()
        ));

        SkinDto result = skinService.updateSkin(updatedSkin);
        assertEquals("Vuelo de halcón", result.getNombre());
        verify(skinRepository).save(updatedSkin);
    }

}
