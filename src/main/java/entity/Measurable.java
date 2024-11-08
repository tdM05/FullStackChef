package entity;

/**
 * The representation of a measurable entity with a number and a unit.
 *
 * @param <N> the type of the number
 */
public interface Measurable<N> {
    /**
     * Gets the number.
     *
     * @return the number
     */
    N getNumber();

    /**
     * Sets the number.
     *
     * @param number the number
     */
    void setNumber(N number);

    /**
     * Gets the unit.
     *
     * @return the unit
     */
    String getUnit();

    /**
     * Sets the unit.
     *
     * @param unit the unit
     */
    void setUnit(String unit);
}
