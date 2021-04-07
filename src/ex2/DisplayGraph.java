package ex2;

import com.gibran.MaximumFlow.FileRead;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.OrderedSparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import javafx.scene.control.Alert;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DisplayGraph {
    /**
     * This method visualizes a graph
     *
     * @param graphMatrix                 - The graph matrix
     * @param arrayIndexStringEquivalents - String names for each edge
     * @param name                        - Name of the graph
     */
    public static void displayGraph(int[][] graphMatrix, String[] arrayIndexStringEquivalents, String name) {
        //Create a new graph object
        Graph<String, String> graph = new OrderedSparseMultigraph<String, String>();

//        Add the vertices to the graph
        for (String arrayIndexStringEquivalent : arrayIndexStringEquivalents) {
            graph.addVertex(arrayIndexStringEquivalent);
        }
//
//        for(int i = 0; i < graphMatrix.length; i++){
//            graph.addVertex(Integer.toString(i));
//        }

        //This list is used to get unique numbers.
        // The unique numbers are used if there are edges with the same weight
        List<Integer> listOfNumbers = new ArrayList<Integer>();
        for (int i = 0; i < (graphMatrix.length * graphMatrix.length); i++) {
            listOfNumbers.add(i++);
        }

        int z = 0;
        for (int i = 0; i < graphMatrix.length; i++) {
            for (int j = 0; j < graphMatrix.length; j++) {
                if (graphMatrix[i][j] != 0) {
                    try {
                        String vertexI = arrayIndexStringEquivalents[i];
                        String vertexJ = arrayIndexStringEquivalents[j];
                        graph.addEdge(String.valueOf(graphMatrix[i][j]), vertexI, vertexJ, EdgeType.DIRECTED);
                    } catch (Exception ex) {
                        int numOfSpacesBetweenLetters = listOfNumbers.remove(z);
                        String spaces = "";
                        for (int x = 0; x <= numOfSpacesBetweenLetters / 2; x++) {
                            spaces += " ";
                        }

                        String newWeight = spaces + graphMatrix[i][j] + spaces;
                        String vertexI = arrayIndexStringEquivalents[i];
                        String vertexJ = arrayIndexStringEquivalents[j];
                        try {
                            graph.addEdge(newWeight, vertexI, vertexJ, EdgeType.DIRECTED);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Alert a = new Alert(Alert.AlertType.WARNING);
                            a.setContentText("Error! Please regenerate the graph!");
                            a.show();
                            return;
                        }
                    }
                }
            }
        }


        Layout<String, String> layout = new KKLayout<>(graph);
        layout.setSize(new Dimension(650, 650));
        VisualizationViewer<String, String> vs = new VisualizationViewer<String, String>(layout);
        vs.setPreferredSize(new Dimension(650, 650));

        Transformer<String, Font> fontTransformer = new Transformer<String, Font>() {
            @Override
            public Font transform(String string) {
                Font font = new Font("Arial", Font.BOLD, 15);
                return font;
            }
        };

        vs.getRenderContext().setLabelOffset(20);
        vs.getRenderContext().setEdgeFontTransformer(fontTransformer);
        vs.getRenderContext().setVertexFontTransformer(fontTransformer);

        //Creates GraphMouse and adds to visualization
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vs.setGraphMouse(gm);


        //Colors Vertices
        Transformer<String, Paint> vertexPaintTransformer = new Transformer<String, Paint>() {
            public Paint transform(String i) {
                return Color.GREEN;
            }
        };


        //Renders Vertex colors/labels
        vs.getRenderContext().setVertexFillPaintTransformer(vertexPaintTransformer);
        vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vs.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);

        //Renders Edge labels
        vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        //Initialize JFrames
        JFrame frame = new JFrame(name);
        frame.getContentPane().add(vs);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) throws FileNotFoundException {
        FileRead fr = new FileRead();
        com.gibran.MaximumFlow.Graph graph = fr.graphReader("ladder_2.txt");
        String[] arrayIndexStringEquivalents = new String[graph.getNumberOfNodes()];
        int source = 0;
        int sink = graph.getNumberOfNodes() - 1;
        graph.printGraph();

//        String[] array = new String[graph.getNumberOfNodes() * graph.getAdjacencyMatrix()[0].length];
//
//        int k = 0;
//
//        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
//            for (int j = 0; j < graph.getNumberOfNodes(); j++) {
//                array[k] = Integer.toString( graph.getAdjacencyMatrix()[i][j]);
//                k++;
//            }
//        }

        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            if (source == i) {
                arrayIndexStringEquivalents[i] = "S";
            } else if (sink == i) {
                arrayIndexStringEquivalents[i] = "T";
            } else {
                arrayIndexStringEquivalents[i] = String.valueOf(i);
            }
        }

        displayGraph(graph.getAdjacencyMatrix(), arrayIndexStringEquivalents, "Network FLow");


    }
}



