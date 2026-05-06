public class QuantityMeasurementApp {

    // ✅ Standalone-style enum (inside same file for simplicity)
    enum LengthUnit {

        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor; // relative to feet

        LengthUnit(double factor) {
            this.factor = factor;
        }

        // 🔹 to base (feet)
        public double convertToBaseUnit(double value) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            return value * factor;
        }

        // 🔹 from base (feet)
        public double convertFromBaseUnit(double baseValue) {
            if (!Double.isFinite(baseValue)) {
                throw new IllegalArgumentException("Invalid value");
            }
            return baseValue / factor;
        }
    }

    // ✅ QuantityLength (refactored)
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

        // 🔹 Convert
        public QuantityLength convertTo(LengthUnit target) {
            if (target == null) {
                throw new IllegalArgumentException("Target unit null");
            }

            double base = unit.convertToBaseUnit(value);
            double result = target.convertFromBaseUnit(base);

            return new QuantityLength(result, target);
        }

        // 🔹 UC6 add (default)
        public static QuantityLength add(QuantityLength a, QuantityLength b) {
            return add(a, b, a.unit);
        }

        // 🔥 UC7 add (target unit)
        public static QuantityLength add(QuantityLength a,
                                         QuantityLength b,
                                         LengthUnit target) {

            if (a == null || b == null || target == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double baseA = a.unit.convertToBaseUnit(a.value);
            double baseB = b.unit.convertToBaseUnit(b.value);

            double sumBase = baseA + baseB;

            double result = target.convertFromBaseUnit(sumBase);

            return new QuantityLength(result, target);
        }

        // 🔹 equals
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisBase = unit.convertToBaseUnit(value);
            double otherBase = other.unit.convertToBaseUnit(other.value);

            return Math.abs(thisBase - otherBase) < EPSILON;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // ✅ Main (demo)
    public static void main(String[] args) {

        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCHES);

        // Conversion
        System.out.println(a.convertTo(LengthUnit.INCHES)); // 12.0 INCHES

        // Equality
        System.out.println(a.equals(b)); // true

        // UC6 addition
        System.out.println(QuantityLength.add(a, b)); // 2.0 FEET

        // UC7 addition with target
        System.out.println(
                QuantityLength.add(a, b, LengthUnit.YARDS)); // ~0.667 YARDS
    }
}