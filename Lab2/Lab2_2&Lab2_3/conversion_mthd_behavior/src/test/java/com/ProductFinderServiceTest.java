package com;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Optional;

public class ProductFinderServiceTest {
    @Mock
    private ISimpleHttpClient httpClient;

    private ProductFinderService pfinderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pfinderService = new ProductFinderService(httpClient);
    }

    @Test
    void testFindProductDetailsMethodA() throws IOException {
        String jsonResponse = "{"
        + "\"id\": 3,"
        + "\"title\": \"Mens Cotton Jacket\","
        + "\"price\": 55.99,"
        + "\"description\": \"A stylish cotton jacket for men.\","
        + "\"category\": \"men's clothing\","
        + "\"image\": \"https://example.com/imagemdeexemplo.jpg\""
        + "}";

        when(httpClient.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(jsonResponse);
        Optional<Product> product = pfinderService.findProductDetails(3);

        assertTrue(product.isPresent());
        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle());

        verify(httpClient).doHttpGet("https://fakestoreapi.com/products/3");
    }   

    @Test
    void testFindProductDetailsMethodB() throws IOException{
        String jsonResponse = "{"
        + "\"id\": 3,"
        + "\"title\": \"Mens Cotton Jacket\","
        + "\"price\": 55.99,"
        + "\"description\": \"A stylish cotton jacket for men.\","
        + "\"category\": \"men's clothing\","
        + "\"image\": \"https://example.com/imagemdeexemplo.jpg\""
        + "}";

        when(httpClient.doHttpGet("https://fakestoreapi.com/products/3")).thenReturn(jsonResponse);
        Optional<Product> product = pfinderService.findProductDetails(300);

        assertTrue(product.isEmpty());

        verify(httpClient).doHttpGet("https://fakestoreapi.com/products/3");
    }
}
