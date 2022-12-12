package com.marcosfausto.helpdesk.services;

import com.marcosfausto.helpdesk.domain.Chamado;
import com.marcosfausto.helpdesk.domain.Cliente;
import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.domain.enums.Perfil;
import com.marcosfausto.helpdesk.domain.enums.Prioridade;
import com.marcosfausto.helpdesk.domain.enums.Status;
import com.marcosfausto.helpdesk.repositories.ChamadoRepository;
import com.marcosfausto.helpdesk.repositories.ClienteRepository;
import com.marcosfausto.helpdesk.repositories.TecnicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class DBService {

    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;
    private final ChamadoRepository chamadoRepository;


    public void instaciaDB() {
        Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "55048215095", "valdir@mail.com", "123");
        tec1.setPerfis(Perfil.ADMIN);
        Tecnico tec2 = new Tecnico(null, "Richard Stallman", "903.347.070-56", "stallman@mail.com", "123");
        Tecnico tec3 = new Tecnico(null, "Claude Elwood Shannon", "271.068.470-54", "shannon@mail.com", "123");
        Tecnico tec4 = new Tecnico(null, "Tim Berners-Lee", "162.720.120-39", "lee@mail.com", "123");
        Tecnico tec5 = new Tecnico(null, "Linus Torvalds", "778.556.170-27", "linus@mail.com", "123");

        Cliente cli1 = new Cliente(null, "Linus Torvalds", "11166189074", "torvalds@mail.com", "123");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);

        tecnicoRepository.saveAll(Arrays.asList(tec1,tec2,tec3,tec4,tec5));
        clienteRepository.save(cli1);
        chamadoRepository.save(c1);

    }
}
