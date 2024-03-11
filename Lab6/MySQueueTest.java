import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MySQueueTest {
    private final SQueue queue = new SQueue();

    @Test
    public void testAdd() {
        queue.add(1);
        assertEquals(1, queue.peek());
    }

    @Test
    public void testAddMultiple() {
        for (int i = 1; i <= 100; i++)
            queue.add(i);
        assertEquals(1, queue.remove());
    }

    @Test
    public void testPop() {
        queue.add(1);
        assertEquals(1, queue.remove());
    }

    @Test
    public void testPop_Multiple() {
        for (int i = 1; i <= 5; i++)
            queue.add(i);
        for (int i = 1; i <= 5; i++)
            assertEquals(i, queue.remove());
    }

    @Test
    public void testPop_Invalid() {
        for (int i = 1; i <= 5; i++)
            queue.add(i);
        for (int i = 0; i < 5; i++)
            queue.remove();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 5; i++)
                queue.remove();
        });
    }

    @Test
    public void testPeek() {
        queue.add(1);
        queue.add(2);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
    }

    @Test
    public void testPeek_Invalid() {
        assertDoesNotThrow(() -> {
            assertEquals(-1, queue.peek());
        });
    }

    @Test
    public void testSize() {
        for (int i = 0; i < 100; i++) {
            assertEquals(i, queue.size());
            queue.add(i);
            assertEquals(i + 1, queue.size());
        }
    }

    @Test
    public void testIsEmpty() {
        assertTrue(queue.isEmpty());
        queue.add(1);
        assertFalse(queue.isEmpty());
        queue.remove();
        assertTrue(queue.isEmpty());
    }
}
