import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Queue;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class MySQueueTest {
    private final SQueue queue = new SQueue();

    @Test
    public void testNoStaticMethods() {
        Method[] methods = QStack.class.getDeclaredMethods();

        for (Method method : methods) {
            assertFalse(Modifier.isStatic(method.getModifiers()));
        }
    }

    @Test
    void verifyStackAndNoQueue() {
        try {
            Field[] fields = SQueue.class.getDeclaredFields();

            boolean hasStackField = false;

            for (Field field : fields) {
                if (Stack.class.isAssignableFrom(field.getType())) {
                    // Check if it's a Stack<Integer>
                    if (field.getGenericType() instanceof ParameterizedType) {
                        ParameterizedType pType = (ParameterizedType) field.getGenericType();
                        hasStackField = Integer.class.equals(pType.getActualTypeArguments()[0]);
                    }
                } else if (Queue.class.isAssignableFrom(field.getType())) {
                    fail("SQueue should not have a Queue<Integer> field");
                }
            }

            assertTrue(hasStackField, "QStack should have at least one private Stack<Integer> field");

        } catch (Exception e) {
            fail("Error during reflection: " + e.getMessage());
        }
    }

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
    public void testRemove() {
        queue.add(1);
        assertEquals(1, queue.remove());
    }

    @Test
    public void testRemove_Multiple() {
        for (int i = 1; i <= 5; i++)
            queue.add(i);
        for (int i = 1; i <= 5; i++)
            assertEquals(i, queue.remove());
    }

    @Test
    public void testRemove_Invalid() {
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
