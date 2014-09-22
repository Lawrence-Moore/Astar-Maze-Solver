
public class MazeTableEntry implements Comparable<MazeTableEntry>{
	
	/**Distance from destination node */
	private int distance;
	
	/** the vertex for this entry */
	private Vertex myVertex;

	/** the immediately preceding vertex in the shortest path */
	private Vertex parent;
	
	/** whether myVertex's shortest path has been found */
	private boolean known;

	/**
	 * Set up an initial table entry
	 * 
	 * @param vertex: the initial vertex
	 */
	public MazeTableEntry(Vertex vertex) {
		myVertex = vertex;
		known = false;
	}

	/** compares the entries based on the weight */
	public int compareTo(MazeTableEntry incomingEntry) {
		return (distance) - (incomingEntry.getDistance()) ;
	}
	
	@Override
	public String toString() {
		return ("(" + myVertex + ", " + parent + ", " + distance + ", " + known + ") ");
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
    
    public void setDistance(int newDistance) {
    	distance = newDistance;
    }
}
