package br.ufmg.dcc.saracura.controller;

import br.ufmg.dcc.saracura.domain.Medico;
import br.ufmg.dcc.saracura.service.MedicoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("Medico Controller")
@RestController
@RequestMapping("/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @ApiOperation("Método responsável por buscar um médico por CRM.")
    @GetMapping("/{crm}")
    public ResponseEntity<Medico> obterMedicoPorCrm(@PathVariable @ApiParam("CRM do médico.") final String crm) {
        return ResponseEntity.ok(medicoService.obterMedico(crm));
    }

    @ApiOperation("Método responsável por cadastrar um novo médico no registro da clínica.")
    @PostMapping("/")
    public ResponseEntity<Medico> cadastrarMedico(@RequestBody @ApiParam("Médico a ser cadastrado.") final Medico medico) {
        return ResponseEntity.ok(medicoService.cadastrarMedico(medico));
    }

    @ApiOperation("Método responsável por remover um medico do registro da clínica.")
    @DeleteMapping("/{crm}")
    public ResponseEntity<Void> excluirMedico(@PathVariable @ApiParam("CRM do médico a ser excluído.") final String crm) {
        medicoService.excluirMedico(crm);
        return ResponseEntity.noContent().build();
    }

}
