public class QuickUnionDS implements DisjointSets {
    /**
     * Each entry in this array is the parent of the index. Say 3 is
     * one of the child of 5, then the 3rd slot stores 5. If 1 is the
     * root, then the 1st slot stores -1.
     */
    private int[] parent;

    /* Θ(N), need to set all slots to -1*/
    public QuickUnionDS(int N) {
        parent = new int[N];
        for (int ix = 0; ix < N; ix++) {
            parent[ix] = -1;
        }
    }

    /**
     * O(N)
     */
    @Override
    public void connect(int p, int q) {
        int pRoot = findRoot(p);
        int qRoot = findRoot(q);
        parent[qRoot] = pRoot;
    }

    /*O(N)*/
    @Override
    public boolean isConnected(int p, int q) {
        return findRoot(p) == findRoot(q);
    }

    /**
     * Find root.
     * O(N)
     *
     * @param target Target with which to find the root index
     * @return root index of target number. A special case is that
     * target itself is a root. Then return -1 directly without going
     * into the while loop.
     */
    private int findRoot(int target) {
        int ix = target;
        while (parent[ix] != -1) {
            ix = parent[ix];
        }
        return ix;
    }

}
