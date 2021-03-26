package com.gibran.MaximumFlow;

import java.util.ArrayDeque;
import java.util.Deque;

public class BreadthFirstSearch {
    public static boolean bfs(Graph residualGraph, int source, int destination, int[] parent) {
        boolean pathFound = true;
        Deque<Integer> queue = new ArrayDeque();
        int nodes = residualGraph.getNumberOfNodes();
        boolean[] visited = new boolean[nodes];

        queue.addLast(source);
        parent[source] = 1;
        visited[source] = true;

        while(!queue.isEmpty()) {
            int u = queue.poll();

            for(int n = 0; n < nodes; n++) {
                if(visited[n] == false && residualGraph.getAdjacencyMatrix()[u][n] > 0) {
                    queue.add(n);
                    parent[n] = u;
                    visited[n] = true;
                }
            }
        }
        pathFound = visited[destination];
        return pathFound;
    }
}
