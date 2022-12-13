package com.marcosfausto.helpdesk.services;

import com.marcosfausto.helpdesk.domain.Pessoa;
import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.domain.dtos.TecnicoDTO;
import com.marcosfausto.helpdesk.repositories.PessoaRepository;
import com.marcosfausto.helpdesk.repositories.TecnicoRepository;
import com.marcosfausto.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marcosfausto.helpdesk.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final PessoaRepository pessoaRepository;


    public Tecnico findById(Integer id) {
        return tecnicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }
    public Tecnico create(TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(null);
        validaCpfEEmail(tecnicoDTO);
        return tecnicoRepository.save(new Tecnico(tecnicoDTO));
    }

    public void validaCpfEEmail(TecnicoDTO tecnicoDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(tecnicoDTO.getCpf());
        if (pessoa.isPresent() && !pessoa.get().getId().equals(tecnicoDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        pessoa = pessoaRepository.findByEmail(tecnicoDTO.getEmail());
        if (pessoa.isPresent() && !pessoa.get().getId().equals(tecnicoDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }

}
