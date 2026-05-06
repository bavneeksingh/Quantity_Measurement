public enum LengthUnit {

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
public class QuantityLength {

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

    // 🔹 Static API (UC5 requirement)
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        // Convert to base (feet)
        double baseValue = value * source.getFactor();

        // Convert to target
        return baseValue / target.getFactor();
    }

    // 🔹 Instance method (immutability)
    public QuantityLength convertTo(LengthUnit target) {
        double converted = convert(this.value, this.unit, target);
        return new QuantityLength(converted, target);
    }

    // 🔹 equals override (compare via base unit)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisBase = this.value * this.unit.getFactor();
        double otherBase = other.value * other.unit.getFactor();

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    // 🔹 toString override
    @Override
    public String toString() {
        return value + " " + unit;
    }
}
public class QuantityMeasurementApp {

    public static double demonstrateLengthConversion(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        return QuantityLength.convert(value, from, to);
    }

    // Overloaded method
    public static QuantityLength demonstrateLengthConversion(QuantityLength length,
                                                             LengthUnit to) {
        return length.convertTo(to);
    }

    public static void main(String[] args) {
        System.out.println(convert(1.0, LengthUnit.FEET, LengthUnit.INCHES)); // 12.0
    }

    public static double convert(double value, LengthUnit from, LengthUnit to) {
        return QuantityLength.convert(value, from, to);
    }
}