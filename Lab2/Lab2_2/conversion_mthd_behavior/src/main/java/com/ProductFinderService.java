package com;

import java.net.http.HttpClient;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductFinderService {
    private final ISimpleHttpClient httpClient;
    private String API_PRODUCTS = "https://fakestoreapi.com/products/";

    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
    public Optional<Product> findProductDetails(int id) {
        try{
            String jsonResponse = httpClient.doHttpGet(API_PRODUCTS + id);
        
            if ((jsonResponse == null) || (jsonResponse.isEmpty())) {
                return Optional.empty();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            // transforms the json in an object of Product class
            Product product = objectMapper.readValue(jsonResponse, Product.class);
            return Optional.of(product);
    
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}