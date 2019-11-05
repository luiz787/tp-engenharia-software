package br.ufmg.dcc.saracura.controller;

import br.ufmg.dcc.saracura.domain.Paciente;
import br.ufmg.dcc.saracura.service.PacienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("Paciente Controller")
@RestController
@RequestMapping("/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping("/")
    public ResponseEntity<Paciente> cadastrarPaciente(@RequestBody @ApiParam("Paciente a ser cadastrado") final Paciente paciente) {
        return ResponseEntity.ok(pacienteService.cadastrarPaciente(paciente));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Paciente> obterPaciente(@PathVariable("cpf") @ApiParam("CPF do paciente") final String cpf) {
        return ResponseEntity.ok(pacienteService.consultarPacientePorCpf(cpf));
    }
}
