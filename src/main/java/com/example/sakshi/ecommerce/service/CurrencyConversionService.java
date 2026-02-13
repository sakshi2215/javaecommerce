package com.example.sakshi.ecommerce.service;
public interface CurrencyConversionService {

    double conversionRate(String targetCurrency) throws Exception;
}
