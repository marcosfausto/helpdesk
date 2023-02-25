package com.marcosfausto.helpdesk.services;

import com.marcosfausto.helpdesk.domain.Cliente;
import com.marcosfausto.helpdesk.domain.Pessoa;
import com.marcosfausto.helpdesk.repositories.ClienteRepository;
import com.marcosfausto.helpdesk.repositories.PessoaRepository;
import com.marcosfausto.helpdesk.security.UserSS;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PessoaRepository pessoaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> pessoa = pessoaRepository.findByEmail(email);
        return pessoa.map(value -> new UserSS(value.getId(), value.getEmail(), value.getSenha(), value.getPerfis())).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
    }
}
