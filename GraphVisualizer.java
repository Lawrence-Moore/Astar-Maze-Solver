import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;


public class GraphVisualizer {

	public static void main(String[] args) {
		JFrame frame = new JFrame("A Modest Graph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//internal stuff
		GraphGenerator graphgen = new GraphGenerator(6);
		graphgen.generateVertexes();
		graphgen.generateConnections();
		AdjacencyList adjList = graphgen.getAdjacencyList();
		System.out.println(adjList);
		// end of internal stuff
			
		Vertex node1 = graphgen.getRandomNode();
		Vertex node2 = graphgen.getRandomNode();
		
		while (node1.equals(node2)) {
			node2 = graphgen.getRandomNode();
		}
		
		System.out.println("Starting Node: " + node1);
		System.out.println("Ending Node: " + node2);
		
		List<TableEntry> list= Astar.runAstar(node1, node2, adjList);
		if (list != null) {
			System.out.println(Astar.printPath(list));
			GraphPanel newPanel = new GraphPanel(adjList, list, Astar.getEdgesOfPath(list), node1, node2);
			
			frame.getContentPane().add(newPanel);
			
			frame.pack();
			frame.setVisible(true);

		} else {
			System.out.println("Graph was not fully connected");
		}
	}
}
