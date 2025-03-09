package com.example.Desafio_Itau.Controler;


import com.example.Desafio_Itau.Service.EstatisticaService;
import com.example.Desafio_Itau.models.TransacaoDTO;
import com.example.Desafio_Itau.models.EstatisticaDTO;
import com.example.Desafio_Itau.Service.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class ApiControler {

    @Autowired
    private ApiService apiService;

    @Autowired
    private EstatisticaService estatisticaService;

    @PostMapping(value= "/transacao")
    @Operation(description="Endpoint responsável por adicionar transações.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Transação gravada com sucesso"),
            @ApiResponse(responseCode = "422", description = "campos não atendem requisitos da transação"),
            @ApiResponse(responseCode = "400", description = "Erro de Requisição"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    public ResponseEntity addTransacao(@RequestBody @Valid TransacaoDTO transacao){
        apiService.addData(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping(value= "/transacao")
    @Operation(description="Endpoint responsável por deletar transações.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Transação deletadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de Requisição"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    public ResponseEntity deleteTransacao(){
        apiService.deleteData();
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping (value="/estatistica")
    @Operation(description="Endpoint responsável por buscar estatísticas transações.")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Recuperadas com Sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de Busca de Estatísticas"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    public ResponseEntity<EstatisticaDTO> estatisticas(@RequestParam(required = false, defaultValue = "60") Integer time ){
       return ResponseEntity.status(HttpStatus.OK).body(estatisticaService.estatisticas(time));
    }
}
