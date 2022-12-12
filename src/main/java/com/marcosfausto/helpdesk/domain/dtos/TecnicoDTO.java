package com.marcosfausto.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.domain.enums.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TecnicoDTO {

    protected Integer id;
    protected String nome;
    protected String cpf;
    protected String email;
    protected String senha;
    protected Set<Integer> perfis = new HashSet<>();
    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    public TecnicoDTO(Tecnico tecnico) {
            this.id = tecnico.getId();
            this.nome = tecnico.getNome();
            this.cpf = tecnico.getCpf();
            this.email = tecnico.getEmail();
            this.senha = tecnico.getSenha();
            this.perfis = tecnico.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
            this.dataCriacao = tecnico.getDataCriacao();
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void setPerfis(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

}
