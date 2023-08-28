package site.bitrun.cryptocurrency.domain;

import jakarta.persistence.*;

@Entity
public class CryptoRank {

    @Id @GeneratedValue
    @Column(name = "crypto_rank_id")
    private Long id;

    private String name;
    private String symbol;

    @Embedded
    private InnerQuote quote;

    public CryptoRank() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public InnerQuote getQuote() {
        return quote;
    }

    public void setQuote(InnerQuote quote) {
        this.quote = quote;
    }
}

