import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    void testAddition_TargetFeet() {
        var result = QuantityMeasurementApp.QuantityLength.add(
                new QuantityMeasurementApp.QuantityLength(1.0,
                        QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.QuantityLength(12.0,
                        QuantityMeasurementApp.LengthUnit.INCHES),
                QuantityMeasurementApp.LengthUnit.FEET
        );

        assertEquals(2.0, result.toString().contains("2.0") ? 2.0 : 0, 1e-6);
    }

    @Test
    void testAddition_TargetInches() {
        var result = QuantityMeasurementApp.QuantityLength.add(
                new QuantityMeasurementApp.QuantityLength(1.0,
                        QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.QuantityLength(12.0,
                        QuantityMeasurementApp.LengthUnit.INCHES),
                QuantityMeasurementApp.LengthUnit.INCHES
        );

        assertEquals(24.0, result.getValue(), 1e-6);
    }

    @Test
    void testAddition_TargetYards() {
        var result = QuantityMeasurementApp.QuantityLength.add(
                new QuantityMeasurementApp.QuantityLength(1.0,
                        QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.QuantityLength(12.0,
                        QuantityMeasurementApp.LengthUnit.INCHES),
                QuantityMeasurementApp.LengthUnit.YARDS
        );

        assertEquals(0.6667, result.getValue(), 1e-3);
    }

    @Test
    void testAddition_Commutative() {
        var a = new QuantityMeasurementApp.QuantityLength(1.0,
                QuantityMeasurementApp.LengthUnit.FEET);
        var b = new QuantityMeasurementApp.QuantityLength(12.0,
                QuantityMeasurementApp.LengthUnit.INCHES);

        var r1 = QuantityMeasurementApp.QuantityLength.add(a, b,
                QuantityMeasurementApp.LengthUnit.YARDS);

        var r2 = QuantityMeasurementApp.QuantityLength.add(b, a,
                QuantityMeasurementApp.LengthUnit.YARDS);

        assertEquals(r1.getValue(), r2.getValue(), 1e-6);
    }

    @Test
    void testAddition_NullTarget() {
        assertThrows(IllegalArgumentException.class, () -> {
            QuantityMeasurementApp.QuantityLength.add(
                    new QuantityMeasurementApp.QuantityLength(1.0,
                            QuantityMeasurementApp.LengthUnit.FEET),
                    new QuantityMeasurementApp.QuantityLength(12.0,
                            QuantityMeasurementApp.LengthUnit.INCHES),
                    null
            );
        });
    }
}