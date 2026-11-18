package productapp;

public class ProductCalculations {

    public double getVat(double price) {
        return price * 0.155;
    }

    public double getCommission(double price) {
        return price * 0.05;
    }

    public double getTotalSaleAmount(double price) {
        return price + getVat(price) + getCommission(price);
    }
}