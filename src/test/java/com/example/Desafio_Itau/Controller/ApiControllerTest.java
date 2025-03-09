package com.example.Desafio_Itau.Controller;

import com.example.Desafio_Itau.Controler.ApiControler;
import com.example.Desafio_Itau.Exceptions.UnprocessableEntity;
import com.example.Desafio_Itau.Service.ApiService;
import com.example.Desafio_Itau.Service.EstatisticaService;
import com.example.Desafio_Itau.models.EstatisticaDTO;
import com.example.Desafio_Itau.models.TransacaoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {

    @InjectMocks
    ApiControler apiControler;

    @Mock
    ApiService apiService;
    @Mock
    EstatisticaService estatisticaService;
    TransacaoDTO transacaoDTO;
    EstatisticaDTO estatisticaDTO;


    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(apiControler).build();
        transacaoDTO = new TransacaoDTO(20.0, OffsetDateTime.of(2025, 3, 9, 1, 30, 0, 0, ZoneOffset.UTC));
        estatisticaDTO = new EstatisticaDTO(1L, 20.0, 20.0, 20.0, 20.0);
    }

    @Test
    void deveAdicionarTransacaoComSucesso() throws Exception {
        doNothing().when(apiService).addData(transacaoDTO);
        mockMvc.perform(post("/transacao")
                        .content(objectMapper.writeValueAsString(transacaoDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void deveRetornarExecaoTransacao() throws Exception {
        doThrow(new UnprocessableEntity("")).when(apiService).addData(transacaoDTO);
        mockMvc.perform(post("/transacao")
                        .content(objectMapper.writeValueAsString(transacaoDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void deletarTransacoesComSucesso() throws Exception {
        doNothing().when(apiService).deleteData();
        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());
    }


    @Test
    void deveConseguirEstatisticasComSucesso() throws Exception {
        when(estatisticaService.estatisticas(60)).thenReturn(estatisticaDTO);
        mockMvc.perform(get("/estatistica")
                .param("time", "60")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
