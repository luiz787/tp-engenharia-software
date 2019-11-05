package br.ufmg.dcc.saracura.controller;

import br.ufmg.dcc.saracura.domain.Exame;
import br.ufmg.dcc.saracura.dto.RequisicaoExame;
import br.ufmg.dcc.saracura.service.ExameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exames")
@RequiredArgsConstructor
public class ExameController {

    private final ExameService exameService;

    @PostMapping("/")
    public ResponseEntity<Exame> marcarExame(@RequestBody RequisicaoExame requisicaoExame) {
        return ResponseEntity.ok(exameService.marcarExame(requisicaoExame));
    }

}
