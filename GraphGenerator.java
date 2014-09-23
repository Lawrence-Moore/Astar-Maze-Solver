import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * This class implements the disjoint set algorithm to generate a maze
 * 
 * @author Lawrence Moore
 *
 */

public class GraphGenerator {
	
	private Map<Vertex, VertexEntry> vertexTable;
	VertexEntry[] vertArr;
	Set<Vertex> vertexSet;
	AdjacencyList adjList;
	Random rand;

	private class VertexEntry {
		Vertex vertex;
		int upperSet;
		int posInArr;
		
		public VertexEntry(Vertex vert, int i) {
			vertex = vert;
			upperSet = i;
			posInArr = i;
		}
	}
	
	public GraphGenerator(int size) {
		vertArr = new VertexEntry[size];
		vertexTable = new Hashtable<Vertex, VertexEntry>();
		vertexSet = new HashSet<>();
		adjList = new AdjacencyList();
		rand = new Random();
	}
	
	public void generateVertexes() {
		for (int i = 0; i < vertArr.length; i++) {
			Vertex currentVert = new Vertex("Vertex " + i, rand.nextInt(500), rand.nextInt(500));
			VertexEntry currentVertexEntry = new VertexEntry(currentVert, i);
			
			vertexTable.put(currentVert, currentVertexEntry);
			vertArr[i] = currentVertexEntry;
		}
	}

	public void generateConnections() {
		for (int i = 0; i < 2 * vertArr.length - vertArr.length / 2; i ++) {
			int position1 = i;
			int position2 = rand.nextInt(vertArr.length);
			if (find(vertArr[position1].vertex) != find(vertArr[position2].vertex)) {
				union(vertArr[position1].vertex, vertArr[position2].vertex);
				adjList.addEdge(vertArr[position1].vertex, vertArr[position2].vertex);
				vertexSet.add(vertArr[position1].vertex);
				vertexSet.add(vertArr[position2].vertex);
			}
		}
	}
	
	public AdjacencyList getAdjacencyList() {
		return adjList;
	}
	
	public int find(Vertex u) {
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
		vertArr[endEntry.upperSet].upperSet = startEntry.upperSet;
	}
	
	/**
	 * find the set that a vertex belongs to
	 * @param the vertex to look at
	 */
	public void updateHead(VertexEntry vertex) {
		VertexEntry currentSet = vertex;
		while (currentSet.upperSet != -1 && currentSet.posInArr != currentSet.upperSet) {
			currentSet = vertArr[currentSet.upperSet];
		}
		vertex.upperSet = currentSet.posInArr;
	}
	
	public Vertex getRandomNode(){
		int index = rand.nextInt(vertexSet.size());
		while (!vertexSet.contains(vertArr[index].vertex)) {
			index = rand.nextInt(vertexSet.size());
		}
		return vertArr[index].vertex;
	}
	
	public static void main(String[] args) {
		GraphGenerator graphgen = new GraphGenerator(2);
		graphgen.generateVertexes();
		graphgen.generateConnections();
		AdjacencyList adjList = graphgen.getAdjacencyList();
		System.out.println(adjList);
		Astar.runAstar(graphgen.getRandomNode(), graphgen.getRandomNode(), adjList);
		//Astar.runAstar(start, end, adjList);
	}
}
