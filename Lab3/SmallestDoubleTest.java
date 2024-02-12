import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SmallestDoubleTest {
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
        String inputData = "4\n50\n20.1\n30.6\n20.5";
        ByteArrayInputStream inContent = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(inContent);

        SmallestDouble.findSmallestDouble();
        assertTrue("Output should contain '20.1'", outContent.toString().contains("20.1"));
    }

    @Test
    public void testEqualSmallest() {
        String inputData = "3\n20.1\n20.1\n20.1";
        ByteArrayInputStream inContent = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(inContent);

        SmallestDouble.findSmallestDouble();
        assertTrue("Output should contain '20.1'", outContent.toString().contains("20.1"));
    }

    @Test
    public void testNegativeSmallest() {
        String inputData = "3\n-20.1\n-20.1\n-20.1";
        ByteArrayInputStream inContent = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(inContent);

        SmallestDouble.findSmallestDouble();
        assertTrue("Output should contain '-20.1'", outContent.toString().contains("-20.1"));
    }

    @Test
    public void testExtremes() {
        String inputData = "2\n" + Double.MAX_VALUE + "\n" + Double.MIN_VALUE;
        ByteArrayInputStream inContent = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(inContent);

        SmallestDouble.findSmallestDouble();
        assertTrue("Output should contain '" + Double.MIN_VALUE + "'", outContent.toString().contains(Double.MIN_VALUE + ""));
    }

}
