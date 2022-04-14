package com.estacoes.estacoes.config;

import com.estacoes.estacoes.services.TaxasService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfig {

    public static String TAX_PATTERN;
    private final TaxasService taxasService;

    public ComponentConfig(TaxasService taxasService) {
        this.taxasService = taxasService;
    }

    @Bean
    public void lerTaxas () {
        try {
            String sql = "SELECT vl_parameter FROM tb_parametro WHERE description='TAX_PATTERN'";
            TAX_PATTERN = taxasService.execute(sql);
        } catch (Exception e) {
        }
    }
}
