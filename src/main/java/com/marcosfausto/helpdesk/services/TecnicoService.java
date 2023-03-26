package com.marcosfausto.helpdesk.services;

import com.marcosfausto.helpdesk.domain.Pessoa;
import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.domain.dtos.TecnicoDTO;
import com.marcosfausto.helpdesk.repositories.PessoaRepository;
import com.marcosfausto.helpdesk.repositories.TecnicoRepository;
import com.marcosfausto.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marcosfausto.helpdesk.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionalException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;
    private final PessoaRepository pessoaRepository;
    private final BCryptPasswordEncoder encoder;

    public Tecnico findById(Integer id) {
        return tecnicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }
    public Tecnico create(TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(null);
        tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
        validaCpfEEmail(tecnicoDTO);
        return tecnicoRepository.save(new Tecnico(tecnicoDTO));
    }

    public Tecnico update(Integer id, TecnicoDTO tecnicoDTO) {
        tecnicoDTO.setId(id);
        Tecnico oldObj = findById(id);

        if(!tecnicoDTO.getSenha().equals(oldObj.getSenha())) {
            tecnicoDTO.setSenha(encoder.encode(tecnicoDTO.getSenha()));
        }

        validaCpfEEmail(tecnicoDTO);
        oldObj = new Tecnico(tecnicoDTO);
        return tecnicoRepository.save(oldObj);
    }

    public void delete(Integer id) {
        if (findById(id).getChamados().size() > 0 ) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
        }
        try {
            tecnicoRepository.delete(findById(id));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possivel excluir porque há entidades relacionadas");
        }
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
