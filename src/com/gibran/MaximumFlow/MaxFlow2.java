package com.gibran.MaximumFlow;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class MaxFlow2 {
//    public int DFS(int start, boolean[] visited, Graph graph)
//    {
//        // Print the current node
//
//        // Set current node as visited
//        visited[start] = true;
//
//        // For every node of the graph
//        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
//
//            // If some node is adjacent to the current node
//            // and it has not already been visited
//            if (graph.getAdjacencyMatrix()[start][i] == 1 && (!visited[i])) {
//                DFS(i, visited, graph);
//            }
//        }
//
//    }

//    public static void dfs(int i, int[][] graph, boolean[] visited) {
//        if(!visited[i]){
//            visited[i] = true; // Mark node as "visited"
//            System.out.print(i+1 + " ");
//
//            for (int j = 0; j < graph[i].length; j++) {
//                if (graph[i][j]==1 && !visited[j]) {
//                    dfs(j, graph, visited); // Visit node
//                }
//            }
//        }
//    }
//    public static void main(String[] args) throws FileNotFoundException {
//        // your matrix declare
//        FileRead fR = new FileRead();
//        Graph graph = fR.graphReader("ladder_9.txt");
//        int[][] matrix = graph.getAdjacencyMatrix();
//        boolean [] visited = new boolean[graph.getNumberOfNodes()];
//        int count = 0;
//        for(int i = 0; i < matrix.length; i++) {
//            if(!visited[i]) {
//                System.out.println("Compnent: " );
//                dfs(i,matrix,visited);
//                ++count;
//            }
//        }
//        System.out.println("Total number of Components: " + count);
//
//}}
    private static Graph graph;
    public static int depthFirstSearch(int source, int flow, Graph graph, int[] deepRoot) {
        int[][] adjMatrix = graph.getAdjacencyMatrix();
        if(source == graph.getNumberOfNodes() - 1)
            return flow;
        int newFlow = 0;
        for (int i = 0; i < graph.getNumberOfNodes()- 1; i++){
            if(adjMatrix[source][i] > 0 && deepRoot[source] + 1 == deepRoot[i] && (newFlow == depthFirstSearch(i,Integer.min(flow, adjMatrix[source][i]), graph, deepRoot))){
                adjMatrix[source][i] -= newFlow;
                adjMatrix[i][source] += newFlow;
                return newFlow;
            }
        }
        return 0;

    }

    public static int Dinic(Graph res, int src, int dest, int[] parent, int[] deepRoot) {
        int flow = 0;
        while (BreadthFirstSearch.bfs(res, src, dest, parent)) {

            flow += depthFirstSearch(src, Integer.MAX_VALUE, res, deepRoot);
        }

        return flow;
    }

    public static void main(String[] args) throws FileNotFoundException {
        FileRead fr = new FileRead();
        graph = fr.graphReader("ladder_9.txt");
        int[] deepRoot = new int[graph.getNumberOfNodes()];
        int[] parent = new int[graph.getNumberOfNodes()];
        System.out.println(Dinic(graph, 0, 1534, parent, deepRoot));
    }


}

