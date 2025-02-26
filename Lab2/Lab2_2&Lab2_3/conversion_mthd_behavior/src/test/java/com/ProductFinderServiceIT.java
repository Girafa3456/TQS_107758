package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProductFinderServiceIT {
    private ISimpleHttpClient httpClient;

    private ProductFinderService pfinderService;

    @BeforeEach
    void setUp() {
        httpClient = new TqsBasicHttpConnection();
        pfinderService = new ProductFinderService(httpClient);
    }

    @Test
    void testFindProductDetailsMethodA() {
        Optional<Product> product = pfinderService.findProductDetails(3);

        assertTrue(product.isPresent());
        assertEquals(3, product.get().getId());
        assertEquals("Mens Cotton Jacket", product.get().getTitle());
        assertEquals(55.99, product.get().getPrice());
        assertEquals("great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.", product.get().getDescription());
        assertEquals("https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg", product.get().getImage());
        assertEquals(	"men's clothing", product.get().getCategory());
    }   

    @Test
    void testFindProductDetailsMethodB() {
        Optional<Product> product = pfinderService.findProductDetails(300);
        assertTrue(product.isEmpty());
    }
}
