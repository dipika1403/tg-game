package com.galvanize.characterservice.repository;

import com.galvanize.characterservice.entity.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends CrudRepository<Hero, Long> {
}
