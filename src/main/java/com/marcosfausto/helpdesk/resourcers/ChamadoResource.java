package com.marcosfausto.helpdesk.resourcers;

import com.marcosfausto.helpdesk.domain.Chamado;
import com.marcosfausto.helpdesk.domain.dtos.ChamadoDTO;
import com.marcosfausto.helpdesk.services.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chamados")
public class ChamadoResource {

    private final ChamadoService chamadoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(new ChamadoDTO(chamadoService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ChamadoDTO>> findAll() {
        return ResponseEntity.ok().body(chamadoService.findAll().stream().map(ChamadoDTO::new).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO chamadoDTO) {
        Chamado chamado = chamadoService.create(chamadoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(chamado.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
}
