package com.gibran.MaximumFlow;

import java.util.ArrayList;
import java.util.List;
/**
 * Student Name: Gibran Kasif
 * IIT ID: 2019176
 * UoW ID: w1761211
 */
public class Graph {
    private int numberOfEdges; //Defines the number of nodes for a single graph
    private int numberOfNodes; //Defines the number of edges for a single graph
    private int[][] adjacencyMatrix; //Adjacency Matrix built using an 2D int array

    /**
     * Graph's Default constructor
     * @param numberOfNodes --> Takes downs the number of nodes to initialise the
     *                      Adjacency Matrix capacity.
     */
    public Graph(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        adjacencyMatrix = new int[numberOfNodes][numberOfNodes];
    }

    /**
     * The following method returns the number of nodes of a graph
     * @return --> numberOfNodes
     */
    public int getNumberOfNodes() {
        return numberOfNodes;
    }
    /**
     * The following method returns the number of edges of a graph
     * @return --> numberOfEdges
     */
    public int getNumberOfEdges() {
        return numberOfEdges;
    }

    /**
     * The following method returns the Adjacency Matrix
     * @return --> adjacencyMatrix
     */
    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    /**
     * The following method returns an edge value to a specific connection between
     * nodes i and j.
     * @param i --> Node i connecting towards Node j
     * @param j --> Node j connected from Node i
     * @return --> 0 or adjacencyMatrix[i][j]
     */
    public int getEdge(int i, int j) {
        if (hasEdge(i, j)) {
            return adjacencyMatrix[i][j];
        }
        return 0;
    }

    /**
     * The following method places an edge to a specific connection between
     * node i and j
     * @param i --> Node i connecting towards Node j
     * @param j --> Node j connected from Node i
     * @param edgeWeight --> The weight of the new edge
     */
    public void putEdge(int i, int j, int edgeWeight) {
        numberOfEdges++;
        adjacencyMatrix[i][j] = edgeWeight;
    }

    /**
     * The following method removes a particular edge
     * @param i --> Node i connecting towards Node j
     * @param j --> Node j connected from Node i
     */
    public void removeEdge(int i, int j) {
        numberOfEdges--;
        adjacencyMatrix[i][j] = 0;
    }

    /**
     * Used to identify if an edge exists between two nodes
     * @param i --> Node i connecting towards Node j
     * @param j --> Node j connected from Node i
     * @return --> true or false
     */
    public boolean hasEdge(int i, int j) {
        return adjacencyMatrix[i][j] != 0;
    }

    /**
     * Adds up all inward and outward connections for a specific node
     * @param node --> The node referred to identify all connections both inwards and outwards
     * @return --> int value retuning either 0 or value > 0
     */
    public int degree(int node) {
        return inDegree(node) + outDegree(node);
    }

    /**
     * Adds up all inward connections for a specific node
     * @param node --> The node referred to identify connections coming inwards the node
     * @return --> int value retuning either 0 or value > 0
     */
    public int inDegree(int node) {
        int inDegree = 0;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[i][node] != 0) {
                inDegree++;
            }
        }
        return inDegree;

    }

    /**
     * Adds up all outward connections for a specific node
     * @param node --> The node referred to identify connections diverting outwards the node
     * @return --> int value retuning either 0 or value > 0
     */
    public int outDegree(int node) {
        int outDegree = 0;
        for (int j = 0; j < adjacencyMatrix.length; j++) {
            if (adjacencyMatrix[node][j] != 0) {
                outDegree++;
            }
        }
        return outDegree;
    }

    /**
     * Method used to find and return all neighbouring nodes of a particular node
     * @param node --> The specified node used to finds neighboring nodes
     * @return --> List of Integers which contain the node values else if no neighbours are present returns null.
     */
    public List<Integer> adjacentNodes(int node) {
        List<Integer> neighbours = new ArrayList<>();
        for (int i = 0; i < numberOfNodes; i++) {
            if (hasEdge(node, i)) {
                neighbours.add(i);
            }
        }
        if (neighbours.size() == 0)
            return null;
        return neighbours;
    }

    /**
     * Following method prints the 2D int array, in the format of an
     * adjacency matrix.
     */
    public void printGraph() {
        System.out.println("Adjacency Matrix:");
        for (int row = 0; row < adjacencyMatrix.length; row++) {
            for (int col = 0; col < adjacencyMatrix.length; col++) {
                System.out.printf("%4d", adjacencyMatrix[row][col]);
            }
            System.out.println();
        }
    }

    /**
     *
     */
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
