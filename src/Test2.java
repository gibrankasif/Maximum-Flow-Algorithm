import com.gibran.MaximumFlow.FileRead;
import com.gibran.MaximumFlow.Graph;
import com.gibran.MaximumFlow.MaximumFlow;

import java.io.FileNotFoundException;

public class Test2 {
    public static void main(String[] args) throws FileNotFoundException {
        MaximumFlow maximumFlow = new MaximumFlow();
        FileRead fileRead = new FileRead();
        Graph graph = fileRead.graphReader("ladder_9.txt");
        System.out.println("Number of Node:  " + (graph.getNumberOfNodes()));
        System.out.println("Number of Edges:  " + (graph.getNumberOfEdges()));

        long startTime = System.nanoTime();
        System.out.println(maximumFlow.findMaxFlow(graph, 0, 1535));
        long endTime = System.nanoTime();
        System.out.println((endTime - startTime));
    }
}
