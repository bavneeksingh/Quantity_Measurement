public class QuantityMeasurementApp {

    // ✅ Enum inside same class
    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double factor; // conversion to feet

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getFactor() {
            return factor;
        }
    }

    // ✅ QuantityLength inside same class
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

        // ✅ Conversion
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (source == null || target == null) {
                throw new IllegalArgumentException("Units cannot be null");
            }

            double base = value * source.getFactor();   // to feet
            return base / target.getFactor();           // to target
        }

        // ✅ Static Add
        public static QuantityLength add(QuantityLength a, QuantityLength b) {
            if (a == null || b == null) {
                throw new IllegalArgumentException("Operands cannot be null");
            }

            double baseA = a.value * a.unit.getFactor();
            double baseB = b.value * b.unit.getFactor();

            double sumBase = baseA + baseB;

            double result = sumBase / a.unit.getFactor();

            return new QuantityLength(result, a.unit);
        }

        // ✅ Overloaded Add
        public static QuantityLength add(double v1, LengthUnit u1,
                                         double v2, LengthUnit u2) {
            return add(new QuantityLength(v1, u1),
                    new QuantityLength(v2, u2));
        }

        // ✅ Instance Add
        public QuantityLength add(QuantityLength other) {
            return add(this, other);
        }

        // ✅ Equals (cross-unit comparison)
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

    // ✅ Demo
    public static void main(String[] args) {

        // Equality test
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCHES);
        System.out.println("Equal? " + q1.equals(q2)); // true

        // Addition test
        QuantityLength result =
                QuantityLength.add(
                        new QuantityLength(1.0, LengthUnit.FEET),
                        new QuantityLength(12.0, LengthUnit.INCHES)
                );

        System.out.println("Addition Result: " + result); // 2.0 FEET
    }
}