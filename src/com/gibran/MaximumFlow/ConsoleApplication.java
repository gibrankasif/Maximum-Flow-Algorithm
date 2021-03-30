package com.gibran.MaximumFlow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {
    static Graph graph;
    static List<Integer> nodesAvailable;
    static int sinkNode;
    static int sourceNode;

    public static void main(String[] args) throws FileNotFoundException { consoleMenu(); }

    public static void consoleMenu() throws FileNotFoundException {
        Scanner fileInput = new Scanner(System.in);
        String[] datasets;
        boolean fileChoice = false;
        while (!fileChoice) {
            File datasetDirectory = new File("Inputs/");
            // Populates the array with names of files and directories
            datasets = datasetDirectory.list();
            int fileIndex = 1;
            List<String> optionNumbers = new ArrayList<>();
            // For each pathname in the datasets array
            for (String fileName : datasets) {
                // Print the names of files and directories
                System.out.println(fileIndex + " --> " + fileName);
                optionNumbers.add(String.valueOf(fileIndex));
                fileIndex++;
            }
            System.out.println("Select your dataset:");
            String selectedNumber = fileInput.nextLine();
            if (optionNumbers.contains(selectedNumber)) {
                fileChoice = true;
                FileRead fileRead = new FileRead();
                String fileName = datasets[Integer.parseInt(selectedNumber) - 1];
                graph = fileRead.graphReader(fileName);
                System.out.println("--------------------------------------------------------------");
                System.out.println();
                graph.printGraph();
                System.out.println();
                System.out.println("Number of nodes: " + fileRead.numberOfNodes);
                System.out.println("Number of edges: " + fileRead.numberOfEdges);
                System.out.println("Successfully loaded " + fileName);
                System.out.println("--------------------------------------------------------------");

            } else {
                System.out.println("Invalid file choice, please try again!");
            }
        }
        nodesAvailable = new ArrayList<>(graph.getNumberOfNodes());
        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            nodesAvailable.add(i, i);
        }
        String arrayOfNodes = "Nodes at present: " + nodesAvailable;

//        System.out.println(arrayOfNodes);
//        Scanner enterNodeValues = new Scanner(System.in);
//
//        int[] chosenNodes = inputNodeValidation(enterNodeValues);
        sourceNode = 0;
        sinkNode = graph.getNumberOfNodes() - 1;

        consoleMenuLoop:
        while (true) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Source: {"  + sourceNode + "}" + "  Sink: {" + sinkNode + "}");
            System.out.println();
            System.out.println("Enter \"A\" to add a new edge");
            System.out.println("Enter \"C\" to change the weight of an edge");
            System.out.println("Enter \"R\" to remove an edge");
            System.out.println("Enter \"F\" to search an edge");
            System.out.println("Enter \"L\" to list down the adjacent neighbors of a node");
            System.out.println("Enter \"I\" to calculate the In-Degree of a node");
            System.out.println("Enter \"O\" to calculate the Out-Degree of a node");
            System.out.println("Enter \"D\" to calculate the Degree of a node");
            System.out.println("Enter \"M\" to calculate the maximum flow");
            System.out.println("Enter \"U\" to update the source and sink nodes");
            System.out.println("Enter \"P\" to display the network flow/adjacency matrix");
            System.out.println("Enter \"Q\" to end the program");
            System.out.println("--------------------------------------------------------------");

            Scanner consoleScanner = new Scanner(System.in);
            String userChoice = consoleScanner.nextLine().toUpperCase().trim();

            switch (userChoice) {
                case "A":
                    System.out.println(arrayOfNodes);
                    addNewEdge();
                    break;
                case "C":
                    System.out.println(arrayOfNodes);
                    changeEdgeWeight();
                    break;
                case "R":
                    System.out.println(arrayOfNodes);
                    removeEdge();
                    break;
                case "F":
                    System.out.println(arrayOfNodes);
                    findEdge();
                    break;
                case "L":
                    System.out.println(arrayOfNodes);
                    neighboursForNode();
                    break;
                case "I":
                    System.out.println(arrayOfNodes);
                    inDegree();
                    break;
                case "O":
                    System.out.println(arrayOfNodes);
                    outDegree();
                    break;
                case "D":
                    System.out.println(arrayOfNodes);
                    degree();
                    break;
                case "M":
                    System.out.println(arrayOfNodes);
                    calculateMaximumFlow();
                    break;
                case "P":
                    graph.printGraph();
                    break;
                case "U":
                    System.out.println(arrayOfNodes);
                    updateSourceSinkNodes();
                    break;
                case "Q":
                    break consoleMenuLoop;
                default:
                    System.out.println("Please insert a valid option!");
            }
        }
    }

    public static void addNewEdge() {
        Scanner newEdgeScanner = new Scanner(System.in);
        try {
            int[] selectedNodes = inputNodeValidation(newEdgeScanner);
            int startNode = selectedNodes[0];
            int endNode = selectedNodes[1];
            System.out.println("Please enter the edge weight: ");
            int edgeWeight = newEdgeScanner.nextInt();

            if (graph.hasEdge(startNode, endNode)) {
                System.out.println("The following connection already exists!");
                int existingEdgeValue = graph.getEdge(startNode, endNode);
                if (existingEdgeValue == edgeWeight) {
                    System.out.println("The edge weight is the same weight as previously entered!");
                } else {
                    System.out.println("Do you wish to modify the existing edge weight of " + existingEdgeValue + " to the new weight of " + edgeWeight + "?");
                    System.out.println("Enter Y to confirm or any key to retract:");
                    String inputDecision = newEdgeScanner.next().trim().toUpperCase();
                    if (inputDecision.equals("Y")) {
                        graph.putEdge(startNode, endNode, edgeWeight);
                        System.out.println("Edge Weight updated from " + existingEdgeValue + " to " + edgeWeight);
                    } else {
                        System.out.println("No edge was placed...");
                    }
                }
            } else {
                long startTime = System.nanoTime();
                graph.putEdge(startNode, endNode, edgeWeight);
                long endTime = System.nanoTime();

                System.out.println("Successfully added new edge");
            }
        } catch (Exception ex) {
            System.out.println("Please enter a valid number!");
        }
    }

    public static void changeEdgeWeight() {
        Scanner changeEdgeScanner = new Scanner(System.in);
        try {
            int[] selectedNodes = inputNodeValidation(changeEdgeScanner);
            int startNode = selectedNodes[0];
            int endNode = selectedNodes[1];
            System.out.println("Please enter the edge weight: ");
            int edgeWeight = changeEdgeScanner.nextInt();
            if (!graph.hasEdge(startNode, endNode)) {
                System.out.println("The following connection does not exist!");
            } else {
                int existingEdgeValue = graph.getEdge(startNode, endNode);
                if (existingEdgeValue == edgeWeight) {
                    System.out.println("The edge weight is the same weight as previously entered!");
                } else {
                    graph.putEdge(startNode, endNode, edgeWeight);
                    System.out.println("Edge Weight updated from " + existingEdgeValue + " to " + edgeWeight);
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Please enter a valid number!");
        }
    }

    public static void removeEdge() {
        Scanner changeEdgeScanner = new Scanner(System.in);
        int[] selectedNodes = inputNodeValidation(changeEdgeScanner);
        int startNode = selectedNodes[0];
        int endNode = selectedNodes[1];

        if (graph.hasEdge(startNode, endNode)) {
            graph.removeEdge(startNode, endNode);
            System.out.println("Successfully removed edge");
        } else {
            System.out.println("The following edge does not exist!");
        }
    }

    public static void neighboursForNode(){
        Scanner neighbourScanner = new Scanner(System.in);
        try {
            System.out.println("Enter a node to find its adjacent nodes: ");
            int selectedNode = neighbourScanner.nextInt();
            if (!nodesAvailable.contains(selectedNode)) {
                System.out.println("The following node does not exist!");
            } else {
                List<Integer> adjacentNodes = graph.adjacentNodes(selectedNode);
                if (adjacentNodes == null) {
                    System.out.println("There are no neighboring nodes adjacent to " + selectedNode);
                } else {
                    System.out.println("Node " + selectedNode + " is connected to: " + adjacentNodes);
                }
            }
        }catch (Exception ex){
            System.out.println("Please enter a valid number!");
        }
    }

    public static void inDegree(){
        Scanner inDegreeScanner = new Scanner(System.in);
        try {
            System.out.println("Enter the node:");
            int selectedNode = inDegreeScanner.nextInt();
            if (!nodesAvailable.contains(selectedNode)) {
                System.out.println("The following node does not exist!");
            } else {
                System.out.println("In-Degree(" + selectedNode + ") = "+ graph.inDegree(selectedNode));
            }
        }
        catch (Exception ex){
            System.out.println("Please enter a valid number!");
        }
    }

    public static void outDegree(){
        Scanner outDegreeScanner = new Scanner(System.in);
        try {
            System.out.println("Enter the node:");
            int selectedNode = outDegreeScanner.nextInt();
            if (!nodesAvailable.contains(selectedNode)) {
                System.out.println("The following node does not exist!");
            } else {
                System.out.println("Out-Degree(" + selectedNode + ") = "+ graph.outDegree(selectedNode));
            }
        }
        catch (Exception ex){
            System.out.println("Please enter a valid number!");
        }
    }

    public static void degree(){
        Scanner degreeScanner = new Scanner(System.in);
        try {
            System.out.println("Enter the node:");
            int selectedNode = degreeScanner.nextInt();
            if (!nodesAvailable.contains(selectedNode)) {
                System.out.println("The following node does not exist!");
            } else {
                System.out.println("Degree(" + selectedNode + ") = "+ graph.degree(selectedNode));
            }
        }
        catch (Exception ex){
            System.out.println("Please enter a valid number!");
        }
    }

    public static void calculateMaximumFlow() {
        MaximumFlow maximumFlow = new MaximumFlow();
        int startNode = sourceNode;
        int endNode = sinkNode;
        long startTime = System.nanoTime();
        int maxFlow = maximumFlow.findMaxFlow(graph, startNode, endNode);
        long endTime = System.nanoTime();
        System.out.println();
        System.out.println("The calculated maximum flow is " + maxFlow);
        long seconds = (endTime - startTime);
        System.out.println();
        System.out.println("Time taken: "+ seconds + "secs.");
    }

    public static void updateSourceSinkNodes() {
        Scanner changesNodeSC = new Scanner(System.in);
        int[] selectedNodes = inputNodeValidation(changesNodeSC);
        sourceNode = selectedNodes[0];
        sinkNode = selectedNodes[1];
    }

    private static void findEdge() {
        Scanner searchEdge = new Scanner(System.in);
        int[] selectedNodes = inputNodeValidation(searchEdge);
        int start = selectedNodes[0];
        int end = selectedNodes[1];

        if (graph.hasEdge(start, end)) {
            int queriedEdge = graph.getEdge(start, end);
            System.out.println("Edge between the source and sink node is " + queriedEdge);
        } else {
            System.out.println("There is no path between the nodes!");
        }
    }

    public static int[] inputNodeValidation(Scanner sc) {
        boolean correctInput = false;
        int[] nodesSelected = new int[2];
        while (!correctInput) {
            try {
                System.out.println("Please enter the starting node: ");
                int start = sc.nextInt();
                System.out.println("Please enter the ending node: ");
                int end = sc.nextInt();

                if (!nodesAvailable.contains(start)) {
                    System.out.println("The source node is out of bounds!");
                }
                else if (!nodesAvailable.contains(end)){
                    System.out.println("The sink node is out of bounds!");
                }
                else if(start == end){
                    System.out.println("Both source and sink nodes cannot be the same!");
                }
                else {
                    nodesSelected[0] = start;
                    nodesSelected[1] = end;
                    correctInput = true;
                }
            }catch (Exception e){
                System.out.println("Incorrect values have been entered!");
                sc.nextLine();
            }
        }
        return nodesSelected;
    }
}

