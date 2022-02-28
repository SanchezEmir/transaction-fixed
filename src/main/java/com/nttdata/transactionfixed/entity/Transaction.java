package com.nttdata.transactionfixed.entity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nttdata.transactionfixed.entity.dto.FixedTerm;
import com.nttdata.transactionfixed.entity.enums.ETypeTransaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Document(collection = "TransactionFixedTerm")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

  @Id
  private String id;

  @NotNull
  private FixedTerm fixedTerm;

  @NotNull
  private String transactionCode;

  @NotNull
  private ETypeTransaction typeTransaction;

  @NotNull
  private Double transactionAmount;

  private Double commissionAmount;

  private LocalDateTime transactionDate;

}
