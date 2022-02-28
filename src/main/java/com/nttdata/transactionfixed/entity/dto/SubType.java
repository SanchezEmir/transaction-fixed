package com.nttdata.transactionfixed.entity.dto;

import com.nttdata.transactionfixed.entity.enums.ESubType;

import lombok.Data;

@Data
public class SubType {
  
  private String id;
  
  private ESubType value;

}
