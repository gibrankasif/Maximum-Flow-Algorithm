package com.gibran.MaximumFlow;

/**
 * Student Name: Gibran Kasif
 * IIT ID: 2019176
 * UoW ID: w1761211
 */
public class MaximumFlow {
    BreadthFirstSearch breadthFirstSearch;

    /**
     * The following implementation of the Maximum Flow calculation is based on
     * Edmond's Karp Algorithm, which implements a Breadth First Search in the
     * default Ford Fulkerson Algorithm, BFS is used to find the shortest augmenting
     * path between the Source and Sink nodes. This results in a shorter run time.
     *
     * @param graph -->  Passes the initial Graph loaded from the selected Dataset
     * @param source -->  Starting Node
     * @param sink --> Ending node
     * @return --> Maximum Flow
     */
    public int findMaxFlow(Graph graph, int source, int sink) {
        if (source == sink)
        { //Used for validation, only applied for test classes not the Console.
            return 0;
        }

        int numberOfNodes = graph.getNumberOfNodes();
        //Creating a residual graph, a copy of the initial graph
        Graph residualGraph = new Graph(numberOfNodes);


        for(int i = 0; i < numberOfNodes; i++) {
            for(int j = 0; j < numberOfNodes; j++){
                residualGraph.getAdjacencyMatrix()[i][j] = graph.getAdjacencyMatrix()[i][j];
            }
        }
        //Used to store the path from source to node, for use in BFS.
        int[] parent = new int[numberOfNodes];
        //Initialised to store the maximum flow value
        int maximumFlow = 0;
        //Used to count the number of all short paths
        int numAugmentedPaths = 0;

        //Loops for each existing path traced from the source to the sink node
        while(breadthFirstSearch.bfs(residualGraph, source, sink, parent)) {

            /* Bottle neck of the current path, its set to the largest
            possible number */

            int bottleNeck = Integer.MAX_VALUE;
            /* Looping backwards through parent[], which finds the residual capacity
            and stores in the bottleNeck of a path. */
            for(int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                //Finding the minimum flow which can be sent from the existing bottleNeck or the capacity of the new edge
                bottleNeck = Math.min(bottleNeck, residualGraph.getAdjacencyMatrix()[j][i]);
            }

            //Used to update the residual graph
            for (int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                residualGraph.getAdjacencyMatrix()[j][i] -= bottleNeck;
                residualGraph.getAdjacencyMatrix()[i][j] += bottleNeck;
            }
            /*Adds up the maximum flow of each path to the maximumFlow
             which would in the end return the total max flow.
             */
            maximumFlow += bottleNeck;
            //Counts the number of paths
            numAugmentedPaths++;
            System.out.println();
            System.out.println("Number of augmented paths: " + numAugmentedPaths);
            System.out.println("The current flow value: " + bottleNeck);
            System.out.println("Current max-flow value: " + maximumFlow);
        }
//        residualGraph.printGraph();

        return maximumFlow;
    }
}
