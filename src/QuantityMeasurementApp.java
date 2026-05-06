public class QuantityMeasurementApp {

    // ✅ Enum inside same class
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

    // ✅ Core logic class inside
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

        // ✅ Static conversion (UC5)
        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (source == null || target == null) {
                throw new IllegalArgumentException("Units cannot be null");
            }

            double baseValue = value * source.getFactor(); // to feet
            return baseValue / target.getFactor();         // to target
        }

        // ✅ Instance conversion
        public QuantityLength convertTo(LengthUnit target) {
            double converted = convert(this.value, this.unit, target);
            return new QuantityLength(converted, target);
        }

        // ✅ Equality (cross-unit)
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

    // ✅ Demo methods
    public static double demonstrateLengthConversion(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        return QuantityLength.convert(value, from, to);
    }

    public static QuantityLength demonstrateLengthConversion(QuantityLength length,
                                                             LengthUnit to) {
        return length.convertTo(to);
    }

    // ✅ Main method
    public static void main(String[] args) {

        // Example 1: direct conversion
        double result1 = demonstrateLengthConversion(1.0,
                LengthUnit.FEET,
                LengthUnit.INCHES);

        System.out.println("1 FEET -> INCHES = " + result1); // 12.0

        // Example 2: object conversion
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = q1.convertTo(LengthUnit.FEET);

        System.out.println("1 YARD -> FEET = " + q2); // 3.0 FEET

        // Example 3: equality
        QuantityLength a = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength b = new QuantityLength(36.0, LengthUnit.INCHES);

        System.out.println("Equal? " + a.equals(b)); // true
    }
}