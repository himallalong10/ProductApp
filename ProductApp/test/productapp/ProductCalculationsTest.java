package productapp;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProductCalculationsTest {

    ProductCalculations calculations =
            new ProductCalculations();

    @Test
    public void getVat_withValidData_returnsCorrectValue() {

        double result = calculations.getVat(3500);

        assertEquals(542.50, result, 0.01);
    }

    @Test
    public void getTotal_withValidData_returnsCorrectValue() {

        double result =
                calculations.getTotalSaleAmount(3500);

        assertEquals(4217.50, result, 0.01);
    }
}