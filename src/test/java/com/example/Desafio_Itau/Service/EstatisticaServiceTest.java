package com.example.Desafio_Itau.Service;

import com.example.Desafio_Itau.models.EstatisticaDTO;
import com.example.Desafio_Itau.models.TransacaoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstatisticaServiceTest {
    @Mock
    ApiService apiService;

    @InjectMocks
    EstatisticaService estatisticaService;
    TransacaoDTO transacaoDTO;
    EstatisticaDTO estatisticaDTO;
    EstatisticaDTO estatisticasnulas;

    @BeforeEach
    void setup(){
        transacaoDTO = new TransacaoDTO(20.0, OffsetDateTime.now());
        estatisticaDTO = new EstatisticaDTO(1l, 20.0, 20.0, 20.0, 20.0);
        estatisticasnulas = new EstatisticaDTO(0l, 0.0, 0.0, 0.0, 0.0);
    }

    @Test
    void calcularEstatisticaComSucesso(){
        when(apiService.getStats(60)).thenReturn(Collections.singletonList(transacaoDTO));
        EstatisticaDTO resultado = estatisticaService.estatisticas(60);
        verify(apiService, times(1)).getStats(60);
        assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticaDTO);
    }

    @Test
    void estatisticasNulas(){
        when(apiService.getStats(60)).thenReturn(Collections.emptyList());
        EstatisticaDTO resultado = estatisticaService.estatisticas(60);
        verify(apiService, times(1)).getStats(60);
        assertThat(resultado).usingRecursiveComparison().isEqualTo(estatisticasnulas);
    }
}

