package com.nttdata.transactionfixed.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.transactionfixed.entity.Transaction;

import reactor.core.publisher.Flux;

public interface ITransactionRepository extends ReactiveMongoRepository<Transaction, String> {
  
  Flux<Transaction> findByFixedTermId(String id);

}
