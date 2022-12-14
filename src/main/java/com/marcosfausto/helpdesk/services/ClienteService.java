package com.marcosfausto.helpdesk.services;

import com.marcosfausto.helpdesk.domain.Pessoa;
import com.marcosfausto.helpdesk.domain.Cliente;
import com.marcosfausto.helpdesk.domain.dtos.ClienteDTO;
import com.marcosfausto.helpdesk.repositories.PessoaRepository;
import com.marcosfausto.helpdesk.repositories.ClienteRepository;
import com.marcosfausto.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marcosfausto.helpdesk.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PessoaRepository pessoaRepository;


    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    public Cliente create(ClienteDTO clienteDTO) {
        clienteDTO.setId(null);
        validaCpfEEmail(clienteDTO);
        return clienteRepository.save(new Cliente(clienteDTO));
    }

    public void update(Integer id, ClienteDTO clienteDTO) {
        clienteDTO.setId(findById(id).getId());
        validaCpfEEmail(clienteDTO);
        clienteRepository.save(new Cliente(clienteDTO));
    }

    public void delete(Integer id) {
        if (findById(id).getChamados().size() > 0 ) {
            throw new DataIntegrityViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
        }
        try {
            clienteRepository.delete(findById(id));
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possivel excluir porque há entidades relacionadas");
        }
    }

    public void validaCpfEEmail(ClienteDTO clienteDTO) {
        Optional<Pessoa> pessoa = pessoaRepository.findByCpf(clienteDTO.getCpf());
        if (pessoa.isPresent() && !pessoa.get().getId().equals(clienteDTO.getId())) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
        }
        pessoa = pessoaRepository.findByEmail(clienteDTO.getEmail());
        if (pessoa.isPresent() && !pessoa.get().getId().equals(clienteDTO.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
        }
    }

}
