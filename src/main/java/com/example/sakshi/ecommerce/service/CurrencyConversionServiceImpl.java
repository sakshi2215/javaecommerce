package com.example.sakshi.ecommerce.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final RestTemplate restTemplate;

    // USD is always the target currency
    private final String targetCurrency = "USD";

    @Value("${currency.api.url}")
    private String apiUrl;

    @Value("${currency.api.key}")
    private String apiKey;

    public CurrencyConversionServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public double conversionRate(String baseCurrency) throws Exception {

        /**
         * UriComponentsBuilder is a Spring Framework utility class used
         * to build and manipulate URIs in a safe, flexible, and readable way.
         *
         * we are using getForObject() method with the URL that we want to fetch
         * data from and the type to which it should be casted.
         */

//         String response = restTemplate.getForObject(apiUrl, String.class);
//         System.out.println(response);
//        // Build API URL with dynamic base currency
        String url = apiUrl + "/" + apiKey + "/latest/" + baseCurrency.toUpperCase();

        // Call API
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        System.out.println(response);

        if (response == null || !"success".equals(response.get("result"))) {
            throw new Exception("Currency API request failed");
        }
        Map<String, Double> rates =
                (Map<String, Double>) response.get("conversion_rates");

        Double rate = rates.get(targetCurrency);
        if (rate == null) {
            throw new Exception("USD conversion rate not found");
        }

        return rate;


    }
}
