package workshop.introMVC.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {

    private double purchasePrice;

    private int numberOfShares;

    private Date date;

    private String stockTicker;

    private String portfolioId;

    private String id;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
    }

    public Transaction(double purchasePrice, int numberOfShares, Date date, String stockTicker, String portfolioId) {
        this.purchasePrice = purchasePrice;
        this.numberOfShares = numberOfShares;
        this.date = date;
        this.stockTicker = stockTicker;
        this.portfolioId = portfolioId;
        this.id = UUID.randomUUID().toString();
    }
}
