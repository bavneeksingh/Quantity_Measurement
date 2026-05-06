public class VolumeMeasurementApp {

    // 🔹 Interface
    interface IMeasurable {
        double getConversionFactor();
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);
    }

    // 🔹 VolumeUnit Enum
    enum VolumeUnit implements IMeasurable {
        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        private final double factor;

        VolumeUnit(double factor) {
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
    }

    // 🔹 Generic Quantity Class
    static class Quantity<U extends IMeasurable> {

        private final double value;
        private final U unit;
        private static final double EPS = 1e-6;

        public Quantity(double value, U unit) {
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

        public U getUnit() {
            return unit;
        }

        // 🔹 Convert
        public Quantity<U> convertTo(U target) {
            double base = unit.convertToBaseUnit(value);
            double result = target.convertFromBaseUnit(base);
            return new Quantity<>(result, target);
        }

        // 🔹 Add (default → first unit)
        public Quantity<U> add(Quantity<U> other) {
            return add(other, this.unit);
        }

        // 🔹 Add (explicit target)
        public Quantity<U> add(Quantity<U> other, U target) {
            if (other == null) {
                throw new IllegalArgumentException("Null operand");
            }

            double base1 = unit.convertToBaseUnit(value);
            double base2 = other.unit.convertToBaseUnit(other.value);

            double sum = base1 + base2;

            double result = target.convertFromBaseUnit(sum);
            return new Quantity<>(result, target);
        }

        // 🔹 Equality
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Quantity<?> other)) return false;

            // Prevent cross-category comparison
            if (!unit.getClass().equals(other.unit.getClass())) return false;

            double base1 = unit.convertToBaseUnit(value);
            double base2 = ((IMeasurable) other.unit)
                    .convertToBaseUnit(other.value);

            return Math.abs(base1 - base2) < EPS;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // 🔹 Main (demo)
    public static void main(String[] args) {

        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

        // Equality
        System.out.println(v1.equals(v2)); // true

        // Conversion
        System.out.println(v1.convertTo(VolumeUnit.MILLILITRE)); // 1000 mL

        // Addition
        System.out.println(v1.add(v2)); // 2 L

        // Explicit unit
        System.out.println(v1.add(v3, VolumeUnit.MILLILITRE)); // ~4785.41 mL
    }
}