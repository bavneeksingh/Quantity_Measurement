public class QuantityMeasurementApp {

    // ================= INTERFACE =================
    interface IMeasurable {
        double getConversionFactor();
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);
        String getUnitName();
    }

    // ================= LENGTH UNIT =================
    enum LengthUnit implements IMeasurable {

        FEET(1.0),
        INCHES(1.0 / 12),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() {
            return factor;
        }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return this.name();
        }
    }

    // ================= WEIGHT UNIT =================
    enum WeightUnit implements IMeasurable {

        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() {
            return factor;
        }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return this.name();
        }
    }

    // ================= GENERIC QUANTITY =================
    static class Quantity<U extends IMeasurable> {

        private final double value;
        private final U unit;
        private static final double EPSILON = 0.0001;

        public Quantity(double value, U unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        // ================= EQUALITY =================
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || this.getClass() != obj.getClass()) return false;

            Quantity<?> that = (Quantity<?>) obj;

            // prevent cross-category comparison
            if (this.unit.getClass() != that.unit.getClass()) return false;

            double base1 = this.unit.convertToBaseUnit(this.value);
            double base2 = that.unit.convertToBaseUnit(that.value);

            return Math.abs(base1 - base2) < EPSILON;
        }

        @Override
        public int hashCode() {
            double base = unit.convertToBaseUnit(value);
            return Double.hashCode(base);
        }

        // ================= CONVERSION =================
        public Quantity<U> convertTo(U targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = unit.convertToBaseUnit(value);
            double converted = targetUnit.convertFromBaseUnit(base);

            return new Quantity<>(round(converted), targetUnit);
        }

        // ================= ADDITION =================
        public Quantity<U> add(Quantity<U> other) {
            return add(other, this.unit);
        }

        public Quantity<U> add(Quantity<U> other, U targetUnit) {
            if (other == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            if (this.unit.getClass() != other.unit.getClass()) {
                throw new IllegalArgumentException("Different measurement categories");
            }

            double base1 = this.unit.convertToBaseUnit(this.value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            double sumBase = base1 + base2;
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new Quantity<>(round(result), targetUnit);
        }

        private double round(double value) {
            return Math.round(value * 100.0) / 100.0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        // LENGTH
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println(l1.equals(l2)); // true
        System.out.println(l1.convertTo(LengthUnit.INCHES)); // 12 inches
        System.out.println(l1.add(l2, LengthUnit.FEET)); // 2 feet

        // WEIGHT
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println(w1.equals(w2)); // true
        System.out.println(w1.convertTo(WeightUnit.GRAM)); // 1000 g
        System.out.println(w1.add(w2, WeightUnit.KILOGRAM)); // 2 kg
    }
}