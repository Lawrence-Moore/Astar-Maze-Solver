/**
 * This class represents a single vertex in a graph
 * 
 * @author Lawrence Moore
 *
 */
public class Vertex {
	
	/** the name of the vertex */
	private String name;
	private int xposition, yposition;

	/**
	 * Construct a vertex from a name
	 * @param pname the name of the vertex
	 */
	public Vertex(String pname) {
		name = pname;
	}
	
	public Vertex(String pname, int x, int y) {
		name = pname;
		xposition = x;
		yposition = y;
	}
	
	public void setPosition(int x, int y) {
		xposition = x;
		yposition = y;
	}

	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * @return the name of the vertex
	 */
	public String getName() {
		return name;
	}
	
	public int getX() {
		return xposition;
	}
	
	public int getY() {
		return yposition;
	}
	
	/**
	 * determines whether two Vertexes are equal
	 * @param the object to compare to
	 * @return whether the objects are equal
	 */
	public boolean equals(Object o) {
		if (o != null && o instanceof Vertex) {
			Vertex v = (Vertex) o;
			return name.equals(v.name);
		} else {
			return false;
		}
	}
	
	/**
	 * calculates the hash
	 */
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
