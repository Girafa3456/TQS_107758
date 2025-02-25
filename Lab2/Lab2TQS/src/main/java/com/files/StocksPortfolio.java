package com.files;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StocksPortfolio {
    private IStockmarketService service;
    private List<Stock> stocks = new ArrayList<>();

    public StocksPortfolio(IStockmarketService service) {
        this.service = service;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double totalValue() {
        double sum = 0;
        for (int i = 0; i < stocks.size(); i++) {
            sum += stocks.get(i).getQuantity() * service.getPrice(stocks.get(i).getLabel());
        }
        return sum;
    }

    public List<Stock> mostValuableStocks(int topN) {
        return stocks.stream()
            .sorted((s1, s2) -> Double.compare(
                service.getPrice(s2.getLabel()) * s2.getQuantity(),
                service.getPrice(s1.getLabel()) * s1.getQuantity()
            ))
            .limit(topN)
            .collect(Collectors.toList());
    }
}
