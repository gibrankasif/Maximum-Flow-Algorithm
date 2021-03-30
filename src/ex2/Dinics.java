package ex2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import static java.lang.Math.min;

import java.util.*;

public class Dinics extends NetworkFlowSolverBase {

    private int[] level;

    /**
     * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
     * the graph.
     *
     * @param n - The number of nodes in the graph including source and sink nodes.
     * @param s - The index of the source node, 0 <= s < n
     * @param t - The index of the sink node, 0 <= t < n, t != s
     */
    public Dinics(int n, int s, int t) {
        super(n, s, t);
        level = new int[n];
    }

    @Override
    public void solve() {
        // next[i] indicates the next unused edge index in the adjacency list for node i. This is part
        // of the Shimon Even and Alon Itai optimization of pruning deads ends as part of the DFS phase.
        int[] next = new int[n];

        while (bfs()) {
            Arrays.fill(next, 0);
            // Find max flow by adding all augmenting path flows.
            for (long f = dfs(s, next, INF); f != 0; f = dfs(s, next, INF)) {
                maxFlow += f;
            }
        }
        for (int i = 0; i < n; i++) if (level[i] != -1) minCut[i] = true;
    }

    // Do a BFS from source to sink and compute the depth/level of each node
    // which is the minimum number of edges from that node to the source.
    private boolean bfs() {
        Arrays.fill(level, -1);
        level[s] = 0;
        Deque<Integer> q = new ArrayDeque<>(n);
        q.offer(s);
        while (!q.isEmpty()) {
            int node = q.poll();
            for (Edge edge : graph[node]) {
                long cap = edge.remainingCapacity();
                if (cap > 0 && level[edge.to] == -1) {
                    level[edge.to] = level[node] + 1;
                    q.offer(edge.to);
                }
            }
        }
        return level[t] != -1;
    }

    private long dfs(int at, int[] next, long flow) {
        if (at == t) return flow;
        final int numEdges = graph[at].size();

        for (; next[at] < numEdges; next[at]++) {
            Edge edge = graph[at].get(next[at]);
            long cap = edge.remainingCapacity();
            if (cap > 0 && level[edge.to] == level[at] + 1) {

                long bottleNeck = dfs(edge.to, next, min(flow, cap));
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck);
                    return bottleNeck;
                }
            }
        }
        return 0;
    }

    /* Examples */

    public static void main(String[] args) throws FileNotFoundException {
        testSmallFlowGraph();
        // testGraphFromSlides();
    }

    // Testing graph from:
    // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
    private static void testSmallFlowGraph() throws FileNotFoundException {
        int n = 6;
        int s = n - 1;
        int t = n - 2;

        Dinics solver;
        solver = graphReader("ladder_9.txt");



        long start = System.nanoTime();
        System.out.printf("Maximum flow %d\n", solver.getMaxFlow()); // 30
        System.out.println(System.nanoTime() - start);
    }

    private static void testGraphFromSlides() {
        int n = 11;
        int s = n - 1;
        int t = n - 2;

        NetworkFlowSolverBase solver;
        solver = new Dinics(n, s, t);

        // Source edges
        solver.addEdge(s, 0, 5);
        solver.addEdge(s, 1, 10);
        solver.addEdge(s, 2, 15);

        // Middle edges
        solver.addEdge(0, 3, 10);
        solver.addEdge(1, 0, 15);
        solver.addEdge(1, 4, 20);
        solver.addEdge(2, 5, 25);
        solver.addEdge(3, 4, 25);
        solver.addEdge(3, 6, 10);
        solver.addEdge(3, 7, 20);
        solver.addEdge(4, 2, 5);
        solver.addEdge(4, 7, 30);
        solver.addEdge(5, 7, 20);
        solver.addEdge(5, 8, 10);
        solver.addEdge(7, 8, 15);

        // Sink edges
        solver.addEdge(6, t, 5);
        solver.addEdge(7, t, 15);
        solver.addEdge(8, t, 10);
        float start = System.nanoTime();
        System.out.printf("Maximum flow %d\n", solver.getMaxFlow()); // 30
        System.out.println(System.nanoTime() - start);
    }
    public static Dinics graphReader(String fileName) throws FileNotFoundException {
        try {
            Dinics graph;
            File file = new File("Inputs/"  + fileName);
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);
            String nodeQty = sc.nextLine().trim();
            int number = Integer.parseInt(nodeQty);

            graph = new Dinics(number,0, number- 1);

            int[] nodeConnections = new int[3];
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] count = line.split(" ");
                for(int j = 0; j < count.length; j++) {
                    nodeConnections[j] = Integer.parseInt(count[j]);
                }
                graph.addEdge(nodeConnections[0], nodeConnections[1], nodeConnections[2]);
            }
            return graph;
        } catch(FileNotFoundException ex1){
            System.out.println("File "+ fileName +" does not exist!");
            return null;
        }
    }
}