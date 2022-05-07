package com.dio.santander.banklineapi.repository;

import java.util.List;

import com.dio.santander.banklineapi.model.Movimentacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {
  
  public List<Movimentacao> findByIdConta(Integer idConta);
}
