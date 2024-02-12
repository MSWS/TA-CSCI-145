import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

public class MyCircleTester {
    @Test
    public void verifyInstanceVariables() {
        for (Field field : Circle.class.getDeclaredFields()) {
            // print the field we're looking at
            System.out.println(field);
            assertEquals("All instance variables should be of type double", field.getType(), double.class);
            assertEquals("All instance variables should be private", field.getModifiers(), 2);
        }
    }

    @Test
    public void verifyNoExtraInstanceVariables() {
        assert Circle.class.getDeclaredFields().length == 3;
    }

    private Circle circle;

    @Before
    public void initCircle() {
        circle = new Circle();
        circle.setCenter_x(3.14);
        circle.setCenter_y(1.59);
        circle.setRadius(2.65);
    }

    @Test
    public void testToString() {
        String toString = circle.toString();
        System.out.println(toString);
        assertTrue("toString should contain the center x value", toString.contains("center: (3.14, 1.59)"));
        assertTrue("toString should contain the radius value", toString.contains("radius: 2.65"));
    }

    @Test
    public void testGetters() {
        assertEquals("getCenter_x should return the x value", 3.14, circle.getCenter_x(), 0.0001);
        assertEquals("getCenter_y should return the y value", 1.59, circle.getCenter_y(), 0.0001);
        assertEquals("getRadius should return the radius value", 2.65, circle.getRadius(), 0.0001);
    }

    @Test
    public void testSetters() {
        circle.setCenter_x(2.65);
        circle.setCenter_y(3.14);
        circle.setRadius(1.59);
        assertEquals("setCenter_x should set the x value", 2.65, circle.getCenter_x(), 0.0001);
        assertEquals("setCenter_y should set the y value", 3.14, circle.getCenter_y(), 0.0001);
        assertEquals("setRadius should set the radius value", 1.59, circle.getRadius(), 0.0001);
    }

    @Test
    public void testInvalidRadius() {
        circle.setRadius(-1);
        assertEquals("setRadius should not set the radius to a negative value", 2.65, circle.getRadius(), 0.0001);
    }

    @Test
    public void testArea() {
        assertEquals("area should return the area of the circle", circle.getRadius() * circle.getRadius() * Math.PI, circle.area(), 0.00001);
    }

    @Test
    public void testDiameter() {
        assertEquals("diameter should return the diameter of the circle", circle.getRadius() * 2, circle.diameter(), 0.00001);
    }

    @Test
    public void testPerimeter() {
        assertEquals("perimeter should return the perimeter of the circle", circle.getRadius() * 2 * Math.PI, circle.perimeter(), 0.00001);
    }

    @Test
    public void testUnitCircle() {
        assertFalse(circle.isUnitCircle());
        Circle unitCircle = new Circle();
        unitCircle.setCenter_x(0);
        unitCircle.setCenter_y(0);
        unitCircle.setRadius(1);
        assertTrue(unitCircle.isUnitCircle());

        unitCircle.setCenter_x(1);
        assertFalse(unitCircle.isUnitCircle());

        unitCircle.setCenter_x(0);
        unitCircle.setCenter_y(1);
        assertFalse(unitCircle.isUnitCircle());

        unitCircle.setCenter_x(0);
        unitCircle.setCenter_y(0);
        unitCircle.setRadius(2);
        assertFalse(unitCircle.isUnitCircle());

        unitCircle.setRadius(1);
        assertTrue(unitCircle.isUnitCircle());
    }

    @Test
    public void testEquals() {
        Circle nonEqual = new Circle();
        nonEqual.setCenter_x(3);
        nonEqual.setCenter_y(1);
        nonEqual.setRadius(2);
        assertFalse(nonEqual.equals(circle));
        assertFalse(circle.equals(nonEqual));

        Circle equal = new Circle();
        equal.setCenter_x(3.14);
        equal.setCenter_y(1.59);
        equal.setRadius(2.65);
        assertTrue(equal.equals(circle));
        assertTrue(circle.equals(equal));
    }

    @Test
    public void testConcentricity() {
        Circle diffCenter = new Circle();
        diffCenter.setCenter_x(3);
        diffCenter.setCenter_y(1);
        diffCenter.setRadius(circle.getRadius());
        assertFalse(circle.isConcentric(diffCenter));

        Circle diffRadius = new Circle();
        diffRadius.setCenter_x(circle.getCenter_x());
        diffRadius.setCenter_y(circle.getCenter_y());
        diffRadius.setRadius(1);
        assert diffRadius.getRadius() != circle.getRadius();
        assertTrue(circle.isConcentric(diffRadius));

        Circle same = new Circle();
        same.setCenter_x(circle.getCenter_x());
        same.setCenter_y(circle.getCenter_y());
        same.setRadius(circle.getRadius());
        assertFalse(circle.isConcentric(same));

        Circle diffCenterAndRadius = new Circle();
        diffCenterAndRadius.setCenter_x(3);
        diffCenterAndRadius.setCenter_y(1);
        diffCenterAndRadius.setRadius(1);
        assertFalse(circle.isConcentric(diffCenterAndRadius));
    }

    @Test
    public void testDistanceSymmetry() {
        Circle a = new Circle();
        a.setCenter_x(0);
        a.setCenter_y(0);
        a.setRadius(1);

        Circle b = new Circle();
        b.setCenter_x(1);
        b.setCenter_y(1);
        b.setRadius(1);

        assertEquals(a.distance(b), b.distance(a), 0.0001);
    }

    @Test
    public void testDistance() {
        Circle a = new Circle();
        a.setCenter_x(0);
        a.setCenter_y(0);
        a.setRadius(1);

        double dist = Math.sqrt(Math.pow(a.getCenter_x() - circle.getCenter_x(), 2) + Math.pow(a.getCenter_y() - circle.getCenter_y(), 2));

        assertEquals(a.distance(circle), dist, 0.0001);
    }

    @Test
    public void testIntersections() {
        Circle doesIntersect = new Circle();
        doesIntersect.setCenter_x(3);
        doesIntersect.setCenter_y(1);
        doesIntersect.setRadius(2);

        assertTrue(circle.intersects(doesIntersect));

        Circle doesNotIntersect = new Circle();
        doesNotIntersect.setCenter_x(10);
        doesNotIntersect.setCenter_y(10);
        doesNotIntersect.setRadius(1);

        assertFalse(circle.intersects(doesNotIntersect));
    }
}

