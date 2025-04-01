package com.leszko.calculator;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This class contains unit tests for the Calculator class.
 */
public class CalculatorTest {
     private Calculator calculator = new Calculator();

     @Test
     public void testSum() {
          assertEquals(5, calculator.sum(2, 3));
     }
     @Test
     public void testDiv() {
          assertEquals(3, calculator.div(6, 2));
     }
}

