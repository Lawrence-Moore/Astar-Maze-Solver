import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map.Entry;


/**
 * This class implements the A* algorithm with the Manhattan heuristic
 * 
 * @author Lawrence Moore
 *
 */
public class MazeAstar {
	
	public static List<MazeTableEntry> runAstar(Vertex start, Vertex end, AdjacencyList adjList) {
		//map of the adjacency list
		Map<Vertex, Map<Vertex, Integer>> map = adjList.getAdjList();
		
		//Using the vertices map to update priorities in the priority queue
		Map<Vertex, MazeTableEntry> verticesMap = new Hashtable<Vertex, MazeTableEntry>();
		PriorityQueue<MazeTableEntry> queue = new PriorityQueue<>();
		
		//This will be my final list of entries
		List<MazeTableEntry> finalList = new ArrayList<MazeTableEntry>();
		
		//adding the first element to the queue and final list
		MazeTableEntry firstEntry  = new MazeTableEntry(start);
		queue.add(firstEntry);
		verticesMap.put(start, firstEntry);
		
		boolean isFound = false;
		while (!queue.isEmpty()) {
			//removing the entry from the priority heap and saying it's known
			MazeTableEntry entry = queue.remove();
			entry.setKnown(true);
			finalList.add(entry);
			
			// If the item dequed from the priority que is the end vertex, end the loop
			if (entry.getVertex().equals(end)) {
				isFound = true;
				break;
			}
			
			Map<Vertex, Integer> adjMap = map.get(entry.getVertex());
			//iterates through all of the adjacent paths
			for (Entry<Vertex, Integer> i : adjMap.entrySet()) {
				MazeTableEntry incomingEntry = new MazeTableEntry(i.getKey());
				
				//setting the weight and parent of table entry
				incomingEntry.setParent(entry.getVertex());
				
				int manhattanDistance = Math.abs(i.getKey().getX() - end.getX()) + Math.abs(i.getKey().getY() - end.getY());
				incomingEntry.setDistance(manhattanDistance);
				
				//if the vertex has never been seen, add to queue and vertex map
				if (!verticesMap.containsKey(i.getKey())) {
					verticesMap.put(i.getKey(), incomingEntry);
					queue.add(incomingEntry);
				
					//if the vertex has been seen but now has a smaller weight, update
				} else if (verticesMap.get(i.getKey()).compareTo(incomingEntry) > 0 ) {
					verticesMap.get(i.getKey()).setDistance(incomingEntry.getDistance());
				}
			}
		}
		if (isFound){
			return finalList;
		} else {
			return null;
		}
	}
	
	//gives the edges of the shortest path
	public static List<Edge> getEdgesOfPath(List<MazeTableEntry> path) {
		if (path.isEmpty()) return null;
		
		ArrayList<Edge> finalList = new ArrayList<Edge>();
		MazeTableEntry item = path.get(path.size() - 1);
		while (item.getParent() != null) {
			Edge currentEdge = new Edge(item.getParent(), item.getVertex(), item.getDistance());
			finalList.add(currentEdge);
			
			Iterator<MazeTableEntry> it = path.iterator();
			MazeTableEntry original = item;
			while (item.getVertex() != original.getParent() && it.hasNext()) {
				item = it.next();
			}
		}
		return finalList;
	}
	
	//gives the String of the path to print out
	public static String printPath(List<MazeTableEntry> list) {
		if (list == null) {
			System.out.println("List was null");
			return null;
		}
		
		if (list.isEmpty()){
			System.out.println("List was empty");
			return null;
		}
		
		int count = 0;
		String line = "";
		MazeTableEntry item = list.get(list.size() - 1);
		while (item.getParent() != null) {
			count += item.getDistance();
			String edge = ("(" + item.getParent() + ", " + item.getVertex() + ", " +item.getDistance() + ") ");
			line = (edge + line);
			Iterator<MazeTableEntry> it = list.iterator();
			MazeTableEntry original = item;
			while (item.getVertex() != original.getParent() && it.hasNext()) {
				item = it.next();
			}
		}
		line = ("[" + count + "] " + line);
		return line;
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
		
		List<MazeTableEntry> result = MazeAstar.runAstar(node1, node2, adjList);
		System.out.println(MazeAstar.printPath(result));
	}
}
