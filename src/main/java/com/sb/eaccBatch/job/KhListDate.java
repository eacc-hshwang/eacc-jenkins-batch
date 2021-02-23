package com.sb.eaccBatch.job;


import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
//@Entity
public class KhListDate {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String appr_date;
    private String acquire_date;
    private String bill_date;
    private String tax_date;

    public KhListDate(String appr_date, String acquire_date, String tax_date) {
        this.appr_date = appr_date;
        this.acquire_date = acquire_date;
        this.tax_date = tax_date;
    }
    
    public KhListDate(String bill_date) {
        this.bill_date = bill_date;
    }
}
