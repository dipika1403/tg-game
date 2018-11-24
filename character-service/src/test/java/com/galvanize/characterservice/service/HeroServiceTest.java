package com.galvanize.characterservice.service;

import com.galvanize.characterservice.entity.Hero;
import com.galvanize.characterservice.exception.BadRequestParameterException;
import com.galvanize.characterservice.repository.HeroRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class HeroServiceTest {
    @MockBean
    HeroRepository heroRepository;
    @Autowired
    HeroService heroService;

    @Test(expected = BadRequestParameterException.class)
    public void generateHeroWithWrongClassTest() {
        String mockNameHero = "mockName";
        String mockClassHero = "wrongClass";
        heroService.generateHeroByNameAndClass(mockNameHero, mockClassHero);
    }

    @Test
    public void saveHeroWarrior() {
        Hero mockHero = Hero.builder().build();
        Hero mockSavedHero = Hero.builder().id(1L).build();
        when(heroRepository.save(mockHero)).thenReturn(mockSavedHero);
        heroService.saveHero(mockHero);
        verify(heroRepository, times(1)).save(mockHero);
    }

    @Test
    public void generateHeroWarrior() {
        String mockNameHero = "mockName";
        String mockClassHero = "Warrior";
        Hero heroEntity = heroService.generateHeroByNameAndClass(mockNameHero, mockClassHero);
        assertThat("expect Strength greater or equals Intelligence", heroEntity.getHeroStr(), is(greaterThanOrEqualTo(heroEntity.getHeroInt())));
        assertThat("expect Strength greater or equals Dexterity", heroEntity.getHeroStr(), is(greaterThanOrEqualTo(heroEntity.getHeroDex())));
        assertThat("expect Strength greater or equals Constitution", heroEntity.getHeroStr(), is(greaterThanOrEqualTo(heroEntity.getHeroCon())));
        assertThat("expect Strength greater or equals Wisdom", heroEntity.getHeroStr(), is(greaterThanOrEqualTo(heroEntity.getHeroWis())));
        assertThat("expect Strength greater or equals Charisma", heroEntity.getHeroStr(), is(greaterThanOrEqualTo(heroEntity.getHeroCha())));
        assertThat("expect Intelligence less or equals Strength", heroEntity.getHeroInt(), is(lessThanOrEqualTo(heroEntity.getHeroStr())));
        assertThat("expect Intelligence less or equals Dexterity", heroEntity.getHeroInt(), is(lessThanOrEqualTo(heroEntity.getHeroDex())));
        assertThat("expect Intelligence less or equals Constitution", heroEntity.getHeroInt(), is(lessThanOrEqualTo(heroEntity.getHeroCon())));
        assertThat("expect Intelligence less or equals Wisdom", heroEntity.getHeroInt(), is(lessThanOrEqualTo(heroEntity.getHeroWis())));
        assertThat("expect Intelligence less or equals Charisma", heroEntity.getHeroInt(), is(lessThanOrEqualTo(heroEntity.getHeroCha())));
        assertThat("expect HitPoint equals twice Constitution", heroEntity.getHeroCon() * 2, is(equalTo(heroEntity.getHitPoints())));
    }

    @Test
    public void generateHeroArcher() {
        String mockNameHero = "mockName";
        String mockClassHero = "Archer";
        Hero heroEntity = heroService.generateHeroByNameAndClass(mockNameHero, mockClassHero);
        assertThat("expect Dexterity greater or equals Intelligence", heroEntity.getHeroDex(), is(greaterThanOrEqualTo(heroEntity.getHeroInt())));
        assertThat("expect Dexterity greater or equals Strength", heroEntity.getHeroDex(), is(greaterThanOrEqualTo(heroEntity.getHeroStr())));
        assertThat("expect Dexterity greater or equals Constitution", heroEntity.getHeroDex(), is(greaterThanOrEqualTo(heroEntity.getHeroCon())));
        assertThat("expect Dexterity greater or equals Wisdom", heroEntity.getHeroDex(), is(greaterThanOrEqualTo(heroEntity.getHeroWis())));
        assertThat("expect Dexterity greater or equals Charisma", heroEntity.getHeroDex(), is(greaterThanOrEqualTo(heroEntity.getHeroCha())));
        assertThat("expect Charisma less or equals Strength", heroEntity.getHeroCha(), is(lessThanOrEqualTo(heroEntity.getHeroStr())));
        assertThat("expect Charisma less or equals Dexterity", heroEntity.getHeroCha(), is(lessThanOrEqualTo(heroEntity.getHeroDex())));
        assertThat("expect Charisma less or equals Constitution", heroEntity.getHeroCha(), is(lessThanOrEqualTo(heroEntity.getHeroCon())));
        assertThat("expect Charisma less or equals Wisdom", heroEntity.getHeroCha(), is(lessThanOrEqualTo(heroEntity.getHeroWis())));
        assertThat("expect Charisma less or equals Intelligence", heroEntity.getHeroCha(), is(lessThanOrEqualTo(heroEntity.getHeroInt())));
        assertThat("expect HitPoint equals twice Constitution", heroEntity.getHeroCon() * 2, is(equalTo(heroEntity.getHitPoints())));
    }

    @Test
    public void generateHeroWizard() {
        String mockNameHero = "mockName";
        String mockClassHero = "Wizard";
        Hero heroEntity = heroService.generateHeroByNameAndClass(mockNameHero, mockClassHero);
        assertThat("expect Intelligence greater or equals Strength", heroEntity.getHeroInt(), is(greaterThanOrEqualTo(heroEntity.getHeroStr())));
        assertThat("expect Intelligence greater or equals Dexterity", heroEntity.getHeroInt(), is(greaterThanOrEqualTo(heroEntity.getHeroDex())));
        assertThat("expect Intelligence greater or equals Constitution", heroEntity.getHeroInt(), is(greaterThanOrEqualTo(heroEntity.getHeroCon())));
        assertThat("expect Intelligence greater or equals Wisdom", heroEntity.getHeroInt(), is(greaterThanOrEqualTo(heroEntity.getHeroWis())));
        assertThat("expect Intelligence greater or equals Charisma", heroEntity.getHeroInt(), is(greaterThanOrEqualTo(heroEntity.getHeroCha())));
        assertThat("expect Strength less or equals Intelligence", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroInt())));
        assertThat("expect Strength less or equals Dexterity", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroDex())));
        assertThat("expect Strength less or equals Constitution", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroCon())));
        assertThat("expect Strength less or equals Wisdom", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroWis())));
        assertThat("expect Strength less or equals Charisma", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroCha())));
        assertThat("expect HitPoint equals twice Constitution", heroEntity.getHeroCon() * 2, is(equalTo(heroEntity.getHitPoints())));
    }

    @Test
    public void generateHeroRogue() {
        String mockNameHero = "mockName";
        String mockClassHero = "Rogue";
        Hero heroEntity = heroService.generateHeroByNameAndClass(mockNameHero, mockClassHero);
        assertThat("expect Charisma greater or equals Intelligence", heroEntity.getHeroCha(), is(greaterThanOrEqualTo(heroEntity.getHeroInt())));
        assertThat("expect Charisma greater or equals Dexterity", heroEntity.getHeroCha(), is(greaterThanOrEqualTo(heroEntity.getHeroDex())));
        assertThat("expect Charisma greater or equals Constitution", heroEntity.getHeroCha(), is(greaterThanOrEqualTo(heroEntity.getHeroCon())));
        assertThat("expect Charisma greater or equals Wisdom", heroEntity.getHeroCha(), is(greaterThanOrEqualTo(heroEntity.getHeroWis())));
        assertThat("expect Charisma greater or equals Strength", heroEntity.getHeroCha(), is(greaterThanOrEqualTo(heroEntity.getHeroStr())));
        assertThat("expect Strength less or equals Intelligence", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroInt())));
        assertThat("expect Strength less or equals Dexterity", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroDex())));
        assertThat("expect Strength less or equals Constitution", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroCon())));
        assertThat("expect Strength less or equals Wisdom", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroWis())));
        assertThat("expect Strength less or equals Charisma", heroEntity.getHeroStr(), is(lessThanOrEqualTo(heroEntity.getHeroCha())));
        assertThat("expect HitPoint equals twice Constitution", heroEntity.getHeroCon() * 2, is(equalTo(heroEntity.getHitPoints())));
    }
}
