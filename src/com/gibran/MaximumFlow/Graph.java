package com.gibran.MaximumFlow;

public class Graph {
    private int numberOfNodes;
    private int[][] adjacencyMatrix;

    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        adjacencyMatrix = new int[numberOfNodes][numberOfNodes];
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public void putEdge(int i, int j, int edgeWeight){
        adjacencyMatrix[i][j] = edgeWeight;
    }

    public void removeEdge(int i, int j) {
        adjacencyMatrix[i][j] = 0;
    }

    public boolean hasEdge(int i, int j){
        return adjacencyMatrix[i][j] != 0;
    }

    public int degree(int node) {
        int numOfEdges = 0;
        for(int i = 0; i < numberOfNodes; i++) {
            if(adjacencyMatrix[node][i] != 0){
                numOfEdges++;
            }
        }
        if(adjacencyMatrix[node][node] != 0){
            numOfEdges++;
        }
        return numOfEdges;
    }


}
