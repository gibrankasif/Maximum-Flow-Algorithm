package com.gibran.MaximumFlow;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Student Name: Gibran Kasif
 * IIT ID: 2019176
 * UoW ID: w1761211
 */
public class BreadthFirstSearch {
    /**
     *
     * @param residualGraph
     * @param source
     * @param destination
     * @param parent
     * @return
     */
    public static boolean bfs(Graph residualGraph, int source, int destination, int[] parent) {
        int nodes = residualGraph.getNumberOfNodes();
        boolean[] visited = new boolean[nodes];
        Deque<Integer> queue = new ArrayDeque<>(nodes); //The Queue structure placed using an ArrayDeque

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
        return visited[destination];
    }
}
