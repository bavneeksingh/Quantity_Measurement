import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
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

    // =========================
    // SUBTRACTION METHODS
    // =========================

    // Implicit target unit (this.unit)
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    // Explicit target unit
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateOperand(other);
        validateUnit(targetUnit);
        ensureSameCategory(other);

        double thisBase = unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        double resultBase = thisBase - otherBase;

        double result = targetUnit.convertFromBaseUnit(resultBase);
        result = round(result);

        return new Quantity<>(result, targetUnit);
    }

    // =========================
    // DIVISION METHOD
    // =========================

    public double divide(Quantity<U> other) {
        validateOperand(other);
        ensureSameCategory(other);

        double otherBase = other.unit.convertToBaseUnit(other.value);
        if (otherBase == 0.0) {
            throw new ArithmeticException("Division by zero");
        }

        double thisBase = unit.convertToBaseUnit(this.value);

        return thisBase / otherBase;
    }

    // =========================
    // VALIDATION HELPERS
    // =========================

    private void validateOperand(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Operand cannot be null");
        }
        if (Double.isNaN(other.value) || Double.isInfinite(other.value)) {
            throw new IllegalArgumentException("Invalid operand value");
        }
    }

    private void validateUnit(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
    }

    private void ensureSameCategory(Quantity<U> other) {
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Cross-category operation not allowed");
        }
    }

    // =========================
    // UTILITY METHODS
    // =========================

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit((double) other.value);

        return Math.abs(thisBase - otherBase) < 0.0001;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.convertToBaseUnit(value));
    }
}