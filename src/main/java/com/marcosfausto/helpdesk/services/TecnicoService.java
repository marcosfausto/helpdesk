package com.marcosfausto.helpdesk.services;

import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.repositories.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    public Tecnico findById(Integer id) {
        return tecnicoRepository.findById(id).orElse(null);
    }

}
