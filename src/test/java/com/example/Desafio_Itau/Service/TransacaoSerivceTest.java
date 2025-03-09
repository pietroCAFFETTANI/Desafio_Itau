package com.example.Desafio_Itau.Service;

import com.example.Desafio_Itau.Exceptions.UnprocessableEntity;
import com.example.Desafio_Itau.models.EstatisticaDTO;
import com.example.Desafio_Itau.models.TransacaoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class TransacaoSerivceTest {

    @InjectMocks
    ApiService apiService;
    TransacaoDTO transacaoDTO;
    TransacaoDTO transacaoDinheiroNegativo;


    @BeforeEach
    void setup(){
        transacaoDTO = new TransacaoDTO(20.0, OffsetDateTime.now());
        transacaoDinheiroNegativo = new TransacaoDTO(-20.0, OffsetDateTime.now());
    }

    @Test
    void deveAdicionarTransacaoComSucesso(){
        apiService.addData(transacaoDTO);
        List<TransacaoDTO> resultadoEsperado = apiService.getStats(50000);
        Assertions.assertTrue(resultadoEsperado.contains(transacaoDTO));

    }

    @Test
    void deveRecusarTransferenciaComValorNegativo(){
        Assertions.assertThrows(UnprocessableEntity.class, () -> apiService.addData(transacaoDinheiroNegativo));
    }

    @Test
    void deveRecusarTransferenciaComDataFutura(){
        Assertions.assertThrows(UnprocessableEntity.class, () -> apiService.addData(new TransacaoDTO(10.0, OffsetDateTime.now().plusDays(1))));
    }


    @Test
    void deveDeletarTransacoes(){
        apiService.deleteData();
        List<TransacaoDTO> lista = apiService.getStats(5000);
        Assertions.assertTrue(lista.isEmpty());
    }

    @Test
    void deveBuscarEstatisticas(){
        TransacaoDTO dto = new TransacaoDTO(20.0, OffsetDateTime.now().minusHours(2));
        apiService.addData(transacaoDTO);
        apiService.addData(dto);
        List<TransacaoDTO> resultadoEsperado = apiService.getStats(60);
        Assertions.assertTrue(resultadoEsperado.contains(transacaoDTO));
        Assertions.assertFalse(resultadoEsperado.contains(dto));
    }
}

