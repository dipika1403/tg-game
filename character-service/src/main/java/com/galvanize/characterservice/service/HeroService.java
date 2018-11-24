package com.galvanize.characterservice.service;


import com.galvanize.characterservice.entity.Hero;
import com.galvanize.characterservice.exception.BadRequestParameterException;
import com.galvanize.characterservice.exception.HeroNotFoundException;
import com.galvanize.characterservice.repository.HeroRepository;
import com.galvanize.tggame.entity.ClassCharacter;
import com.galvanize.tggame.entity.HeroEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class HeroService {

    private final HeroRepository heroRepository;

    @Autowired
    public HeroService(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Hero generateHeroByNameAndClass(String name, String classHero) {
        ClassCharacter classCharacter;
        try {
            classCharacter = ClassCharacter.valueOf(classHero.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestParameterException(String.format("Character by class: %s not found", classHero));
        }

        List<Integer> skillList = genSkillList();
        Hero.HeroBuilder heroBuilder = Hero.builder().name(name).classCharacter(classCharacter);
        if (classCharacter == ClassCharacter.WARRIOR) {
            heroBuilder
                    .heroInt(skillList.get(0))
                    .heroStr(skillList.get(1))
                    .heroWis(skillList.get(2))
                    .heroCha(skillList.get(3))
                    .heroDex(skillList.get(4));
        } else if (classCharacter == ClassCharacter.ARCHER) {
            heroBuilder
                    .heroCha(skillList.get(0))
                    .heroDex(skillList.get(1))
                    .heroInt(skillList.get(2))
                    .heroWis(skillList.get(3))
                    .heroStr(skillList.get(4));
        } else if (classCharacter == ClassCharacter.WIZARD) {
            heroBuilder
                    .heroStr(skillList.get(0))
                    .heroInt(skillList.get(1))
                    .heroWis(skillList.get(2))
                    .heroCha(skillList.get(3))
                    .heroDex(skillList.get(4));
        } else if (classCharacter == ClassCharacter.ROGUE) {
            heroBuilder
                    .heroStr(skillList.get(0))
                    .heroCha(skillList.get(1))
                    .heroInt(skillList.get(2))
                    .heroWis(skillList.get(3))
                    .heroDex(skillList.get(4));
        }
        heroBuilder
                .heroCon(skillList.get(5))
                .hitPoints(skillList.get(5) * 2);

        return heroBuilder.build();
    }

    public HeroEntity saveHero(Hero hero) {
        return createHeroEntity(heroRepository.save(hero));
    }

    private HeroEntity createHeroEntity(Hero hero) {
        return HeroEntity.builder()
                .id(hero.getId())
                .name(hero.getName())
                .classCharacter(hero.getClassCharacter())
                .heroInt(hero.getHeroInt())
                .heroCha(hero.getHeroCha())
                .heroCon(hero.getHeroCon())
                .heroDex(hero.getHeroDex())
                .heroStr(hero.getHeroStr())
                .heroWis(hero.getHeroWis())
                .inventory(new ArrayList<>())
                .hitPoints(hero.getHitPoints())
                .location(hero.getLocation())
                .build();
    }

    private List<Integer> genSkillList() {
        Random rnd = new Random();
        List<Integer> arr = rnd.ints(6, 8, 19).boxed().collect(Collectors.toList());
        Integer max = Collections.max(arr);
        arr.remove(max);
        Integer min = Collections.min(arr);
        arr.remove(min);
        arr.add(0, max);
        arr.add(0, min);
        return arr;
    }

    public HeroEntity getHeroById(Long id) {
        Optional<Hero> optHero = heroRepository.findById(id);
        if (!optHero.isPresent()) {
            throw new HeroNotFoundException(String.format("Character was not found by id: %d", id));
        }
        return createHeroEntity(optHero.get());
    }

    public Iterable<HeroEntity> getAllHeroes() {
        return StreamSupport.stream(heroRepository.findAll().spliterator(), false)
                .map(this::createHeroEntity)
                .collect(Collectors.toList());
    }
}
