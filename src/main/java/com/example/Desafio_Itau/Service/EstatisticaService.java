package com.example.Desafio_Itau.Service;


import com.example.Desafio_Itau.models.EstatisticaDTO;
import com.example.Desafio_Itau.models.TransacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EstatisticaService {

    @Autowired
    private ApiService transacao;

    public EstatisticaDTO estatisticas(Integer time ){
        List<TransacaoDTO> recorte = transacao.getStats(time);
        DoubleSummaryStatistics stats = recorte.stream().mapToDouble(TransacaoDTO::value).summaryStatistics();

        if(recorte.isEmpty()){
            return new EstatisticaDTO(0L, (double) 0, (double) 0, (double) 0, (double) 0);
        }

        return new EstatisticaDTO(
                stats.getCount(),
                stats.getSum(),
                stats.getAverage(),
                stats.getMin(),
                stats.getMax()
        );
    }
}
