package Skin.VideoGame.service;

import Skin.VideoGame.Dtos.SkinDto;
import Skin.VideoGame.documents.SkinDocument;
import Skin.VideoGame.enumeraciones.ColorSkin;
import Skin.VideoGame.enumeraciones.TipoSkin;
import Skin.VideoGame.helper.Converter;
import Skin.VideoGame.repositories.SkinRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        Mockito.when(skinRepository.save(Mockito.any(SkinDocument.class))).thenReturn(savedSkinDocument);

        SkinDto expectedSkinDto = new SkinDto();
        Mockito.when(converter.toSkinDto(savedSkinDocument)).thenReturn(expectedSkinDto);

        SkinDto result = skinService.createNewSkin(nombre, tipoSkin, colorSkin, precio);

        Mockito.verify(skinRepository).save(Mockito.any(SkinDocument.class));
        Mockito.verify(converter).toSkinDto(savedSkinDocument);

        Assert.assertEquals(expectedSkinDto, result);
    }
}
