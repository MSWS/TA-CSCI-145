import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SummationTest {
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
    public void testPositiveSummation() {
        // Simulate user input for 3 integers: 5, 7, and 2
        String inputData = "3\n5\n7\n2\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(inContent);

        Summation.sumIntegers();
        assertTrue("Output should contain '14'", outContent.toString().contains("14"));
    }

    @Test
    public void testNegativeSummation() {
        // Simulate user input for 3 integers: -5, -7, and -2
        String inputData = "3\n-5\n-7\n-2\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(inContent);

        Summation.sumIntegers();
        assertTrue("Output should contain '-14'", outContent.toString().contains("-14"));
    }

    @Test
    public void testZeroSummation() {
        // Simulate user input for 3 integers: 0, 0, and 0
        String inputData = "0\n";
        ByteArrayInputStream inContent = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(inContent);

        Summation.sumIntegers();
        assertTrue("Output should contain '0'", outContent.toString().contains("0"));
    }
}
