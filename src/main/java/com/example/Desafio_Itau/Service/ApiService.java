package com.example.Desafio_Itau.Service;

import com.example.Desafio_Itau.Exceptions.UnprocessableEntity;
import com.example.Desafio_Itau.models.TransacaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.util.*;

@Service
@Slf4j
public class ApiService {
    private List<TransacaoDTO> lista = new ArrayList<>();

    public ResponseEntity addData(TransacaoDTO transacao){

        log.info("Inicio do processo de adicionar transação");
        if(transacao.dateTime().isAfter(OffsetDateTime.now())){
            log.error("Erro: data hora maior do que data hora atual.");
            throw new UnprocessableEntity("");
        }
        if(transacao.value() < 0){
            log.error("Erro: valor da transação menor do que 0.");
            throw new UnprocessableEntity("");
        }
        lista.add(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public void deleteData(){
        log.info("Iniciando limpeza dos dados.");
        lista.clear();
        log.info("Dados deletados com sucesso.");
    }

    public List<TransacaoDTO> getStats(Integer time){
        log.info("Cáculo do intervalo de tempo.");
        OffsetDateTime intervalo = OffsetDateTime.now().minusSeconds(time);

        log.info("Retorno de lista com transações dentro do intervalo.");
        return lista.stream().filter(transacoes -> transacoes.dateTime().isAfter(intervalo)).toList();
    }

}