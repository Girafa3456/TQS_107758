package com;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
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
            System.out.println(jsonResponse);
            if ((jsonResponse == null) || (jsonResponse.isEmpty())) {
                return Optional.empty();
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse); // Parse JSON into a tree

            // Extract fields manually because of the extra fields of rate
            int productId = root.get("id").asInt();
            String title = root.get("title").asText();
            double price = root.get("price").asDouble();
            String description = root.get("description").asText();
            String category = root.get("category").asText();
            String image = root.get("image").asText();

            Product product = new Product(productId, image, description, price, title, category);
            return Optional.of(product);
    
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

}