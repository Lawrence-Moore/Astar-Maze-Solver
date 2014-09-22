import java.util.LinkedList;

/**
 * Represents a single entry in our table of vertexes for solving Dijkstra's
 * 
 * @author Lawrence Moore
 *
 */
public class TableEntry implements Comparable<TableEntry> {

	/**  the overall weight of the path we know so far */
	private int weight;
	
	/**Distance from destination node */
	private int distance;
	
	/** the vertex for this entry */
	private Vertex myVertex;

	/** the immediately preceeding vertex in the shortest path */
	private Vertex parent;
	
	/** whether myVertex's shortest path has been found */
	private boolean known;

	/**
	 * Set up an initial table entry
	 * 
	 * @param vertex: the initial vertex
	 */
	public TableEntry(Vertex vertex) {
		myVertex = vertex;
		known = false;
		weight = 0;
	}

	/** compares the entries based on the weight */
	public int compareTo(TableEntry other) {
		return (weight + distance) - (other.getWeight() + other.getDistance()) ;
	}
	
	@Override
	public String toString() {
		return ("(" + myVertex + ", " + parent + ", " + weight + ", " + known + ") ");
	}

	/** gets the weight */
	public int getWeight() {
		return weight;
	}
	
	/** gets the vertex */
	public Vertex getVertex() {
		return myVertex;
	}
	
	/** gets the parent */
	public Vertex getParent() {
		return parent;
	}
	
	public int getDistance() {
		return distance;
	}
	
	/** finds whether the vertex is known */
	public boolean known() {
		return known;
	}
	
	/** sets whether the vertex is known */
	public void setKnown(boolean val) {
		known = val;
	}
	
	/** sets the parent */
	public void setParent(Vertex p) {
		parent = p;
	}
    
	/** sets the weight */
    public void setWeight(int newWeight) {
        weight = newWeight;
    }
    
    public void setDistance(int newDistance) {
    	distance = newDistance;
    }
	
}
