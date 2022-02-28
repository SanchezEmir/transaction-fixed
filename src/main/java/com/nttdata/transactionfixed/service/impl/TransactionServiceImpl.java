package com.nttdata.transactionfixed.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.transactionfixed.entity.Transaction;
import com.nttdata.transactionfixed.entity.dto.FixedTerm;
import com.nttdata.transactionfixed.entity.enums.ETypeTransaction;
import com.nttdata.transactionfixed.repository.ITransactionRepository;
import com.nttdata.transactionfixed.service.ITransactionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements ITransactionService {
  
  private final WebClient webClient;
  private final ReactiveCircuitBreaker reactiveCircuitBreaker;

  @Value("${config.base.apigateway}")
  private String url;

  public TransactionServiceImpl(
      ReactiveResilience4JCircuitBreakerFactory circuitBreakerFactory) {
    this.webClient = WebClient.builder().baseUrl(this.url).build();
    this.reactiveCircuitBreaker = circuitBreakerFactory.create("fixedTerm");
  }

  @Autowired
  private ITransactionRepository dao;

  @Override
  public Mono<FixedTerm> findFixedTermById(String t) {
    return reactiveCircuitBreaker.run(webClient.get()
        .uri(this.url + "/fixedTerm/find/{id}", t).accept(MediaType.APPLICATION_JSON)
        .retrieve().bodyToMono(FixedTerm.class), throwable -> {
          return this.getDefaultFixedTerm();
        });
  }

  @Override
  public Mono<FixedTerm> updateFixedTerm(FixedTerm ft) {
    return reactiveCircuitBreaker.run(webClient.put()
        .uri(this.url + "/fixedTerm/update", ft).accept(MediaType.APPLICATION_JSON)
        .bodyValue(ft).retrieve().bodyToMono(FixedTerm.class), throwable -> {
          return this.getDefaultFixedTerm();
        });
  }

  public Mono<FixedTerm> getDefaultFixedTerm() {
    Mono<FixedTerm> fixedTerm = Mono.just(new FixedTerm());
    return fixedTerm;
  }

  @Override
  public Mono<Transaction> create(Transaction t) {
    return dao.save(t);
  }

  @Override
  public Flux<Transaction> findAll() {
    return dao.findAll();
  }

  @Override
  public Mono<Transaction> findById(String id) {
    return dao.findById(id);
  }

  @Override
  public Mono<Transaction> update(Transaction t) {
    return dao.save(t);
  }

  @Override
  public Mono<Boolean> delete(String t) {
    return dao.findById(t)
        .flatMap(tar -> dao.delete(tar).then(Mono.just(Boolean.TRUE)))
        .defaultIfEmpty(Boolean.FALSE);
  }

  @Override
  public Mono<Long> countTransactions(String id,
      ETypeTransaction typeTransaction) {
    return dao.findByFixedTermId(id)
        .filter(transactionFixedTerm -> transactionFixedTerm
            .getTypeTransaction().equals(typeTransaction))
        .count();
  }

}
