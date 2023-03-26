package com.marcosfausto.helpdesk.services;

import com.marcosfausto.helpdesk.domain.Pessoa;
import com.marcosfausto.helpdesk.domain.Cliente;
import com.marcosfausto.helpdesk.domain.dtos.ClienteDTO;
import com.marcosfausto.helpdesk.repositories.PessoaRepository;
import com.marcosfausto.helpdesk.repositories.ClienteRepository;
import com.marcosfausto.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.marcosfausto.helpdesk.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PessoaRepository pessoaRepository;
    private final BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    public Cliente create(ClienteDTO clienteDTO) {
        clienteDTO.setId(null);
        clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
        validaCpfEEmail(clienteDTO);
        return clienteRepository.save(new Cliente(clienteDTO));
    }

    public Cliente update(Integer id, @Valid ClienteDTO clienteDTO) {
        clienteDTO.setId(id);
        Cliente oldObj = findById(id);

        if(!clienteDTO.getSenha().equals(oldObj.getSenha())) {
            clienteDTO.setSenha(encoder.encode(clienteDTO.getSenha()));
        }

        validaCpfEEmail(clienteDTO);
        oldObj = new Cliente(clienteDTO);
        return clienteRepository.save(oldObj);
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
