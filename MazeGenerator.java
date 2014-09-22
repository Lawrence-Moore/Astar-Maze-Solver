import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class MazeGenerator {

	private Map<Vertex, VertexEntry> vertexTable;
	VertexEntry[][] vertArr;
	Set<Vertex> vertexSet;
	AdjacencyList adjList;
	Random rand;
	private final int START = 50, SIZE = 40;
	
	private class Position {
		int x;
		int y;
		
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public boolean equals(Position other) {
			return x == other.x && y == other.y;
		}
	}

	private class VertexEntry {
		Vertex vertex;
		Position upperSet;
		Position posInArr;
		
		public VertexEntry(Vertex vert, int i, int j) {
			vertex = vert;
			upperSet = new Position(-1, -1);
			posInArr = new Position(i,j);
		}
	}
	
	public MazeGenerator(int size) {
		vertArr = new VertexEntry[size][size];
		vertexTable = new Hashtable<Vertex, VertexEntry>();
		vertexSet = new HashSet<>();
		adjList = new AdjacencyList();
		rand = new Random();
	}
	
	public void generateVertexes() {
		for (int i = 0; i < vertArr.length; i++) {
			for (int j = 0; j < vertArr.length; j++) {
				Vertex currentVert = new Vertex("Vertex " + i + "," + j, START + SIZE * i, START + SIZE * j);
				VertexEntry currentVertexEntry = new VertexEntry(currentVert, i, j);
				
				vertexTable.put(currentVert, currentVertexEntry);
				vertArr[i][j] = currentVertexEntry;
			}
		}
	}

	public void generateConnections() {
		while (find(vertArr[0][0].vertex) != find(vertArr[vertArr.length -1][vertArr.length - 1].vertex)) {
			int x1 = rand.nextInt(vertArr.length);
			int y1 = rand.nextInt(vertArr.length);
			VertexEntry start = vertArr[x1][y1];
			
			int x2, y2;
			do {
				x2 = rand.nextInt(2) < 1? x1 + rand.nextInt(2):x1 - rand.nextInt(2);
				
				if (x2 != x1) {
					y2 = y1;
				} else {
					y2 = rand.nextInt(2) < 1? y1 + rand.nextInt(2):y1 - rand.nextInt(2);
				}
				
			} while (!checkBounds(x2) || !checkBounds(y2));
				
			VertexEntry end = vertArr[x2][y2];
			
			if (find(start.vertex) != find(end.vertex)) {
				union(start.vertex, end.vertex);
				adjList.addEdge(start.vertex, end.vertex);
				vertexSet.add(start.vertex);
				vertexSet.add(end.vertex);
			}
		}
	}
	
	private boolean checkBounds(int value) {
		return (value >= 0 && value < vertArr.length);
	}
	
	public AdjacencyList getAdjacencyList() {
		return adjList;
	}
	
	public Position find(Vertex u) {
		VertexEntry startEntry = vertexTable.get(u);
		//finds and updates the set it belongs to
		updateHead(startEntry);
		return startEntry.upperSet;
	}
	
	public void union(Vertex start, Vertex end) {
		VertexEntry startEntry = vertexTable.get(start);
		VertexEntry endEntry = vertexTable.get(end);

		//if they're the same;
		if (startEntry.posInArr == endEntry.posInArr) {
			return;
		}
		
		//makes sure the set of vertex V is up to date
		updateHead(endEntry);
		
		//sets the upperset of V to that of U
		vertArr[endEntry.upperSet.x][endEntry.upperSet.y].upperSet = startEntry.upperSet;
	}
	
	/**
	 * find the set that a vertex belongs to
	 * @param the vertex to look at
	 */
	public void updateHead(VertexEntry vertex) {
		VertexEntry currentSet = vertex;
		Position end = new Position(-1, -1);
		while (!currentSet.upperSet.equals(end) && !currentSet.posInArr.equals(currentSet.upperSet)) {
			currentSet = vertArr[currentSet.upperSet.x][currentSet.upperSet.y];
		}
		vertex.upperSet = currentSet.posInArr;
	}
	
	public Vertex getFirstNode() {
		if (vertArr != null){
			return vertArr[0][0].vertex;
		}
		return null;
	}
	
	public Vertex getLastNode() {
		if (vertArr != null){
			return vertArr[vertArr.length - 1][vertArr.length - 1].vertex;
		}
		return null;
	}
	
	public static void main(String[] args) {
		MazeGenerator graphgen = new MazeGenerator(3);
		graphgen.generateVertexes();
		graphgen.generateConnections();
		AdjacencyList adjList = graphgen.getAdjacencyList();
		System.out.println(adjList);
		System.out.println();
		
		Vertex node1 = graphgen.getFirstNode();
		Vertex node2 = graphgen.getLastNode();
		
		System.out.println("Starting Node: " + node1);
		System.out.println("Ending Node: " + node2);
		System.out.println();
		
		List<TableEntry> result = Astar.runAstar(node1, node2, adjList);
		System.out.println(Astar.printPath(result));
		//Astar.runAstar(start, end, adjList);
	}
}
