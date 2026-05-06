public class QuantityMeasurementApp {

    // ✅ Enum
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    // ✅ Core Class
    static class QuantityLength {

        private final double value;
        private final LengthUnit unit;
        private static final double EPSILON = 1e-6;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
            return unit;
        }

        // ✅ UC5 Conversion
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (source == null || target == null) {
                throw new IllegalArgumentException("Units cannot be null");
            }

            double base = value * source.getFactor(); // to feet
            return base / target.getFactor();
        }

        // 🔥 UC5 Addition (MAIN FEATURE)
        public static QuantityLength add(QuantityLength a, QuantityLength b) {
            if (a == null || b == null) {
                throw new IllegalArgumentException("Operands cannot be null");
            }

            // Convert both to base unit (feet)
            double baseA = a.value * a.unit.getFactor();
            double baseB = b.value * b.unit.getFactor();

            double sumBase = baseA + baseB;

            // Convert result back to unit of FIRST operand
            double result = sumBase / a.unit.getFactor();

            return new QuantityLength(result, a.unit);
        }

        // ✅ Instance version
        public QuantityLength add(QuantityLength other) {
            return add(this, other);
        }

        // ✅ Equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisBase = this.value * this.unit.getFactor();
            double otherBase = other.value * other.unit.getFactor();

            return Math.abs(thisBase - otherBase) < EPSILON;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ✅ MAIN (Demo for UC5)
    public static void main(String[] args) {

        // 🔹 Test 1: Feet + Inches
        QuantityLength result1 =
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCHES)
                );
        System.out.println(result1); // 2.0 FEET

        // 🔹 Test 2: Yard + Feet
        QuantityLength result2 =
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.YARDS),
                        new QuantityLength(3.0, LengthUnit.FEET)
                );
        System.out.println(result2); // 2.0 YARDS

        // 🔹 Test 3: Cm + Inch
        QuantityLength result3 =
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.CENTIMETERS),
                        new QuantityLength(0.393701, LengthUnit.INCHES)
                );
        System.out.println(result3); // ≈ 2.0 CENTIMETERS

        // 🔹 Test 4: Same unit
        QuantityLength result4 =
                new QuantityLength(2.0, LengthUnit.FEET)
                        .add(new QuantityLength(3.0, LengthUnit.FEET));
        System.out.println(result4); // 5.0 FEET
    }
}