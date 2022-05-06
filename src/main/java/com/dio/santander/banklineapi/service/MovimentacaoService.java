package com.dio.santander.banklineapi.service;

import java.time.LocalDateTime;

import com.dio.santander.banklineapi.dto.NovaMovimentacao;
import com.dio.santander.banklineapi.model.Correntista;
import com.dio.santander.banklineapi.model.Movimentacao;
import com.dio.santander.banklineapi.repository.CorrentistaRepository;
import com.dio.santander.banklineapi.repository.MovimentacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {

  @Autowired
  private MovimentacaoRepository repository;

  @Autowired
  private CorrentistaRepository correntistaRepository;

  public void save(NovaMovimentacao novaMovimentacao) {
    Movimentacao movimentacao = new Movimentacao();

    Double valor;
    switch (novaMovimentacao.getTipo()) {
      case RECEITA:
        valor = novaMovimentacao.getValor();
        break;
      case DESPESA:
        valor = novaMovimentacao.getValor() * -1;
        break;
      default:
        valor = 0.0;
        break;
    }
    movimentacao.setValor(valor);

    movimentacao.setDataHora(LocalDateTime.now());
    movimentacao.setDescricao(novaMovimentacao.getDescricao());
    movimentacao.setIdConta(novaMovimentacao.getIdConta());
    movimentacao.setTipo(novaMovimentacao.getTipo());

    Correntista correntista = correntistaRepository.findById(novaMovimentacao.getIdConta()).orElse(null);

    if (correntista != null) {
      correntista.getConta().setSaldo(correntista.getConta().getSaldo()+valor);
      correntistaRepository.save(correntista);
    }

    repository.save(movimentacao);
  }
}
