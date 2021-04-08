package com.gibran.MaximumFlow;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

/**
 * Student Name: Gibran Kasif
 * IIT ID: 2019176
 * UoW ID: w1761211
 */
 class EdgeWeight extends DefaultWeightedEdge {
    private int capacity;
    public EdgeWeight(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return Double.toString(capacity);
    }
}

// Creates and displays a graph
public class GraphGUI extends JApplet {

    private static final Dimension DEFAULT_SIZE = new Dimension(1000, 800);


    private JGraphXAdapter<String, EdgeWeight> jgxAdapter;

    public static int[][] GUIGraph;
    static int GUITotalVertices;

    @Override
    public void init() {
        // create a JGraphT graph
        ListenableGraph<String, EdgeWeight> g =
                new DefaultListenableGraph<>(new DirectedWeightedMultigraph<>(EdgeWeight.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);
        jgxAdapter.isAutoSizeCells();
        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.getViewport().setBackground(new Color(2,2,102));
        component.setToolTips(true);


        component.getViewport().setOpaque(true);
        component.getViewport().setBackground(Color.YELLOW);
        component.setTripleBuffered(true);
        component.setAutoScroll(true);
        component.setFocusable(true);

        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);


        // creating all the nodes of the graph
        for (int i = 0; i < GUITotalVertices; i++) {
            g.addVertex(Integer.toString(i));
        }

        // creating all the edges that exists with Capacities
        for (int u = 0; u < GUITotalVertices; u++) {
            int[] startingNode = GUIGraph[u];
            for (int v = 0; v < GUITotalVertices; v++) {
                if (startingNode[v] > 0) {     // if a capacity exists
                    g.addEdge(Integer.toString(u), Integer.toString(v), new EdgeWeight(GUIGraph[u][v]));
                }
            }
        }



        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 350;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - 1 * radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - 1 * radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }
        public static void main(String[] args) {
        FileRead fr = new FileRead();
        Graph graph = null;
        try {
            graph = fr.graphReader("bridge_1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
     }
     graph.printGraph();



     int maxFlow =findMaxFlow(graph, 0, graph.getNumberOfNodes()-1);
     System.out.println("The total maximum flow is " + maxFlow);

     GUIGraph = graph.getAdjacencyMatrix();
     GUITotalVertices = graph.getNumberOfNodes();
     graph.printGraph();

    GraphGUI applet = new GraphGUI();
    applet.init();

    JFrame frame = new JFrame();
    frame.getContentPane().add(applet);
    frame.setTitle("Final Residual Graph");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
    }
    public static int findMaxFlow(Graph graph, int source, int sink) {
        BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();

        if (source == sink)
        { //Used for validation, only applied for test classes not the Console.
            return 0;
        }

        int numberOfNodes = graph.getNumberOfNodes();
        //Creating a residual graph, a copy of the initial graph
        Graph residualGraph = graph;


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

