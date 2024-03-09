import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class IntegerLinkedListTest {
    private IntegerLinkedList list = new IntegerLinkedList();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out; // Store the original System.out

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut); // Restore System.out
    }

    @Test
    public void testEmpty() {
        assertTrue("List reports non-empty even while empty", list.isEmpty());
    }

    @Test
    public void testEmptyAfterRemove() {
        list.insertFront(1);
        list.insertBack(0);
        list.removeFront();
        list.removeFront();
        assertTrue("List reports non-empty after removing all items", list.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        list.insertFront(1);
        assertFalse("List reports empty even while populated", list.isEmpty());
    }

    @Test
    public void testGet() {
        for (int i = 0; i < 100; i++)
            list.insertFront(i);
        for (int i = 0; i < 100; i++)
            list.insertBack(i);
        for (int i = 0; i < 100; i++)
            assertEquals(99 - i, list.get(i));
        for (int i = 100; i < 200; i++)
            assertEquals(i - 100, list.get(i));
    }

    @Test
    public void testInsertFront() {
        list.insertFront(1);
        list.insertFront(2);
        list.insertFront(3);
        for (int i = 0; i < 3; i++)
            assertEquals(3 - i, list.get(i));
    }

    @Test
    public void testSimpleToString() {
        list.insertFront(1);
        assertEquals("1", getOutput());
    }

    @Test
    public void testComplexToString() {
        list.insertFront(1);
        list.insertFront(2);
        list.insertFront(3);
        assertEquals("3 2 1", getOutput());
    }

    @Test
    public void testInsertBack() {
        testInsertFront();
        list.insertBack(4);
        list.insertBack(5);
        list.insertBack(6);
        for (int i = 3; i < 6; i++)
            assertEquals(i + 1, list.get(i));
    }

    @Test
    public void testBackwardsToString() {
        testInsertBack();
        assertEquals("3 2 1 4 5 6", getOutput());
    }

    @Test
    public void testRemoveFront() {
        testInsertFront();
        list.removeFront();
        assertEquals(2, list.get(0));
        assertEquals(1, list.get(1));
    }

    @Test
    public void testRemoveBack() {
        testInsertFront();
        list.removeBack();
        assertEquals(3, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals("3 2", getOutput());
    }

    @Test
    public void testRemoveAt() {
        testInsertBack();
        list.removeAt(0);
        int[] expected = new int[]{2, 1, 4, 5, 6};
        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i], list.get(i));
        list.removeAt(2);
        expected = new int[]{2, 1, 5, 6};
        for (int i = 0; i < expected.length; i++)
            assertEquals(expected[i], list.get(i));
    }

    @Test
    public void testOutOfBoundsRemoveHead() {
        try {
            list.removeAt(0);
            assertEquals("indexoutofbounds", getOutput().toLowerCase());
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testOutOfBoundsRemoveBody() {
        try {
            list.removeAt(20);
            assertEquals("indexoutofbounds", getOutput().toLowerCase());
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testOutOfBoundsGetHead() {
        try {
            assertEquals(-1, list.get(0));
            assertEquals("indexoutofbounds", getOutput().toLowerCase());
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testOutOfBoundsGetBody() {
        try {
            int result = list.get(20);
            boolean didOutput = getOutput().toLowerCase().equals("indexoutofbounds");
//            assertEquals(-1, list.get(20));
//            assertEquals("indexoutofbounds", getOutput().toLowerCase());
            assertTrue("Did not handle OOB properly", (result == -1 || didOutput));
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testOutOfBoundsRemoveFront() {
        try {
            list.removeFront();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testOutOfBoundsRemoveBack() {
        try {
            list.removeBack();
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    @Test
    public void testNoStaticMethods() {
        Method[] methods = IntegerLinkedList.class.getDeclaredMethods();

        for (Method method : methods) {
            assertFalse(Modifier.isStatic(method.getModifiers()));
        }
    }

    public String getOutput() {
        list.print();
        return outContent.toString().trim();
    }
}
