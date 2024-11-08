package entity;

/**
 * A common implementation of the Measurable interface.
 * @param <N> the type of the number
 */
public class CommonMeasurable<N> implements Measurable<N> {
    private N number;
    private String unit;

    public CommonMeasurable(N number, String unit) {
        this.number = number;
        this.unit = unit;
    }

    @Override
    public N getNumber() {
        return number;
    }

    @Override
    public void setNumber(N number) {
        this.number = number;
    }

    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
