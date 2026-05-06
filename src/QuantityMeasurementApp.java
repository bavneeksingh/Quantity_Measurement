public class QuantityMeasurementApp {

    // ✅ Updated Enum (ONLY place we change code)
    enum LengthUnit {
        FEET(1.0),

        INCH(1.0 / 12.0),        // 1 inch = 1/12 feet

        YARD(3.0),               // 1 yard = 3 feet

        CENTIMETER(0.0328084);   // 1 cm = 0.0328084 feet
        // (0.393701 inch ÷ 12)

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // ✅ Same class as UC3 (NO changes needed)
    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        public double toBaseUnit() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null) return false;

            if (getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
    }

    // ✅ Demo
    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCH);

        System.out.println(q1.equals(q2)); // true
    }
}