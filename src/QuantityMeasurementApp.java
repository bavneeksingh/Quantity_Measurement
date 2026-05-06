public class UC9WeightApp {

    // 🔹 WeightUnit Enum (Standalone with conversion responsibility)
    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor; // to base unit (kg)

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double convertToBaseUnit(double value) {
            return value * factor; // → kg
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor; // kg → unit
        }
    }

    // 🔹 QuantityWeight Class
    static class QuantityWeight {

        private final double value;
        private final WeightUnit unit;
        private static final double EPSILON = 1e-6;

        public QuantityWeight(double value, WeightUnit unit) {
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

        public WeightUnit getUnit() {
            return unit;
        }

        // 🔹 Conversion
        public QuantityWeight convertTo(WeightUnit target) {
            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            double base = unit.convertToBaseUnit(value);
            double result = target.convertFromBaseUnit(base);
            return new QuantityWeight(result, target);
        }

        // 🔹 Addition (default → first operand unit)
        public static QuantityWeight add(QuantityWeight a, QuantityWeight b) {
            return add(a, b, a.unit);
        }

        // 🔹 Addition with explicit target unit (UC7 style)
        public static QuantityWeight add(QuantityWeight a,
                                         QuantityWeight b,
                                         WeightUnit target) {

            if (a == null || b == null || target == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double baseA = a.unit.convertToBaseUnit(a.value);
            double baseB = b.unit.convertToBaseUnit(b.value);

            double sumBase = baseA + baseB;

            double result = target.convertFromBaseUnit(sumBase);

            return new QuantityWeight(result, target);
        }

        // 🔹 equals (cross-unit comparison)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityWeight other = (QuantityWeight) obj;

            double baseThis = unit.convertToBaseUnit(value);
            double baseOther = other.unit.convertToBaseUnit(other.value);

            return Math.abs(baseThis - baseOther) < EPSILON;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔹 MAIN METHOD (Demo)
    public static void main(String[] args) {

        // ✅ Equality
        System.out.println(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                        .equals(new QuantityWeight(1000.0, WeightUnit.GRAM))
        ); // true

        // ✅ Conversion
        System.out.println(
                new QuantityWeight(2.0, WeightUnit.POUND)
                        .convertTo(WeightUnit.KILOGRAM)
        ); // ~0.907 kg

        // ✅ Addition (default)
        System.out.println(
                QuantityWeight.add(
                        new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                        new QuantityWeight(1000.0, WeightUnit.GRAM)
                )
        ); // 2.0 KILOGRAM

        // ✅ Addition (explicit target unit)
        System.out.println(
                QuantityWeight.add(
                        new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                        new QuantityWeight(1000.0, WeightUnit.GRAM),
                        WeightUnit.GRAM
                )
        ); // 2000 GRAM

        // ✅ Mixed units
        System.out.println(
                QuantityWeight.add(
                        new QuantityWeight(2.0, WeightUnit.POUND),
                        new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                        WeightUnit.POUND
                )
        ); // ~4.409 POUND
    }
}