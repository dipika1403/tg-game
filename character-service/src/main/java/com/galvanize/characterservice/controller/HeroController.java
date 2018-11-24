package com.galvanize.characterservice.controller;


import com.galvanize.characterservice.entity.Hero;
import com.galvanize.characterservice.service.HeroService;
import com.galvanize.tggame.entity.HeroEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/character")
public class HeroController {

    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("/gen/{name}/{class}")
    public HeroEntity generateHero(@PathVariable String name,
                                   @PathVariable("class") String classHero) {
        Hero hero = heroService.generateHeroByNameAndClass(name, classHero);
        return heroService.saveHero(hero);
    }

    @GetMapping("/get/{id}")
    public HeroEntity getHeroById(@PathVariable Long id) {
        return heroService.getHeroById(id);
    }

    @GetMapping("/get")
    public Iterable<HeroEntity> getAllHeroes() {
        return heroService.getAllHeroes();
    }

}
