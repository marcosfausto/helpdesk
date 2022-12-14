package com.marcosfausto.helpdesk.domain.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcosfausto.helpdesk.domain.Chamado;
import com.marcosfausto.helpdesk.domain.Cliente;
import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.domain.enums.Prioridade;
import com.marcosfausto.helpdesk.domain.enums.Status;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ChamadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;
    @NotNull(message = "O campo PRIORIDADE É requerido")
    private Integer prioridade;
    @NotNull(message = "O campo STATUS É requerido")
    private Integer status;
    @NotBlank(message = "O campo TITULO É requerido")
    private String titulo;
    @NotBlank(message = "O campo OBSERVACOES É requerido")
    private String observacoes;
    @NotNull(message = "O campo TECNICO É requerido")
    private Integer tecnico;
    @NotNull(message = "O campo CLIENTE É requerido")
    private Integer cliente;
    private String nomeTecnico;
    private String nomeCliente;

    public ChamadoDTO(Chamado chamado) {
        this.id = chamado.getId();
        this.dataAbertura = chamado.getDataAbertura();
        this.dataFechamento = chamado.getDataFechamento();
        this.prioridade = chamado.getPrioridade().getCodigo();
        this.status = chamado.getStatus().getCodigo();
        this.titulo = chamado.getTitulo();
        this.observacoes = chamado.getObservacoes();
        this.tecnico = chamado.getTecnico().getId();
        this.cliente = chamado.getCliente().getId();
        this.nomeCliente = chamado.getCliente().getNome();
        this.nomeTecnico = chamado.getTecnico().getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChamadoDTO chamado = (ChamadoDTO) o;
        return id != null && Objects.equals(id, chamado.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
