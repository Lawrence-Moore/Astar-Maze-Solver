
/**
 * Represents a single edge in a graph
 * 
 * @author robertwaters
 *
 */
public class Edge implements Comparable<Edge> {
	/** the edge;s weight */
	private int weight;
	/** the source vertex */
	private Vertex src;
	/** the destination vertex */
	private Vertex dest;
	
	/**
	 * Construct a new edge
	 * @param u  the source vertex
	 * @param v  the destination vertex
	 * @param weight the edge weight
	 */
	public Edge(Vertex u, Vertex v, int weight) {
		src = u;
		dest = v;
		this.weight = weight;
	}
	
	/** prints out the source vertex, destination vertex, and the weight */
	public String toString() {
		return ("(" + src + ", " + dest + ", " + weight + ")");
	}

	/** compares the edges based on weight*/
	public int compareTo(Edge that) {
		return weight - that.weight;
	}
	
	/** gets weight*/
	public int getWeight() {
		return weight;
	}

	/** gets source vertex*/
	public Vertex getSource() {
		return src;
	}

	/** gets destination vertex*/
	public Vertex getDestination() {
		return dest;
	}

	/** compares edges based on source and destination vertex and weight*/
	public boolean equals(Object o) {
		if (o != null && o instanceof Edge) {
			Edge e = (Edge) o;
			boolean w = weight == e.weight;
			//edges are equal in either direction for undirected graph
			if (src.equals(e.src) && dest.equals(e.dest)) {
				return w;
			} else if (src.equals(e.dest) && dest.equals(e.src)) {
				return w;
			} else {
				return false;
			}
		} else { 
			return false;
		}
	}
	
	/** hashes the edge*/
	public int hashCode() {
		return src.hashCode() ^ dest.hashCode() ^ weight;
	}
}
