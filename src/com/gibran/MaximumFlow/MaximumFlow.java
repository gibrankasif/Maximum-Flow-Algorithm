package com.gibran.MaximumFlow;

public class MaximumFlow {
    BreadthFirstSearch breadthFirstSearch;

    public int findMaxFlow(Graph graph, int source, int sink) {
        int numberOfNodes = graph.getNumberOfNodes();
        Graph residualGraph = new Graph(numberOfNodes);

        for(int i = 0; i < numberOfNodes; i++){
            for(int j = 0; j < numberOfNodes; j++){
                residualGraph.getAdjacencyMatrix()[i][j] = graph.getAdjacencyMatrix()[i][j];
            }
        }

        int parent[] = new int[numberOfNodes];
        int maximumFlow = 0;

        while(breadthFirstSearch.bfs(residualGraph, source, sink, parent)) {
            int path_flow = Integer.MAX_VALUE;
            for(int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                path_flow = Math.min(path_flow, residualGraph.getAdjacencyMatrix()[j][i]);

            }

            for (int i = sink; i != source; i = parent[i]) {
                int j = parent[i];
                residualGraph.getAdjacencyMatrix()[j][i] -= path_flow;
                residualGraph.getAdjacencyMatrix()[i][j] += path_flow;
            }

            maximumFlow += path_flow;
        }
        residualGraph.printGraph();
        System.out.println();
        return maximumFlow;
    }


}
