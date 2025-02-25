package com.files;

import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class mockitoTest {
    
    @Mock
    IStockmarketService stockmarketService;

    StocksPortfolio portfolio;

    @BeforeEach
    public void setup() {
        portfolio = new StocksPortfolio(stockmarketService);
    }

    @Test
    public void testTotalValue() {
        when(stockmarketService.getPrice("Apple")).thenReturn(100.0);
        when(stockmarketService.getPrice("Google")).thenReturn(200.0);
        when(stockmarketService.getPrice("Amazon")).thenReturn(300.0);

        portfolio.addStock(new Stock("Apple", 6)); // 600
        portfolio.addStock(new Stock("Google", 4)); // 800
        portfolio.addStock(new Stock("Amazon", 3)); // 900

        double expectedValue = 600 + 800 + 900;
        assertEquals(expectedValue, portfolio.totalValue());

        verify(stockmarketService).getPrice("Apple");
        verify(stockmarketService).getPrice("Google");
        verify(stockmarketService).getPrice("Amazon");

    }

    @Test
    public void testMostValuableStocks_MoreSize() {
        when(stockmarketService.getPrice("Apple")).thenReturn(100.0);
        when(stockmarketService.getPrice("Google")).thenReturn(200.0);

        portfolio.addStock(new Stock("Apple", 2));    // 300
        portfolio.addStock(new Stock("Google", 1));   // 100


        List<Stock> topStocks = portfolio.mostValuableStocks(5);

        assertEquals(2, topStocks.size()); 
        assertEquals("Apple", topStocks.get(0).getLabel());
        assertEquals("Google", topStocks.get(1).getLabel());
    }

    @Test
    public void testMostValuableStocks_Empty() {
        List<Stock> topStocks = portfolio.mostValuableStocks(3);

        assertTrue(topStocks.isEmpty());
    }

    @Test
    public void testMostValuableStocks_ZeroRequested() {
        when(stockmarketService.getPrice("Apple")).thenReturn(150.0);
        portfolio.addStock(new Stock("Apple", 2));    

        List<Stock> topStocks = portfolio.mostValuableStocks(0);

        assertTrue(topStocks.isEmpty()); 
    }

    @Test
    public void testMostValuableStocks_SameValueStocks() {
        when(stockmarketService.getPrice("Apple")).thenReturn(100.0);
        when(stockmarketService.getPrice("Google")).thenReturn(100.0);
        when(stockmarketService.getPrice("Amazon")).thenReturn(100.0);

        portfolio.addStock(new Stock("Apple", 2));    
        portfolio.addStock(new Stock("Google", 2));  
        portfolio.addStock(new Stock("Amazon", 2));  

        List<Stock> topStocks = portfolio.mostValuableStocks(2);

        assertEquals(2, topStocks.size());
        assertTrue(topStocks.stream().anyMatch(stock -> stock.getLabel().equals("Apple")));
        assertTrue(topStocks.stream().anyMatch(stock -> stock.getLabel().equals("Google")));
    }

    @Test
    public void testMostValuableStocks_NegativeRequestedNumber() {
        when(stockmarketService.getPrice("Apple")).thenReturn(150.0);
        portfolio.addStock(new Stock("Apple", 2));    

        List<Stock> topStocks = portfolio.mostValuableStocks(-1);

        assertTrue(topStocks.isEmpty());
    }
}
