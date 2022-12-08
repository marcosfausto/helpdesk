package com.marcosfausto.helpdesk.domain;

import com.marcosfausto.helpdesk.domain.enums.Perfil;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa {

    @EqualsAndHashCode.Include()
    protected Integer id;
    protected String nome;
    @EqualsAndHashCode.Include()
    protected String cpf;
    protected String email;
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();
    protected LocalDate dataCriacao = LocalDate.now();

    public Pessoa() {
        setPerfis(Perfil.CLIENTE);
    }

    public Pessoa(Integer id, String nome, String cpf, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        setPerfis(Perfil.CLIENTE);
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void setPerfis(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }
}
