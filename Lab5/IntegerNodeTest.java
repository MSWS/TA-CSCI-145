import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

public class IntegerNodeTest {
    private IntegerNode node = new IntegerNode();

    @Test
    public void testPrivateFields() {
        try {
            Field valueField = IntegerNode.class.getDeclaredField("value");
            assertEquals("value field is not int", int.class, valueField.getType());
            assertTrue("value field is not private", !valueField.canAccess(node)); // Ensure it's private

            Field nextField = IntegerNode.class.getDeclaredField("next");
            assertEquals("next field is not IntegerNode", IntegerNode.class, nextField.getType());
            assertTrue("next field is not private", !nextField.canAccess(node)); // Ensure it's private

        } catch (NoSuchFieldException e) {
            fail("One or both of the required fields (value, next) not found in IntegerNode");
        }
    }

    @Test
    public void testValueSetGetter() {
        node.setValue(1);
        assertEquals(1, node.getValue());
        node.setValue(-1);
        assertEquals(-1, node.getValue());
    }

    @Test
    public void testConstructor() {
        node = new IntegerNode(5);
        assertEquals(5, node.getValue());
    }

    @Test
    public void testNextSetGetter() {
        IntegerNode next = new IntegerNode();
        assertNull(node.getNext());
        node.setNext(next);
        assertSame(next, node.getNext());
    }

    @Test
    public void testNoStaticMethods() {
        Method[] methods = IntegerNode.class.getDeclaredMethods();

        for (Method method : methods) {
            assertFalse(Modifier.isStatic(method.getModifiers()));
        }
    }
}
