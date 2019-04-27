package com.soatech.soatechfinanceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxnReport {
    private Integer count;

    private Long total;

    private String currency;
}