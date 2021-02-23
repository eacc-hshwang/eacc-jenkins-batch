package com.sb.eaccBatch.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
public class KhCardAppr {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private String txName;
    private LocalDateTime txDateTime;
    
    
    private int hub_if_key;
    private String hist_row_key;
    private String corp_biz_no;
    private String fcode;
    private String fcode_nm;
    private String card_no;
    private String appr_date;
    private String appr_time;
    private String appr_no;
    private String state;
    private String state_nm;
    private String part_cancel;
    private String part_cancel_nm;
    private String cur_cd;
    private Long appr_amt_tot;
    private Long appr_amt;
    private Long vat;
    private Long tips;
    private String abroad;
    private String abroad_nm;
    private String inst_type;
    private String inst_type_nm;
    private int inst_month;
    private String merch_name;
    private String merch_biz_no;
    private String merch_no;
    private String merch_mcc_name;
    private String merch_mcc_code;
    private String merch_tel;
    private String merch_zipcode;
    private String merch_addr1;
    private String merch_addr2;
    private String merch_ceo;
    private String merch_tax_type;
    private String merch_tax_type_nm;
    private String merch_clo_date;
    private String merch_nts_date;
    private String regr_dh;
    private String regr_id;
    private String regr_nm;

    public KhCardAppr(String appr_date) {
        this.appr_date = appr_date;
    }
}
