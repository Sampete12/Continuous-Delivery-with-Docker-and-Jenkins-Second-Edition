package com.leszko.calculator;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;

/**
 * This is a test class for the Calculator class.  It contains various
 * test methods to verify the functionality of the Calculator.
 */
public class CalculatorTest {
     private Calculator calculator = new Calculator();

     @Test
     public void testSum() {
        assertEquals(5, calculator.sum(2, 3));
        assertEquals(-1, calculator.sum(-2, 1));
    }

     public void testSubtraction() {
          assertEquals(2, calc.subtract(5, 3));
     }
}









