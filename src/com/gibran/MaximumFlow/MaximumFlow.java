package com.gibran.MaximumFlow;

public class MaximumFlow {
    BreadthFirstSearch breadthFirstSearch;

    public int findMaxFlow(Graph graph, int source, int sink) {
        int numberOfNodes = graph.getNumberOfNodes();
        Graph residualGraph = new Graph(numberOfNodes);
        String[] augmentingPaths = new String[residualGraph.getNumberOfNodes()];
        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            if (source == i) {
                augmentingPaths[i] = "S";
            }
            if (sink == i) {
                augmentingPaths[i] = "T";
            } else {
                augmentingPaths[i] = String.valueOf(i);
            }
        }
        String message = "";

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
                message = " --> " + augmentingPaths[i] + message;
            }
            System.out.println("S " + message );

            maximumFlow += path_flow;
        }
        return maximumFlow;
    }


}
