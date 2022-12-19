package com.marcosfausto.helpdesk.resourcers;

import com.marcosfausto.helpdesk.domain.dtos.ChamadoDTO;
import com.marcosfausto.helpdesk.services.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chamados")
public class ChamadoResource {

    private final ChamadoService chamadoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(new ChamadoDTO(chamadoService.findById(id)));
    }
    
}
