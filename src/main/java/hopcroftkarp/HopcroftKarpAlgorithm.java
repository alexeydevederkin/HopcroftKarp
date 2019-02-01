package hopcroftkarp;

import java.util.*;


/*
 *    Hopcroft–Karp algorithm
 *
 *  An algorithm that takes as input a bipartite graph
 *  and produces as output a maximum cardinality matching –
 *  a set of as many edges as possible with the property
 *  that no two edges share an endpoint.
 *
 */
public class HopcroftKarpAlgorithm {

    private static final int nil = 0;
    private static final int infinity = Integer.MAX_VALUE;
    private static List<Integer>[] edgesList;
    private static int[] pair;
    private static int[] dist;
    private static int cv;
    private static int cu;

    /*
     *  Returns true if there is an augmenting path
     */
    private static boolean BFS() {
        Queue<Integer> queue = new ArrayDeque<>();

        // First layer of vertices (set distance to 0)
        for (int v = 1; v <= cv; v++) {
            // If this is a free vertex, add it to queue
            if (pair[v] == nil) {
                dist[v] = 0;  // v is not matched
                queue.add(v);
            } else {
                dist[v] = infinity;  // infinity so that this vertex is considered next time
            }
        }

        // Initialize distance to NIL as infinity
        dist[nil] = infinity;

        // Q is going to contain vertices of the left side only
        while (!queue.isEmpty()) {
            int v = queue.poll();

            // If this node is not NIL and can provide a shorter path to NIL
            if (dist[v] < dist[nil]) {

                for (int u : edgesList[v]) {

                    // If pair of u is not considered so far
                    // (u, pair[u]) is not yet explored edge
                    if (dist[pair[u]] == infinity) {

                        // Consider the pair and add it to queue
                        dist[pair[u]] = dist[v] + 1;
                        queue.add(pair[u]);
                    }
                }
            }
        }

        // If we could come back to NIL using alternating path of distinct vertices
        // then there is an augmenting path
        return dist[nil] != infinity;
    }


    /*
     *  Returns true if there is an augmenting path beginning with free vertex v
     */
    private static boolean DFS(int v) {
        if (v != nil) {
            for (int u : edgesList[v]) {
                // Follow the distances set by BFS
                if (dist[pair[u]] == dist[v] + 1) {
                    // If dfs for pair of u also returns true
                    if (DFS(pair[u])) {
                        pair[u] = v;
                        pair[v] = u;
                        return true;
                    }
                }
            }

            // If there is no augmenting path beginning with v
            dist[v] = infinity;
            return false;
        }
        return true;
    }


    /*
     *  Returns the size of maximum matching
     */
    private static int HopcroftKarp() {
        // pair[v] stores pair of v in matching. If v doesn't have any pair, then pair[v] is 0
        pair = new int[cv + cu + 1];

        // dist[v] stores distance of vertices
        // dist[v] is one more than dist[v'] if v is next to v' in augmenting path
        dist = new int[cv + cu + 1];

        int matching = 0;

        // Keep updating the result while there is an augmenting path
        while (BFS()) {
            // Find a free vertex
            for (int v = 1; v <= cv; v++) {
                // If current vertex is free and there is an augmenting path from current vertex
                if (pair[v] == nil && DFS(v)) {
                    matching++;
                }
            }
        }
        return matching;
    }


    public static void main(String[] args) {
        /*
        Solution for the problem of optimal assignements.

        For instance, 5 employees and 5 jobs represented as matrix ("1" if employee can do the job).

        employee  job1  job2  job3  job4  job5
           0       1     1     1     1     1
           1       1     0     0     1     0
           2       0     1     0     1     0
           3       0     1     0     1     1
           4       1     0     0     0     0

        Optimal solution:

        0 - job3
        1 - job4
        2 - job2
        3 - job5
        4 - job1

        So maximum assignements is 5.

        input:
        5 5
        1 1 1 1 1
        1 0 0 1 0
        0 1 0 1 0
        0 1 0 1 1
        1 0 0 0 0

        output:
        5
        */

        Scanner in = new Scanner(System.in);

        String[] nmline = in.nextLine().split(" ");
        cv = Integer.parseInt(nmline[0]);
        cu = Integer.parseInt(nmline[1]);

        if (cv < 1 || cu < 1) {
            System.out.println("0");
            return;
        }

        edgesList = new List[cv + cu + 1];
        for (int i = 0; i < edgesList.length; i++)
            edgesList[i] = new ArrayList<>();

        for (int i = 0; i < cv; i++) {
            String[] skillLine = in.nextLine().split(" ");
            for (int j = 0; j < skillLine.length; j++) {
                if (Integer.parseInt(skillLine[j]) == 1) {

                    // "+1" since edgesList[0] is the special vertex
                    int from = i + 1;
                    int to = j + 1;

                    /*
                    [0][... cv ...][... cu ...]

                    Example:
                    Edges: v1 - u2, v2 - u1, v3 - u1, v3 - u2
                    0 [--]
                    1 [v1] -> [5]
                    2 [v2] -> [4]
                    3 [v3] -> [4, 5]
                    4 [u1] -> [2, 3]
                    5 [u2] -> [1, 3]
                    */
                    edgesList[from].add(cv + to);
                    edgesList[cv + to].add(from);
                }
            }
        }

        int result = HopcroftKarp();

        System.out.println(result);
    }
}

