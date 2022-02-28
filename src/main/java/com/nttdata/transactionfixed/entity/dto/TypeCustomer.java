package com.nttdata.transactionfixed.entity.dto;

import com.nttdata.transactionfixed.entity.enums.ETypeCustomer;

import lombok.Data;

@Data
public class TypeCustomer {
  
  private String id;
  
  private ETypeCustomer value;
  
  private SubType subType;

}
