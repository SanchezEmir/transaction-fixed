package com.nttdata.transactionfixed.service;

import com.nttdata.transactionfixed.entity.Transaction;
import com.nttdata.transactionfixed.entity.dto.FixedTerm;
import com.nttdata.transactionfixed.entity.enums.ETypeTransaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {

  Mono<Transaction> create(Transaction t);
  
  Flux<Transaction> findAll();
  
  Mono<Transaction> findById(String id);
  
  Mono<Transaction> update(Transaction t);
  
  Mono<Boolean> delete(String t);
  
  Mono<Long> countTransactions(String id, ETypeTransaction typeTransaction);
  
  Mono<FixedTerm> findFixedTermById(String t);
  
  Mono<FixedTerm> updateFixedTerm(FixedTerm ft);

}
