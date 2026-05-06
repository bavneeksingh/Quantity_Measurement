import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityLengthAdditionTest {

    private static final double EPS = 1e-6;

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        QuantityLength result =
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(2.0, LengthUnit.FEET)
                );

        assertEquals(3.0, result.getValue(), EPS);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        QuantityLength result =
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCHES)
                );

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_CrossUnit_InchesPlusFeet() {
        QuantityLength result =
                QuantityLength.add(
                        new QuantityLength(12.0, LengthUnit.INCHES),
                        new QuantityLength(1.0, LengthUnit.FEET)
                );

        assertEquals(24.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_Commutativity() {
        QuantityLength a =
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCHES)
                );

        QuantityLength b =
                QuantityLength.add(
                        new QuantityLength(12.0, LengthUnit.INCHES),
                        new QuantityLength(1.0, LengthUnit.FEET)
                );

        assertEquals(a, b);
    }

    @Test
    void testAddition_WithZero() {
        QuantityLength result =
                QuantityLength.add(
                        new QuantityLength(5.0, LengthUnit.FEET),
                        new QuantityLength(0.0, LengthUnit.INCHES)
                );

        assertEquals(5.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_NegativeValues() {
        QuantityLength result =
                QuantityLength.add(
                        new QuantityLength(5.0, LengthUnit.FEET),
                        new QuantityLength(-2.0, LengthUnit.FEET)
                );

        assertEquals(3.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_NullOperand() {
        assertThrows(IllegalArgumentException.class, () ->
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        null
                )
        );
    }

    @Test
    void testAddition_LargeValues() {
        QuantityLength result =
                QuantityLength.add(
                        new QuantityLength(1e6, LengthUnit.FEET),
                        new QuantityLength(1e6, LengthUnit.FEET)
                );

        assertEquals(2e6, result.getValue(), EPS);
    }
}