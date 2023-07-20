/**
 * 1. A very intuitive way to do this is a List<Set<Integer>>,
 *     but it has theta(N) complexity, and the code is
 *     complicated.
 * 2. A list of integers. The ith entry gives the set number.
 *    The exact number is not important, just make sure different
 *    sets have different numbers.
 * 3. QuickFInd Approach. Downside: If we want to change the set
 *    membership, we need to change many items in order to combine
 *    two sets.
 */

public interface DisjointSets {
    /** connects two items P and Q */
    void connect(int p, int q);

    /** checks to see if two items are connected */
    boolean isConnected(int p, int q);
}
