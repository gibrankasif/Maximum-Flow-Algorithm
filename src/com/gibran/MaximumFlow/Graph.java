package com.gibran.MaximumFlow;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int numberOfEdges;
    private int numberOfNodes;
    private int[][] adjacencyMatrix;

    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        adjacencyMatrix = new int[numberOfNodes][numberOfNodes];

    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getNumberOfEdges(){
        return numberOfEdges;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int getEdge(int i, int j) {
        if(hasEdge(i, j)){
            return adjacencyMatrix[i][j];
        }
        return 0;
    }

    public void putEdge(int i, int j, int edgeWeight){
        numberOfEdges++;
        adjacencyMatrix[i][j] = edgeWeight;
    }

    public void removeEdge(int i, int j) {
        numberOfEdges--;
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

    public int inDegree(int node) {
        int inDegree = 0;
        for(int i = 0; i < adjacencyMatrix.length; i++) {
            if(adjacencyMatrix[i][node] != 0) {
                inDegree++;
            }
        }
        return inDegree;

    }

    public int outDegree(int node) {
        int outDegree = 0;
        for(int j = 0; j < adjacencyMatrix.length; j++) {
            if(adjacencyMatrix[node][j] != 0) {
                outDegree++;
            }
        }
        return outDegree;
    }

    public List<Integer> adjacentNodes(int node) {
        List<Integer> neighbours = new ArrayList<>();
        for(int i = 0; i < numberOfNodes; i++){
            if(hasEdge(node, i)){
                neighbours.add(i);
            }
        }
        if(neighbours.size() == 0)
            return null;
        return neighbours;
    }

    public void printGraph(){
        for(int i = 0; i < adjacencyMatrix.length; i++)
        {
            for(int j = 0; j < adjacencyMatrix.length; j++)
            {
                System.out.printf("%3d ", adjacencyMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public void printEdges() {
        for (int i = 0; i < numberOfNodes; i++) {
            System.out.print("Node " + i + " is connected to: ");
            for (int j = 0; j < numberOfNodes; j++) {
                if (hasEdge(i,j)) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }
}
