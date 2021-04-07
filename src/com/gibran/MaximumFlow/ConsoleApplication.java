package com.gibran.MaximumFlow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Student Name: Gibran Kasif
 * IIT ID: 2019176
 * UoW ID: w1761211
 */
public class ConsoleApplication {
    static Graph graph; //The native graph used for all console operation
    static List<Integer> nodesAvailable; // List of node values available
    static int sinkNode;// Starting node
    static int sourceNode; //Destination Node

    /**
     * The following program is the Console Application dedicated to calculating the maximum flow
     * and running other graph operations, using dataset files.
     * @param args
     * @throws FileNotFoundException
     */

    public static void main(String[] args) throws FileNotFoundException { consoleMenu(); }

    public static void consoleMenu() throws FileNotFoundException {
        /* Initially presents all available datasets to the user
        to choose from.*/
        Scanner fileInput = new Scanner(System.in);
        String[] datasets;
        boolean fileChoice = false;
        //Validates until a actual file option was selected
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
                //Loads datasets into Graph
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
        //Stores all available nodes in nodeAvailable
        nodesAvailable = new ArrayList<>(graph.getNumberOfNodes());
        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            nodesAvailable.add(i, i);
        }
        String arrayOfNodes = "Nodes at present: " + nodesAvailable;

        sourceNode = 0;//By default sets sourceNode to 0
        sinkNode = graph.getNumberOfNodes() - 1; //sinkNode been the last node.

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

    /**
     * The following method is used to add a new edge
     */
    public static void addNewEdge() {
        Scanner newEdgeScanner = new Scanner(System.in);
        try {
            //Node input validation method called to take inputs for both nodes
            int[] selectedNodes = inputNodeValidation(newEdgeScanner);
            int startNode = selectedNodes[0];
            int endNode = selectedNodes[1];
            System.out.println("Please enter the edge weight: ");
            int edgeWeight = newEdgeScanner.nextInt();

            //Checks to see if the edge is already existing
            if (graph.hasEdge(startNode, endNode)) {
                //Alerts the user if the connection already exists
                System.out.println("The following connection already exists!");
                int existingEdgeValue = graph.getEdge(startNode, endNode);

                if (existingEdgeValue == edgeWeight) {
                    System.out.println("The edge weight is the same weight as previously entered!");
                } else {
                    //Requests the user to either update the already existing edge weight.
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
                graph.putEdge(startNode, endNode, edgeWeight);
                System.out.println("Successfully added new edge");
            }

        } catch (Exception ex) {
            System.out.println("Please enter a valid number!"); //Catches an invalid int input
        }
    }

    /**
     * Changes the edge weight of an existing edge
     *
     */
    public static void changeEdgeWeight() {
        Scanner changeEdgeScanner = new Scanner(System.in);
        try {
            //Node input validation method called to take inputs for both nodes
            int[] selectedNodes = inputNodeValidation(changeEdgeScanner);
            int startNode = selectedNodes[0];
            int endNode = selectedNodes[1];
            System.out.println("Please enter the edge weight: ");
            int edgeWeight = changeEdgeScanner.nextInt();
            //Check to see if the connection already exists
            if (!graph.hasEdge(startNode, endNode)) {
                //If the edge does not exist, alert user and return to console menu
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
            System.out.println("Please enter a valid number!"); //Catches an invalid int input
        }
    }

    /**
     * removeEdge() is used to remove an edge from an existing connection between two nodes
     */
    public static void removeEdge() {
        Scanner changeEdgeScanner = new Scanner(System.in);
        //Node input validation method called to take inputs for both nodes
        int[] selectedNodes = inputNodeValidation(changeEdgeScanner);
        int startNode = selectedNodes[0];
        int endNode = selectedNodes[1];

        //Checks to see if the graph has an edge, if so then remove the edge.
        if (graph.hasEdge(startNode, endNode)) {
            graph.removeEdge(startNode, endNode);
            System.out.println("Successfully removed edge");
        } else {
            System.out.println("The following edge does not exist!");
        }
    }

    /**
     * neighboursForNode() lists down all adjacent nodes to a specified node
     */
    public static void neighboursForNode(){
        Scanner neighbourScanner = new Scanner(System.in);
        try {
            System.out.println("Enter a node to find its adjacent nodes: ");
            int selectedNode = neighbourScanner.nextInt();
            if (!nodesAvailable.contains(selectedNode)) {
                System.out.println("The following node does not exist!");
            } else {
                //Stores all adjacent nodes, and prints on the latter code block
                List<Integer> adjacentNodes = graph.adjacentNodes(selectedNode);
                //If the list is null, it means there are no neighbouring nodes adjacent to the selectedNode
                if (adjacentNodes == null) {
                    System.out.println("There are no neighboring nodes adjacent to " + selectedNode);
                } else {
                    System.out.println("Node " + selectedNode + " is connected to: " + adjacentNodes);
                }
            }
        }catch (Exception ex){
            System.out.println("Please enter a valid number!"); //Catches an invalid int input
        }
    }

    /**
     * inDegree() used to calculate the number all incoming node connections to a specific node
     */
    public static void inDegree(){
        Scanner inDegreeScanner = new Scanner(System.in);
        try {
            System.out.println("Enter the node:");
            int selectedNode = inDegreeScanner.nextInt();
            //If the selected node does not exist then alert the user and return back to console menu
            if (!nodesAvailable.contains(selectedNode)) {
                System.out.println("The following node does not exist!");
            } else {
                //Prints the In-Degrees for a specified existing node.
                System.out.println("In-Degree(" + selectedNode + ") = "+ graph.inDegree(selectedNode));
            }
        }
        catch (Exception ex){
            System.out.println("Please enter a valid number!"); //Catches an invalid int input
        }
    }
    /**
     * outDegree() used to calculate the number of all outgoing node connections from a specific node
     */
    public static void outDegree(){
        Scanner outDegreeScanner = new Scanner(System.in);
        try {
            System.out.println("Enter the node:");
            //If the selected node does not exist then alert the user and return back to console menu
            int selectedNode = outDegreeScanner.nextInt();
            if (!nodesAvailable.contains(selectedNode)) {
                System.out.println("The following node does not exist!");
            } else {
                //Prints the Out-Degrees for a specified existing node.
                System.out.println("Out-Degree(" + selectedNode + ") = "+ graph.outDegree(selectedNode));
            }
        }
        catch (Exception ex){
            System.out.println("Please enter a valid number!"); //Catches an invalid int input
        }
    }

    /**
     * degree() used to calculate the number of all node connections to a specific node irrelevant of it's direction.
     */
    public static void degree(){
        Scanner degreeScanner = new Scanner(System.in);
        try {
            System.out.println("Enter the node:");
            //If the selected node does not exist then alert the user and return back to console menu
            int selectedNode = degreeScanner.nextInt();
            if (!nodesAvailable.contains(selectedNode)) {
                System.out.println("The following node does not exist!");
            } else {
                //Prints the Degrees for a specified existing node.
                System.out.println("Degree(" + selectedNode + ") = "+ graph.degree(selectedNode));
            }
        }
        catch (Exception ex){
            System.out.println("Please enter a valid number!"); //Catches an invalid int input
        }
    }

    /**
     *  calculateMaximumFlow() used to calculate the maximum flow of the given set path
     *  from node source to node sink.
     */
    public static void calculateMaximumFlow() {
        //Initialise maximum flow object to call out it's operations.
        MaximumFlow maximumFlow = new MaximumFlow();
        int startNode = sourceNode;
        int endNode = sinkNode;
        //Setting timer to calculate for algorithm analysis
        long startTime = System.nanoTime();
        int maxFlow = maximumFlow.findMaxFlow(graph, startNode, endNode);
        long endTime = System.nanoTime(); //Calculating time finished
        System.out.println();
        System.out.println("The calculated maximum flow is " + maxFlow);
        long nanoSeconds = (endTime - startTime); // Time consumed in nanoseconds
        System.out.println();
        System.out.println("Time taken: "+ nanoSeconds + "(ns)."); //Prints the time taken in nanoseconds
    }

    /**
     * updateSourceSinkNodes() used to update the default set source node and sink node
     * to the choice of the user.
     */
    public static void updateSourceSinkNodes() {
        Scanner changesNodeSC = new Scanner(System.in);
        //Node input validation method called to take inputs for both nodes
        int[] selectedNodes = inputNodeValidation(changesNodeSC);
        //Updating global source and sink node for use in calculating the max flow
        sourceNode = selectedNodes[0];
        sinkNode = selectedNodes[1];
    }

    /**
     * findEdge() used to find a possible connection between two given nodes.
     */
    private static void findEdge() {
        Scanner searchEdge = new Scanner(System.in);
        //Node input validation method called to take inputs for both nodes
        int[] selectedNodes = inputNodeValidation(searchEdge);
        int start = selectedNodes[0];
        int end = selectedNodes[1];
        //Identifies is there is an edge connection between both edges.
        if (graph.hasEdge(start, end)) {
            int queriedEdge = graph.getEdge(start, end);
            System.out.println("Edge between the source and sink node is " + queriedEdge);
        } else {
            System.out.println("There is no path between the nodes!");
        }
    }

    /**
     * inputNodeValidation(Scanner sc) is used in most console operations which acts as input operation to take down two
     * node inputs. The entire method is heavily validated and is called in most operations, to reduce code duplication
     * especially in input validation and error handling.
     * @param sc --> Scanner object been passed from each of the console's methods
     * @return --> returns an int[2], each element being a node selected after going through validation.
     */
    public static int[] inputNodeValidation(Scanner sc) {
        boolean correctInput = false;
        int[] nodesSelected = new int[2];
        //Loops until a valid input node has been placed
        while (!correctInput) {
            try {
                System.out.println("Please enter the starting node: ");
                int start = sc.nextInt();
                System.out.println("Please enter the ending node: ");
                int end = sc.nextInt();
                //Checks if the source node value does not belong to the existing graph.
                if (!nodesAvailable.contains(start)) {
                    System.out.println("The source node is out of bounds!");
                }
                //Checks if the sink node value does not belong to the existing graph.
                else if (!nodesAvailable.contains(end)){
                    System.out.println("The sink node is out of bounds!");
                }
                //Checks to see if the source node is equal to the sink node.
                else if(start == end){
                    System.out.println("Both source and sink nodes cannot be the same!");
                }
                /*Finally if passed all conditions, then successfully considers both node inputs which
                are sent for use in the console method.*/
                else {
                    nodesSelected[0] = start;
                    nodesSelected[1] = end;
                    correctInput = true;
                }
            }catch (Exception e){
                System.out.println("Incorrect values have been entered!");
                sc.nextLine();
            }
        }//Returns array of the two selected nodes.
        return nodesSelected;
    }
}

