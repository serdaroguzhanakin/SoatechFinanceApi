package com.soatech.soatechfinanceapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxnReportRequest {
    private String startDate;
    private String endDate;
}
