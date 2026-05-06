public class QuantityMeasurementApp {

    // ✅ Feet class
    static class Feet {
        private final double value;

        public Feet(double value) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ✅ Inches class (same structure → DRY violation intentionally for UC2)
    static class Inches {
        private final double value;

        public Inches(double value) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // ✅ Static methods (reduce main dependency)
    public static boolean compareFeet(double a, double b) {
        return new Feet(a).equals(new Feet(b));
    }

    public static boolean compareInches(double a, double b) {
        return new Inches(a).equals(new Inches(b));
    }

    // ✅ Main
    public static void main(String[] args) {

        System.out.println("Feet Equal? " + compareFeet(1.0, 1.0));     // true
        System.out.println("Inches Equal? " + compareInches(1.0, 1.0)); // true
    }
}