import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testEquality_YardToYard_SameValue() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.YARD);
        var q2 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.YARD);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.YARD);
        var q2 = new QuantityMeasurementApp.QuantityLength(3.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_YardToInch_EquivalentValue() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.YARD);
        var q2 = new QuantityMeasurementApp.QuantityLength(36.0,
                QuantityMeasurementApp.LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_CmToInch_EquivalentValue() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.CENTIMETER);
        var q2 = new QuantityMeasurementApp.QuantityLength(0.393701,
                QuantityMeasurementApp.LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_CmToFeet_NonEquivalent() {
        var q1 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.CENTIMETER);
        var q2 = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        assertFalse(q1.equals(q2));
    }

    @Test
    void testEquality_MultiUnit_Transitive() {
        var yard = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.YARD);

        var feet = new QuantityMeasurementApp.QuantityLength(3.0,
                QuantityMeasurementApp.LengthUnit.FEET);

        var inch = new QuantityMeasurementApp.QuantityLength(36.0,
                QuantityMeasurementApp.LengthUnit.INCH);

        assertTrue(yard.equals(feet));
        assertTrue(feet.equals(inch));
        assertTrue(yard.equals(inch));
    }

    @Test
    void testEquality_InvalidUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityMeasurementApp.QuantityLength(1.0, null);
        });
    }

    @Test
    void testEquality_SameReference() {
        var q = new QuantityMeasurementApp.QuantityLength(2.0,
                QuantityMeasurementApp.LengthUnit.YARD);

        assertTrue(q.equals(q));
    }

    @Test
    void testEquality_NullComparison() {
        var q = new QuantityMeasurementApp.QuantityLength(2.0,
                QuantityMeasurementApp.LengthUnit.YARD);

        assertFalse(q.equals(null));
    }
}