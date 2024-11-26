package entity;

/**
 * A generic class representing a tuple of length 2.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 */
public class CommonPair<T1, T2> implements Pair<T1, T2> {
    private final T1 first;
    private final T2 second;

    /**
     * Constructs a new CommonPair with the specified elements.
     *
     * @param first the first element
     * @param second the second element
     */
    public CommonPair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first element of the pair.
     *
     * @return the first element
     */
    @Override
    public T1 getFirst() {
        return first;
    }

    /**
     * Returns the second element of the pair.
     *
     * @return the second element
     */
    @Override
    public T2 getSecond() {
        return second;
    }
}