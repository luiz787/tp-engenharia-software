package br.ufmg.dcc.saracura.controller;

import br.ufmg.dcc.saracura.domain.Consulta;
import br.ufmg.dcc.saracura.dto.RequisicaoConsulta;
import br.ufmg.dcc.saracura.service.ConsultaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Consulta Controller")
@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @PostMapping("/")
    public ResponseEntity<Consulta> marcarConsulta(@RequestBody @ApiParam("Requisição de marcação de consulta") final RequisicaoConsulta requisicaoConsulta) {
        return ResponseEntity.ok(consultaService.marcarConsulta(requisicaoConsulta));
    }
}
