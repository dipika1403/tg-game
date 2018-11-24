package com.galvanize.characterservice.controller;

import com.galvanize.characterservice.entity.Hero;
import com.galvanize.characterservice.exception.BadRequestParameterException;
import com.galvanize.characterservice.service.HeroService;
import com.galvanize.tggame.entity.ClassCharacter;
import com.galvanize.tggame.entity.HeroEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HeroController.class, secure = false)
public class HeroControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeroControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HeroService heroService;

    @Test
    public void generateHeroWithCorrectClassTest() throws Exception {
        Hero mockHero = Hero.builder().id(42L).name("test").classCharacter(ClassCharacter.WARRIOR).build();
        String mockNameHero = "someName";
        String mockClassHero = "Warrior";
        HeroEntity mockHeroEntity = HeroEntity.builder().id(42L).name("test").classCharacter(ClassCharacter.WARRIOR).build();
        when(heroService.generateHeroByNameAndClass(mockNameHero,mockClassHero)).thenReturn(mockHero);
        when(heroService.saveHero(mockHero)).thenReturn(mockHeroEntity);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/character/gen/{name}/{class}", mockNameHero, mockClassHero)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();
        verify(heroService, Mockito.times(1)).generateHeroByNameAndClass(mockNameHero,mockClassHero);
    }

    @Test
    public void generateHeroWithWrongClassTest() throws Exception {
//        HeroEntity mockHero = HeroEntity.builder().id(42L).name("test").classCharacter(ClassCharacter.WARRIOR).build();
        String mockNameHero = "someName";
        String mockClassHero = "Warior";
        when(heroService.generateHeroByNameAndClass(mockNameHero,mockClassHero)).thenThrow(BadRequestParameterException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/character/gen/{name}/{class}", mockNameHero, mockClassHero)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
        verify(heroService, Mockito.times(1)).generateHeroByNameAndClass(mockNameHero,mockClassHero);
    }
}