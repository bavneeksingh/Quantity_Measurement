public class QuantityMeasurementApp {

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

        // 🔹 UC6 (existing)
        public static QuantityLength add(QuantityLength a, QuantityLength b) {
            double baseA = a.value * a.unit.getFactor();
            double baseB = b.value * b.unit.getFactor();

            double sumBase = baseA + baseB;

            double result = sumBase / a.unit.getFactor();

            return new QuantityLength(result, a.unit);
        }

        // 🔥 UC7 NEW METHOD (TARGET UNIT)
        public static QuantityLength add(QuantityLength a,
                                         QuantityLength b,
                                         LengthUnit targetUnit) {

            if (a == null || b == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            // Convert both to base (feet)
            double baseA = a.value * a.unit.getFactor();
            double baseB = b.value * b.unit.getFactor();

            double sumBase = baseA + baseB;

            // Convert to target unit
            double result = sumBase / targetUnit.getFactor();

            return new QuantityLength(result, targetUnit);
        }

        // 🔹 Equals
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

        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(
                QuantityLength.add(a, b, LengthUnit.FEET));   // 2.0 FEET

        System.out.println(
                QuantityLength.add(a, b, LengthUnit.INCHES)); // 24.0 INCHES

        System.out.println(
                QuantityLength.add(a, b, LengthUnit.YARDS));  // ~0.667 YARDS
    }
}