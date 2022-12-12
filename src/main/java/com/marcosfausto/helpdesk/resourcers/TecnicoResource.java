package com.marcosfausto.helpdesk.resourcers;

import com.marcosfausto.helpdesk.domain.Tecnico;
import com.marcosfausto.helpdesk.domain.dtos.TecnicoDTO;
import com.marcosfausto.helpdesk.services.TecnicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

    private final TecnicoService tecnicoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(new TecnicoDTO(tecnicoService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        return ResponseEntity.ok().body(tecnicoService.findAll().stream().map(TecnicoDTO::new).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<TecnicoDTO> create(@RequestBody TecnicoDTO tecnicoDTO) {
        Tecnico tecnico = tecnicoService.create(tecnicoDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(tecnico.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
