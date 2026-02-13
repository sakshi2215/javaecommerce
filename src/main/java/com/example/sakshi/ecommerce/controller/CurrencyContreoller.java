package com.example.sakshi.ecommerce.controller;

import com.example.sakshi.ecommerce.service.CurrencyConversionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
public class CurrencyContreoller {

    @Autowired
    public CurrencyConversionServiceImpl currencyConversionService;

    @PostMapping("/")
    public void demo() throws Exception {
        currencyConversionService.conversionRate("USD");
    }
}
