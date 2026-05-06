import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testFeet_SameValue() {
        assertTrue(QuantityMeasurementApp.compareFeet(1.0, 1.0));
    }

    @Test
    void testFeet_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareFeet(1.0, 2.0));
    }

    @Test
    void testInches_SameValue() {
        assertTrue(QuantityMeasurementApp.compareInches(1.0, 1.0));
    }

    @Test
    void testInches_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareInches(1.0, 2.0));
    }

    @Test
    void testFeet_NullComparison() {
        QuantityMeasurementApp.Feet f = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(f.equals(null));
    }

    @Test
    void testInches_NullComparison() {
        QuantityMeasurementApp.Inches i = new QuantityMeasurementApp.Inches(1.0);
        assertFalse(i.equals(null));
    }

    @Test
    void testFeet_SameReference() {
        QuantityMeasurementApp.Feet f = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(f.equals(f));
    }

    @Test
    void testInches_SameReference() {
        QuantityMeasurementApp.Inches i = new QuantityMeasurementApp.Inches(1.0);
        assertTrue(i.equals(i));
    }
}