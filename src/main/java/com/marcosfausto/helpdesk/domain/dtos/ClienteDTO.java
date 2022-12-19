package com.marcosfausto.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcosfausto.helpdesk.domain.Cliente;
import com.marcosfausto.helpdesk.domain.enums.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {

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

    public ClienteDTO(Cliente cliente) {
            this.id = cliente.getId();
            this.nome = cliente.getNome();
            this.cpf = cliente.getCpf();
            this.email = cliente.getEmail();
            this.senha = cliente.getSenha();
            this.perfis = cliente.getPerfis().stream().map(Perfil::getCodigo).collect(Collectors.toSet());
            this.dataCriacao = cliente.getDataCriacao();
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void setPerfis(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

}
