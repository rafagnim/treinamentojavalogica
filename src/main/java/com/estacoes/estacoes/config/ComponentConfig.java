package com.estacoes.estacoes.config;

import com.estacoes.estacoes.services.TaxasService;;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfig {

    private static String TAX_PATTERN;
    private final TaxasService taxasService;

    @Value("${TAX_PATTERN}")
    private String TAX_PATTERN_prop;

    private String TAX_PATTERN_db;

    public ComponentConfig(TaxasService taxasService) {
        this.taxasService = taxasService;
    }

    @Bean
    public void lerTaxas () {
        try {
            String sql = "SELECT vl_parameter FROM tb_parametro WHERE description='TAX_PATTERN'";

            this.TAX_PATTERN_db = taxasService.execute(sql);

            this.TAX_PATTERN = Double.parseDouble(this.TAX_PATTERN_db) < Double.parseDouble(this.TAX_PATTERN_prop) ? this.TAX_PATTERN_db : this.TAX_PATTERN_prop;;

        } catch (Exception e) {
        }
    }

    public static String getTAX_PATTERN() {
        return TAX_PATTERN;
    }
}
