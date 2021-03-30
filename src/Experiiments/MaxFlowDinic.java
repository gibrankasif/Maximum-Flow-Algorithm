package Experiiments;


import com.gibran.MaximumFlow.Graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MaxFlowDinic {

    static class Edge {
        int t, rev, cap, f;

        public Edge(int t, int rev, int cap) {
            this.t = t;
            this.rev = rev;
            this.cap = cap;
        }
    }

    public static List<Edge>[] createGraph(int nodes) {
        List<Edge>[] graph = new List[nodes];
        for (int i = 0; i < nodes; i++)
        graph[i] = new ArrayList<>();
        return graph;
    }

    public static void addEdge(List<Edge>[] graph, int s, int t, int cap) {
        graph[s].add(new Edge(t, graph[t].size(), cap));
        graph[t].add(new Edge(s, graph[s].size() - 1, 0));
    }

    static boolean dinicBfs(List<Edge>[] graph, int src, int dest, int[] dist) {
        Arrays.fill(dist, -1);
        dist[src] = 0;
        int[] Q = new int[graph.length];
        int sizeQ = 0;
        Q[sizeQ++] = src;
        for (int i = 0; i < sizeQ; i++) {
            int u = Q[i];
            for (Edge e : graph[u]) {
            if (dist[e.t] < 0 && e.f < e.cap) {
                dist[e.t] = dist[u] + 1;
                Q[sizeQ++] = e.t;
            }
        }
        }
        return dist[dest] >= 0;
    }

    static int dinicDfs(List<Edge>[] graph, int[] ptr, int[] dist, int dest, int u, int f) {
        if (u == dest)
            return f;
        for (; ptr[u] < graph[u].size(); ++ptr[u]) {
            Edge e = graph[u].get(ptr[u]);
            if (dist[e.t] == dist[u] + 1 && e.f < e.cap) {
                int df = dinicDfs(graph, ptr, dist, dest, e.t, Math.min(f, e.cap - e.f));
                if (df > 0) {
                    e.f += df;
                    graph[e.t].get(e.rev).f -= df;
                    return df;
                }
            }
        }
        return 0;
    }

    public static int maxFlow(List<Edge>[] graph, int src, int dest) {
        int flow = 0;
        int[] dist = new int[graph.length];
        while (dinicBfs(graph, src, dest, dist)) {
            int[] ptr = new int[graph.length];
            while (true) {
                int df = dinicDfs(graph, ptr, dist, dest, src, Integer.MAX_VALUE);
                if (df == 0)
                    break;
                flow += df;
            }
        }
        return flow;
    }

    // Usage example
    public static void main(String[] args) throws FileNotFoundException {

        List<Edge>[] graph = graphReader2("bridge_9.txt");


        long startTime = System.nanoTime();
        System.out.println(maxFlow(graph, 0, 1025));
        long estimatedTIme = System.nanoTime() - startTime;
        System.out.println((estimatedTIme));
    }


    public static List<Edge>[] graphReader2(String fileName) throws FileNotFoundException {
        try {
            List<Edge>[] graph;
            File file = new File("Inputs/"  + fileName);
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);
            String nodeQty = sc.nextLine().trim();
            graph = createGraph(Integer.parseInt(nodeQty));

            int[] nodeConnections = new int[3];
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] count = line.split(" ");
                for(int j = 0; j < count.length; j++) {
                    nodeConnections[j] = Integer.parseInt(count[j]);
                }
                addEdge(graph,nodeConnections[0], nodeConnections[1], nodeConnections[2]);
            }
            return graph;
        } catch(FileNotFoundException ex1){
            System.out.println("File "+ fileName +" does not exist!");
            return null;
        }
    }
}