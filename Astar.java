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
public class Astar {
	
	public static List<TableEntry> runAstar(Vertex start, Vertex end, AdjacencyList adjList) {
		//map of the adjacency list
		Map<Vertex, Map<Vertex, Integer>> map = adjList.getAdjList();
		
		//Using the vertices map to update priorities in the priority queue
		Map<Vertex, TableEntry> verticesMap = new Hashtable<Vertex, TableEntry>();
		PriorityQueue<TableEntry> queue = new PriorityQueue<>();
		
		//This will be my final list of entries
		List<TableEntry> finalList = new ArrayList<TableEntry>();
		
		//adding the first element to the queue and final list
		TableEntry firstEntry  = new TableEntry(start);
		queue.add(firstEntry);
		verticesMap.put(start, firstEntry);
		
		boolean isFound = false;
		while (!queue.isEmpty()) {
			//removing the entry from the priority heap and saying it's known
			TableEntry entry = queue.remove();
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
				TableEntry incomingEntry = new TableEntry(i.getKey());
				
				//setting the weight and parent of table entry
				incomingEntry.setWeight(entry.getWeight() + i.getValue());
				incomingEntry.setParent(entry.getVertex());
				
				int manhattanDistance = Math.abs(i.getKey().getX() - end.getX()) + Math.abs(i.getKey().getY() - end.getY());
				incomingEntry.setDistance(manhattanDistance);
				
				//if the vertex has never been seen, add to queue and vertex map
				if (!verticesMap.containsKey(i.getKey())) {
					verticesMap.put(i.getKey(), incomingEntry);
					queue.add(incomingEntry);
				
					//if the vertex has been seen but now has a smaller weight, update
				} else if (verticesMap.get(i.getKey()).compareTo(incomingEntry) > 0 ) {
					verticesMap.get(i.getKey()).setWeight(incomingEntry.getWeight());
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
	public static List<Edge> getEdgesOfPath(List<TableEntry> path) {
		if (path.isEmpty()) return null;
		
		ArrayList<Edge> finalList = new ArrayList<Edge>();
		TableEntry item = path.get(path.size() - 1);
		while (item.getParent() != null) {
			Edge currentEdge = new Edge(item.getParent(), item.getVertex(), item.getWeight());
			finalList.add(currentEdge);
			
			Iterator<TableEntry> it = path.iterator();
			TableEntry original = item;
			while (item.getVertex() != original.getParent() && it.hasNext()) {
				item = it.next();
			}
		}
		return finalList;
	}
	
	//gives the String of the path to print out
	public static String printPath(List<TableEntry> list) {
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
		TableEntry item = list.get(list.size() - 1);
		while (item.getParent() != null) {
			count += item.getWeight();
			String edge = ("(" + item.getParent() + ", " + item.getVertex() + ", " +item.getWeight() + ") ");
			line = (edge + line);
			Iterator<TableEntry> it = list.iterator();
			TableEntry original = item;
			while (item.getVertex() != original.getParent() && it.hasNext()) {
				item = it.next();
			}
		}
		line = ("[" + count + "] " + line);
		return line;
	}
}
