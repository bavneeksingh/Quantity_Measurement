import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityLengthTest {

    private static final double EPS = 1e-6;

    @Test
    void testConversion_FeetToInches() {
        assertEquals(12.0,
                QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES),
                EPS);
    }

    @Test
    void testConversion_InchesToFeet() {
        assertEquals(2.0,
                QuantityLength.convert(24.0, LengthUnit.INCHES, LengthUnit.FEET),
                EPS);
    }

    @Test
    void testConversion_YardsToInches() {
        assertEquals(36.0,
                QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCHES),
                EPS);
    }

    @Test
    void testConversion_ZeroValue() {
        assertEquals(0.0,
                QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCHES),
                EPS);
    }

    @Test
    void testConversion_NegativeValue() {
        assertEquals(-12.0,
                QuantityLength.convert(-1.0, LengthUnit.FEET, LengthUnit.INCHES),
                EPS);
    }

    @Test
    void testConversion_InvalidUnit() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityLength.convert(1.0, null, LengthUnit.FEET));
    }

    @Test
    void testConversion_NaN() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityLength.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES));
    }

    @Test
    void testRoundTrip() {
        double value = 5.0;
        double result = QuantityLength.convert(
                QuantityLength.convert(value, LengthUnit.FEET, LengthUnit.INCHES),
                LengthUnit.INCHES,
                LengthUnit.FEET
        );

        assertEquals(value, result, EPS);
    }
}