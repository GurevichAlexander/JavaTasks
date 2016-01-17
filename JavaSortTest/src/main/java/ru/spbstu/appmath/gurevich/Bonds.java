package ru.spbstu.appmath.gurevich;

public class Bonds {
    private String issuer;
    private double price;

    public Bonds(String issuer, double price) {
        this.issuer = issuer;
        this.price = price;
    }

    public String getIssuer() {
        return(this.issuer);
    }

    public double getPrice() {
        return(this.price);
    }


}
