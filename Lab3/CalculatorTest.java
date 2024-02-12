import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;

import other.Calculator;

public class CalculatorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAddition() {
        String input = "A\n5\n3\nQ";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("5"));
        assertTrue("Output should include second number", outContent.toString().contains("3"));
        assertTrue("Output should include the sum", outContent.toString().contains("8"));
    }

    @Test
    public void testNegativeAddition() {
        String input = "A\n-5\n3\nq";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("-5"));
        assertTrue("Output should include second number", outContent.toString().contains("3"));
        assertTrue("Output should include the sum", outContent.toString().contains("-2"));
    }

    @Test
    public void testAdditionCaseInsensitive() {
        String input = "a\n1.5\n2.5\nq";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("1.5"));
        assertTrue("Output includes operator", outContent.toString().contains("+"));
        assertTrue("Output should include second number", outContent.toString().contains("2.5"));
        assertTrue("Output should include the sum", outContent.toString().contains("4"));
    }


    @Test
    public void testSubtraction() {
        String input = "S\n10\n2\n";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("10"));
        assertTrue("Output includes operator", outContent.toString().contains("-"));
        assertTrue("Output should include second number", outContent.toString().contains("2"));
        assertTrue("Output should include the difference", outContent.toString().contains("8"));
    }

    @Test
    public void testNegativeSubtraction() {
        String input = "s\n10\n-2";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("10"));
        assertTrue("Output includes operator", outContent.toString().contains("-"));
        assertTrue("Output should include second number", outContent.toString().contains("-2"));
        assertTrue("Output should include the difference", outContent.toString().contains("12"));
    }

    @Test
    public void testMultiplication() {
        String input = "M\n5\n3\nq";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("5"));
        assertTrue("Output includes operator", outContent.toString().contains("*"));
        assertTrue("Output should include second number", outContent.toString().contains("3"));
        assertTrue("Output should include the product", outContent.toString().contains("15"));
    }

    @Test
    public void testNegativeMultiplication() {
        String input = "m\n-5\n3\nq";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("-5"));
        assertTrue("Output includes operator", outContent.toString().contains("*"));
        assertTrue("Output should include second number", outContent.toString().contains("3"));
        assertTrue("Output should include the product", outContent.toString().contains("-15"));
    }

    @Test
    public void testDivision() {
        String input = "D\n10\n2\nq";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("10"));
        assertTrue("Output includes operator", outContent.toString().contains("/"));
        assertTrue("Output should include second number", outContent.toString().contains("2"));
        assertTrue("Output should include the quotient", outContent.toString().contains("5"));
    }

    @Test
    public void testNegativeDivision() {
        String input = "d\n-10\n2\nq";
        simulateUserInput(input);

        executeCalculator();

        assertTrue("Output should include first number", outContent.toString().contains("-10"));
        assertTrue("Output includes operator", outContent.toString().contains("/"));
        assertTrue("Output should include second number", outContent.toString().contains("2"));
        assertTrue("Output should include the quotient", outContent.toString().contains("-5"));
    }

    @Test
    public void testQuit() {
        String input = "q\nq";
        simulateUserInput(input);
        Calculator.main(new String[0]);
    }

    private void simulateUserInput(String input) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
    }

    private void executeCalculator() {
        try {
            Calculator.main(new String[0]);
        } catch (NoSuchElementException ignored) {
        }
    }
}
