package com.leszko.calculator;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;

/**
 * This is a test class for the Calculator class.  It contains various
 * test methods to verify the functionality of the Calculator.
 */
@Test
public void testSumWithZero() {
    assertEquals(3, calculator.sum(3, 0));
    assertEquals(0, calculator.sum(0, 0));
}

@Test
public void testSumWithNegativeNumbers() {
    assertEquals(-5, calculator.sum(-2, -3));
}









