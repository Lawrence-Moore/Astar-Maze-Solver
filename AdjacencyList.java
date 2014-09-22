import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents an adjacency list
 * 
 * @author Lawrence Moore
 *
 */

public class AdjacencyList {

	/**
	 * This is a map (Hash Table) of vertices to their adjacent vertices and the weight associated with that edge
	 */
	private Map<Vertex, Map<Vertex, Integer>> adjList;

    
  	/**
  	*
  	*/
	public AdjacencyList(){
		adjList = new Hashtable<Vertex, Map<Vertex, Integer>>();
	}
	
	public void addEdge(Vertex startVertex, Vertex endVertex) {
		//manhattan distance
		int distance = Math.abs(startVertex.getX() - endVertex.getX()) + Math.abs(startVertex.getY() - endVertex.getY());
		
		
		if (adjList.containsKey(startVertex)) {
			Map<Vertex,Integer> list = adjList.get(startVertex);
			list.put(endVertex, distance);
		} else {
			Map<Vertex,Integer> list = new Hashtable<Vertex,Integer>();
			list.put(endVertex, distance);
			adjList.put(startVertex, list);
		}
		// if it's undirected, you make the edges in the opposite direction
		if (adjList.containsKey(endVertex)) {
			Map<Vertex,Integer> list = adjList.get(endVertex);
			list.put(startVertex, distance);
			//adjList.put(startVertex, list);
		} else {
			Map<Vertex,Integer> list = new Hashtable<Vertex,Integer>();
			list.put(startVertex, distance);
			adjList.put(endVertex, list);
		}
	}

	/**
     * Prints out the adjacency according to the guide lines 
     */
	public String toString() {
		String message = "";
		Set<Entry<Vertex, Map<Vertex, Integer>>> set = adjList.entrySet();
		
		for (Map.Entry<Vertex, Map<Vertex, Integer>> entry : set) {
			Vertex vert = entry.getKey();
			message = (message + vert + ": ");
			
			Map<Vertex, Integer> adjVert = entry.getValue();
			Set<Entry<Vertex, Integer>> adjSet = adjVert.entrySet();
			for (Map.Entry<Vertex, Integer> adjEntry : adjSet) {
				message = (message + ("(" + adjEntry.getKey() + ", " + adjEntry.getValue() + ") "));
			}
			message = (message + "\n");
		}
		return message;
	}
	
    /**
     *  Find a vertex by its name
     *  @param  the name of the vertex to find
     *  @return the vertex with that name (or null if none)
     */
	public Vertex findVertexByName(String name) {
        Vertex newVertex = new Vertex(name);
        if (adjList.containsKey(newVertex)) {
        	return newVertex;
        }
        return null;
    }
    
    /**
     * @return the number of vertexes in the graph
     */
    public int vertexCount() {
        return adjList.size();
    }
    
	public Map<Vertex, Map<Vertex, Integer>> getAdjList() {
		return adjList;
	}
}
