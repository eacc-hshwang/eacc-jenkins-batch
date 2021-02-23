package com.sb.eaccBatch.job.write;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.sb.eaccBatch.job.KhListDate;
import com.sb.eaccBatch.kwichub.KwichubService2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Configuration
public class KHItemWriterJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource; // DataSource DI

    private static final int chunkSize = 1;

    @Bean
    public Job khCardListItemWriterJob() throws Exception {
        return jobBuilderFactory.get("khCardListItemWriterJob")
                .start(khCardApprItemWriterStep())
                .next(khCardAcquireItemWriterStep())
                .next(khTaxItemWriterStep())
                .build();
    }

    @Bean
    public Step khCardApprItemWriterStep() throws Exception {
        return stepBuilderFactory.get("khCardListItemWriterStep")
                .<KhListDate, KhListDate>chunk(chunkSize)
                .reader(khItemReader())
                .writer(khCardApprItemWriter())
                .build();
    }
    
    @Bean
    public Step khCardAcquireItemWriterStep() throws Exception {
        return stepBuilderFactory.get("khCardListItemWriterStep")
                .<KhListDate, KhListDate>chunk(chunkSize)
                .reader(khItemReader())
                .writer(khCardAcquireItemWriter())
                .build();
    }
    
    @Bean
    public Step khTaxItemWriterStep() throws Exception {
        return stepBuilderFactory.get("khCardListItemWriterStep")
                .<KhListDate, KhListDate>chunk(chunkSize)
                .reader(khItemReader())
                .writer(khTaxItemWriter())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<KhListDate> khItemReader() {
        return new JdbcCursorItemReaderBuilder<KhListDate>()
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(KhListDate.class))
                .sql("SELECT (SELECT DATE_FORMAT(DATE_ADD(MAX(APPR_DATE), INTERVAL 1 DAY), '%Y%m%d') FROM kh_card_appr) AS appr_date,"
                		+ "	(SELECT DATE_FORMAT(DATE_ADD(MAX(PURCH_DATE), INTERVAL 1 DAY), '%Y%m%d') FROM kh_card_acquire) AS acquire_date,"
                		+ "	(SELECT DATE_FORMAT(DATE_ADD(MAX(ISSUE_DD), INTERVAL 1 DAY), '%Y%m%d') FROM kh_tax_list) AS tax_date")
                .name("khCardListItemWriter")
                .build();
    }
    
    @Autowired
    KwichubService2 kwichubService2;

    /**
     * reader에서 넘어온 데이터를 하나씩 출력하는 writer
     */
    @Bean // beanMapped()을 사용할때는 필수
    public ItemWriter<KhListDate> khCardApprItemWriter() {
    	return items -> {
    		for (KhListDate item : items) kwichubService2.insert_Approval(item.getAppr_date());
       };
    }
    
    @Bean // beanMapped()을 사용할때는 필수
    public ItemWriter<KhListDate> khCardAcquireItemWriter() {
    	return items -> {
    		for (KhListDate item : items) kwichubService2.insert_Acquire(item.getAcquire_date());
       };
    }
    
    @Bean // beanMapped()을 사용할때는 필수
    public ItemWriter<KhListDate> khTaxItemWriter() {
    	return items -> {
    		for (KhListDate item : items) kwichubService2.insert_eTax(item.getTax_date());
       };
    }
}
