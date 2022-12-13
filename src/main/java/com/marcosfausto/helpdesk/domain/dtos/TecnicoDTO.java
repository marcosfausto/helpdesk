package com.marcosfausto.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.domain.enums.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TecnicoDTO {

    protected Integer id;
    @NotBlank(message = "O campo nome é requerido!")
    protected String nome;
    @NotBlank(message = "O campo cpf é requerido!")
    protected String cpf;
    @NotBlank(message = "O campo email é requerido!")
    protected String email;
    @NotBlank(message = "O campo senha é requerido!")
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
