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

@RequiredArgsConstructor
@Service
public class DBService {

    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository;
    private final ChamadoRepository chamadoRepository;


    public void instaciaDB() {
        Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "55048215095", "valdir@mail.com", "123");
        tec1.setPerfis(Perfil.ADMIN);

        Cliente cli1 = new Cliente(null, "Linus Torvalds", "11166189074", "torvalds@mail.com", "123");

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 01", "Primeiro chamado", tec1, cli1);

        tecnicoRepository.save(tec1);
        clienteRepository.save(cli1);
        chamadoRepository.save(c1);

    }
}
