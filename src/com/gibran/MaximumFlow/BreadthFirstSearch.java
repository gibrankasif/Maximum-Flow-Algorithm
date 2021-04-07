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
     * Returns true if theres a path from source node to
     * sink node.
     * @param residualGraph --> The fluctuating graph currently been updated through each path found by BFS
     * @param source -->  Starting Node
     * @param destination --> Ending node
     * @param parent --> Used to store path
     * @return --> true or false
     */
    public static boolean bfs(Graph residualGraph, int source, int destination, int[] parent) {
        int nodes = residualGraph.getNumberOfNodes();
        //Used to store visited nodes
        boolean[] visited = new boolean[nodes];
        Deque<Integer> queue = new ArrayDeque<>(nodes); //The Queue structure placed using an ArrayDeque

        //Initially begin with the starting node
        queue.addLast(source);
        parent[source] = 1;
        visited[source] = true;

        //loops through the entire queue until its empty
        while(!queue.isEmpty()) {
            //Removes the first element then later moves the next node as the first element
            int u = queue.poll();
            //Loop based on the number of nodes
            for(int n = 0; n < nodes; n++) {
                //Adds to the queue only if the node is not visited and the weight of the edge is greater than 0
                if(visited[n] == false && residualGraph.getAdjacencyMatrix()[u][n] > 0) {
                    queue.add(n);
                    parent[n] = u;
                    visited[n] = true;
                }
            }
        }
        //Returns true if the destination was finally visited.
        return visited[destination];
    }
}
