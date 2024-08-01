package com.RESTAPI.KJ.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

@Configuration
public class CurrencyConfig {

    @Value("${valid.ISO.4217.currencies}")
    private String[] validCurrenciesArray;

    private Set<String> validCurrencies;

    @PostConstruct
    public void init() {
        validCurrencies = new HashSet<>(Arrays.asList(validCurrenciesArray));
    }

    public Set<String> getValidCurrencies() {
        return validCurrencies;
    }
}
