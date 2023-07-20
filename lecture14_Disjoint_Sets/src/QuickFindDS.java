public class QuickFindDS implements DisjointSets {
    private int[] id;

    /* Θ(N) */
    public QuickFindDS(int N) {
        id = new int[N];
        for (int ix = 0; ix < N; ix++) {
            id[ix] = ix;  // Each entry is disconnected, so has different set number.
        }
    }

    @Override
    /*Need the traverse the array, Θ(N)*/
    public void connect(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        // Change all the item in pid set to qid set
        // Traverse the array to find the pid's items
        for (int ix = 0; ix < id.length; ix++) {
            if (id[ix] == pid) {
                id[ix] = qid;
            }
        }
    }

    @Override
    /*Θ(1)*/
    public boolean isConnected(int p, int q) {
        return (id[p] == id[q]);
    }
}
