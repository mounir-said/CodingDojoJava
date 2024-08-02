package com.codingdojo.dojoninja.services;


import org.springframework.stereotype.Service;

import com.codingdojo.dojoninja.models.Ninja;
import com.codingdojo.dojoninja.repositories.NinjaRepository;

@Service
public class NinjaService {
    private final NinjaRepository ninjaRepository;

    public NinjaService(NinjaRepository ninjaRepository) {
        this.ninjaRepository = ninjaRepository;
    }

    public Ninja createNinja(Ninja ninja) {
        return ninjaRepository.save(ninja);
    }
}

