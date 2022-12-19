package com.marcosfausto.helpdesk.services;

import com.marcosfausto.helpdesk.domain.Chamado;
import com.marcosfausto.helpdesk.domain.Cliente;
import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.domain.dtos.ChamadoDTO;
import com.marcosfausto.helpdesk.domain.dtos.ClienteDTO;
import com.marcosfausto.helpdesk.domain.enums.Prioridade;
import com.marcosfausto.helpdesk.domain.enums.Status;
import com.marcosfausto.helpdesk.repositories.ChamadoRepository;
import com.marcosfausto.helpdesk.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final TecnicoService tecnicoService;
    private final ClienteService clienteService;



    public Chamado findById(Integer id) {
        return chamadoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Chamado> findAll() {
        return chamadoRepository.findAll();
    }

    public Chamado create(ChamadoDTO chamadoDTO) {
        return chamadoRepository.save(newChamado(chamadoDTO));
    }

    public void update(Integer id, ChamadoDTO chamadoDTO) {
        findById(id);
        chamadoDTO.setId(id);
        chamadoRepository.save(newChamado(chamadoDTO));
    }

    private Chamado newChamado(ChamadoDTO chamadoDTO) {
        Tecnico tecnico = tecnicoService.findById(chamadoDTO.getTecnico());
        Cliente cliente = clienteService.findById(chamadoDTO.getCliente());

        Chamado chamado = new Chamado();
        if(chamadoDTO.getId() != null) {
            chamado.setId(chamadoDTO.getId());
        }

        if(chamadoDTO.getStatus().equals(2)) {
            chamado.setDataFechamento(LocalDate.now());
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(chamadoDTO.getPrioridade()));
        chamado.setStatus(Status.toEnum(chamadoDTO.getStatus()));
        chamado.setTitulo(chamadoDTO.getTitulo());
        chamado.setObservacoes(chamadoDTO.getObservacoes());
        return chamado;
    }


}
