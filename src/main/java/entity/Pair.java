package entity;

/**
 * A generic interface representing a tuple of length 2.
 *
 * @param <T1> the type of the first element
 * @param <T2> the type of the second element
 */
public interface Pair<T1, T2> {
    /**
     * Returns the first element of the pair.
     *
     * @return the first element
     */
    T1 getFirst();

    /**
     * Returns the second element of the pair.
     *
     * @return the second element
     */
    T2 getSecond();
}