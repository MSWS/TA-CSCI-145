import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class MyQSTackTest {
    private final QStack stack = new QStack();

    @Test
    public void testNoStaticMethods() {
        Method[] methods = QStack.class.getDeclaredMethods();

        for (Method method : methods) {
            assertFalse(Modifier.isStatic(method.getModifiers()));
        }
    }

    @Test
    public void testPush() {
        stack.push(1);
        assertEquals(1, stack.top());
    }

    @Test
    public void testPushMultiple() {
        for (int i = 1; i <= 100; i++)
            stack.push(i);
        assertEquals(100, stack.top());
    }

    @Test
    public void testPop() {
        stack.push(1);
        assertEquals(1, stack.pop());
    }

    @Test
    public void testPop_Multiple() {
        for (int i = 1; i <= 5; i++)
            stack.push(i);
        for (int i = 5; i >= 1; i--)
            assertEquals(i, stack.pop());
    }

    @Test
    public void testPop_Invalid() {
        for (int i = 1; i <= 5; i++)
            stack.push(i);
        for (int i = 0; i < 5; i++)
            stack.pop();
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 5; i++)
                stack.pop();
        });
    }

    @Test
    public void testTop() {
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.top());
        assertEquals(2, stack.top());
    }

    @Test
    public void testTop_Invalid() {
        assertDoesNotThrow(() -> {
            assertEquals(-1, stack.top());
        });
    }

    @Test
    public void testSize() {
        for (int i = 0; i < 100; i++) {
            assertEquals(i, stack.size());
            stack.push(i);
            assertEquals(i + 1, stack.size());
        }
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
        stack.push(1);
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }
}
