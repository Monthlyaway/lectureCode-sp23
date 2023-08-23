# Lecture 17 - B Trees

## Basic Insertion

The problem is adding new leaves at the bottom. Let's make a new rule: Never add new leaves at the bottom.

SOL1: Overstuffed trees. Stuff multiple items in one leaf.

SOL2: **Set the limit on the number of items.** Give an item to the parent, but this leaves a smaller item on the right. **Split the existing node.** For example, split 16 out as an individual node. We end up with a node with 3 objects. In this case, we move up the **left middle** item if even number or just the **middle item** if odd number.

**What happens when the root is too full?** Move the left middle item up one level, **increase the height.**

## Terminology

B-trees of order L=3 are also called 2-3-4 tree of a 2-4 tree. It refers to the number of children that a node can have.

**L: Limit of number of children a node can have.**

## B-tree Invariants

https://www.cs.usfca.edu/~galles/visualization/BTree.html

- All leaves must be the same distance from the source. Because the height is only increased when the root is split. All nodes are moved down a level.
- A non-leaf node with k items must have exactly k+1 children.

## B-tree runtime analysis

**L: Max number of items per node.**

**Any node with k items must have k+1 children.**

**Height: Between $\log_{L+1}(N)$ and $\log_2(N)$.**  Every node may have 2 to L+1 children.

- Worst case: Every node has only 2 children, which makes the height really big.
- Best case: Every node has L+1 children, height grows with $\log_{L+1}(N)$.

### Runtime for `contains`

- Worst case number of nodes to inspect: H+1
- Worst case number of items to inspect **per node: L**
- Overall runtime: O(HL)

Since $H=\Theta(\log N)$, overall runtime is $O(L \log N)$. Since L is a constant, runtime is therefore $O(\log N)$. The same for `add`.

**WHY** $O$ not $\Theta$ ? Because we are not certain about how many items each node has.

## Summary

一个结点的子树可以理解为对这个结点的划分。因为$k$个items恰好有$k+1$个划分区域，在插入的时候判断要插入的item在parent的第$i$个划分里，就把它插入到第$i$个子结点中。

# Lecture 18 - Red Black Tree

## BST Structure and Tree Rotation

`rotateLeft(G)`: Let P be the right child of G.

1. Merge G and P.
2. Send G down and left.
3. Decide which children go with G and which children stay with P. In this case, the children smaller than P become children of G, while the others become siblings of G.

**When we move G down and left, we should take all children smaller than P to the left.**

### Rotation for Balance

Rotation:

- Can shorten (or lengthen) a tree. Or no effect at all.
- Preserves search tree property.

https://youtu.be/b4-2-6R2gzU?list=PL8FaHk7qbOD6aKgTz2W-foDiTeBEaBoS3&t=293

## Red Black Tree Definition

- We can use rotation to balance BST
- 2-3 Tree is balanced by construction, i.e. no rotations required.
- Our goal: build a special BST identical to 2-3 tree.

### Dealing with 3-nodes

- Create dummy glue nodes.
- Create glue links with smaller items off to the left. We avoid wasting a link. This is actually the second step in rotation, consider 3-nodes as merged nodes.

### Left-Leaning Red Black Binary Search Tree (LLRB)

- LLRB are normal BSTs.
- Red links are the glue links when constructing an LLRB from 2-3 tree.
- There is a 1-1 correspondence between an LLRB and an equivalent 2-3 tree.

## RBT Properties

`contains`

exactly like a BST.

**Determine valid LLTBs**:

- Merge the nodes through red links.
- Check the 2-3 tree is valid or not
  - A node with k items should have k+1 children.
  - All leaves should have the **same distance** with root.

**How tall is the corresponding LLRB**

Each 3-node becomes two nodes in the LLRB. Total height is 
$$
\text{black + red}=\text{height}
$$
This computation is valid when we know what the LLRB looks like. But if we only have the corresponding 2-3 tree, we construct the longest path from root to leaf. Since every node on this path can contain at most 2 items, the height of LLRB is at most
$$
2 \times \text{height of its 2-3 tree} + 1
$$
Or consider the black link is between floors, the red link is inside nodes. H(black) + H + 1(red) = 2H + 1

**Some handy properties**

- No node has two red links: Otherwise in the 2-3 tree the original node would contain 3 items, which is not allowed. **There can not be more than two red links with one node, whether it's connected to parent of children.**
- Every path from root to a leaf has the same number of <u>**black links**</u> because 2-3 trees have the same number of links to every leaf. So if we just consider the number of black links from root to leaf, LLRB is balanced.
- No red right links, because of left lean operation. Every time the left item goes down a level.

Since height of a 2-3 tree is $O(\log N)$, and height of an LLRB is no more than 2H+1, height of and LLRB is also $O(\log N)$.

## Red Black Tree Insertion

There exists a 1-1 mapping between: 2-3 tree and LLRB

- Insertion color.
- Fix leaning right links. Use `rotateLeft`
- New rule: represent a temporary 4 node in LLRB with a node connected to 2 red links to 2 children. Remember 4 node means a node has 3 items and 4 children. Since this is a leaf node, it has no children, so just consider the number of items.

- Flip the colors of all edges touching B. This maintains the balance .

### Insertion Summary

- When inserting, use a red link.
- If there is a right leaning "3-node" (or right leaning red link), we have a **Left Leaning Violation**.
  - <u>Rotate left</u> the appropriate node to fix.
- If there are two consecutive left links, we have an **Incorrect 4 Node Violation**.
  - <u>Rotate right</u> the appropriate node to fix.
- If there are any nodes with two red children, we have a **Temporary 4 Node**
  - <u>Color flip</u> the node to emulate the split operation.

### Cascading Balance Example

Left leaning rule is to ensure 1-1 mapping.

# Lecture 19 - Hash Tables

Remember runtime analysis should consider the worst case.

## Intro

1. Items need to be comparable.
2. $\Theta(\log N)$ is not good enough.

**Using Data as an Index**

## Data Indexed English Word Sets

```java
DataIndexedSet<String>
```

**Avoiding Collisions**

1. Review how numbers are represented in decimal. Change **base to 27**.
2. As long as we pick **a base $\ge$ 26,** this algorithm is guaranteed to give each lowercase English word a unique number!
3. Implement `englishToInt`

## ASCII and Data Indexed String Sets

**ASCII Characters**

1. Each possible character is assigned a value between 0 and 127.
2. Character 33 - 126 are "printable".
3. **To show uppercase letter, just increase the base?**
4. Really really big! **Integer Overflow**

**Hash Codes and the Pigeonhole Principle**

1. A hash code projects a value from a set with many (or even an infinite number of) members to a value from a set with a fixed number of (fewer) members.
2. Here, our target is the set of Java integers, which is of size 4294967296.
3. Pigeonhole  principle tells us multiple items share the same hole.

## Separate Chains and Hash Tables

**Intro**

1. Instead of storing`true` in position h, store a **bucket** of these N items at position h.
2. Use **array of LinkedList**. Each list contains items with the same hash code.

**Separate Chaining Performance**

Consider the worst case, let Q be the length of the longest list. $\Theta(Q)$

**Saving Memory Using Separate Chaining and Modulus**

Can use modulus of hash code to reduce bucket count. Cause the list to be longer. If we use 10 bucket, the bucket index should be hash code % 10.

Q is $\Theta(N)$, which is not good. How to fix this.

1. An increasing number of buckets $M$.
2. An increasing number of items $N$.

$$
\text{load factor}=\frac{N}{M}
$$

Whenever load factor is greater than a constant (say 1.5), resize $M$ to $2M$. As long as $M=\Theta(N)$.

# Lecture 20 - Hashing 2

## Recap

1. Generate the hash code.
2. Resize when load factor exceeds some constant.
3. If items are spread out nicely, you get $\Theta(1)$ average run time. Because load factor is always small relative to $N$.

**First Insight**

1. The default hash code is usually just the **address** of the object.
2. You can Override the default `hashCode()`.
3. Other examples: mod, return num directly. leading digit, sum of the digits, 

## Why Custom Hash Functions

**Remind**

```java
o instanceof ColoredNumbr otherCn
```

If two objects are equal, they'd better have the same hash code so the hash table can find it. Consider `contains()`.

## Duplicate

If `equals()` checks whether two objects have the same address. What happen when we call `add(zero)`.

- We get a 0 to bin zero.
- We add another 0 to some other bin. (address mod rule)
- We do not get a duplicate zero. First compute the hash code, turns out the zero should belong to bin one. Then check any items are equal to zero in this bin.

**Bottom line**: Override `equals`, also override `hashCode`.

**Immutable Data Types**: an instance cannot change after instantiation. Use `final ` keyword to show it is immutable.

- Mutable: `ArrayDeque`, Percolation.
- Immutable: Hash Table.

```java
public class RocksBox{
    public final Rock[] rocks;
    public RocksBox(Rock[] r){
        rocks = r;
    }
}

rb.rocks[0] = null;
```

`RocksBox` is mutable because although rocks, which is a reference to the array, can not change, the array's element can change.

One solution is make the `rocks ` private. But this sometimes vulnerable if you change `rox[] `after `rb` is instantiated. Use `arraycopy `to build a personal array.

## Don't Mutate Objects in a Hash Set

1. A `HashSet `of `ArrayList`.
2. Change one of the list, may be add something.
3. As the item's hash code is changed, things go wrong when calling `contains`.

**Don't mutate Keys**

Bottom line: Never mutate an Object being used as a key.

# Lecture 23 -  Graph Traversals and Implementations

To find the shortest path from vertex s to any other vertex, we can use the **Breadth First Search**. 

## Breadth First Search

Goal: Find the shortest path between s and every other vertex.

- Initialize the **fringe** (a **queue** with a starting vertex s)  and mark that vertex. First come first go.
- Repeat until the fringe is empty:
  - Remove vertex v from fringe.
  - For each unmarked neighbor n of v: mark n, add n to fringe, set `edgeTo[n] = v`, set `distTo[n] = distTo[v] + 1`

The queue is forcing us to go to the closet place first. Consider the meaning of fringe (similar to edge).

One use of BFS: **Kevin Bacon**. How many paper are there between my paper with Einstein's relativity theory.

This algorithm can not be used in Google Maps without modification because not all roads take the same amount of time to travel. One possible solution is to weight the edges. 

## Graph API

**Decision 1: Integer Vertices**

```java
int V();  // return the number of vertices, assume to start from 0
```

**Graph Representation 1: Adjacency Matrix**

- Undirected graph matrix is symmetric. 
- `G.adj(v)` takes $\Theta(V)$ time because we have to iterate through a whole line of the matrix. Size = V.

**Graph Representation 2: Edge Sets: Collection of all edges**

**Graph Representation 3: Adjacency Lists**

Common approach: Maintain array of lists indexed by vertex number. 

What is the order of growth of the running time of the print client if the graph uses an adjacency-list representation?
$$
\Theta(V+E)
$$

- Create V iterators.
- Print E times. 

## Depth First Search Implementation

Common design pattern in graph algorithms: Decouple type from processing algorithm.

- Create a graph object.
- Pass the graph to the a graph-processing method (or constructor) in a client class. 
- Query the client class for information. 

Runtime for Depth First Search constructor: Cost models are as follows: 

- Number of `dfs `calls (recursion) 
- `marked[w]` calls 

$$
O(V+E)
$$

- Each vertex is visited (called recursively) at most **once**. 
- Each edge is considered at most **twice**, back and forth. 

https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/

# Lecture 25, 2019 - Shortest Paths

## BFS vs. DFS for Path Finding

**Space Efficiency**

- DFS is worse for spindly graph: DFS use a call stack to track the vertices that have not been traversed. Stack is LCFG data structure. If the graph is spindly, the call stack would be very deep, so the computer needs $\Theta(V)$ memory to remember the recursive call.
- BFS is worse for bushy graph: BFS use a queue to track the vertices. Queue is a FCFG data structure. For  each vertex in the queue, dequeue it, enqueue all the vertices it connects to the rein. So if a vertex connects to all the other vertices in the graph, these vertices need to be enqueued at once.
- But we already store the graph using a $\Theta(V)$ memory to store the graph.

**Notes**: Breadth First Search returns the shortest path. But it does not take edge weight into consideration.

## Dijkstra's Algorithm

**Observation**: The solution would never contains cycle.

**The solution is always a tree：** We keep track of the visited vertex and unvisited vertex. Once we visited vertex A, we won't visit it in the following procedure, which means no cycle in the graph, which means a tree. If the calculated distance is less than the known distance, update the shortest distance.

**Single Source Shortest Path:** Only one source, exists a shortest path tree.

**How many edges are there in the shortest edge tree:** $V-1$. Because for every vertex there exists exactly one input edge (except source). Consider the `edgeTo[]` array.

**Edge relaxation:** Update when better distance is found.

**Why the best?** Because we visit every vertex and every edge coming out of this vertex, so we actually examines every possible path in this graph. The DSF kind of approach ensures that we traverse every vertex. 

**Insert all the vertices into fringe PQ.** Use induction.

**Dijkstra's:**

- `PQ.add(source, 0): add(vertex, distance)`
- `PQ.add(other vertices, infinity)`
- While `PQ`  is not empty:
  - `p = PQ.removeSmallest()`
  - Relax all edges from p

**Relaxation:** relax edge p -> q with weight w

- If `distTO[p] + w < distTo[q]`
  - `distTo[q] = distTO[p] + w`
  - `edgeTo[q] = p`
  - `PQ.changePriority(q, distTo[q])`

**Key Invariants:**

- PQ consists of unvisited vertices in order of `distTo`.
- Visit in order of **best known distance**.

## A*

**Bias the algorithm:** estimated v to goal.

The only change if the fringe PQ, we now store `d(source, v) + estimated(v, goal)` in fringe so that we can bias the algorithm to think about different vertex first.
