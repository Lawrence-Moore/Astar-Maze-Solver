import java.util.List;

import javax.swing.JFrame;


public class MazeVisualizer {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("A Modest Maze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//internal stuff
		int numSquares = 12;
		MazeGenerator graphgen = new MazeGenerator(numSquares);
		graphgen.generateVertexes();
		graphgen.generateConnections();
		AdjacencyList adjList = graphgen.getAdjacencyList();
		//System.out.println(adjList);
		// end of internal stuff
			
		Vertex node1 = graphgen.getFirstNode();
		Vertex node2 = graphgen.getLastNode();
		
		System.out.println("Starting Node: " + node1);
		System.out.println("Ending Node: " + node2);
		
		List<MazeTableEntry> list= MazeAstar.runAstar(node1, node2, adjList);
		if (list != null) {
			System.out.println(MazeAstar.printPath(list));
			MazePanel newPanel = new MazePanel(adjList, list, MazeAstar.getEdgesOfPath(list), numSquares);
			
			frame.getContentPane().add(newPanel);
			
			frame.pack();
			frame.setVisible(true);

		} else {
			System.out.println("Graph was not fully connected");
		}
	}
}
