
/**
 * Edge with source, destination and weight information
 * @author Saveri
 *
 */
public class Edge 

 {
	private Vertex src;
	private Vertex dest;
	private int wt;
	
	/**
	 * Constructor to construct an edge between two vertices (i.e source and destination)
	 * 
	 * @param source - start vertex
	 * @param destination - end vertex
	 * @param weight - weight of edge between two vertices
	 */
	public Edge(int ux, int uy, int vx, int vy, int weight)
	{
		Vertex u = new Vertex(ux,uy);
		Vertex v = new Vertex(vx,vy);
		src = u;
		dest = v;
		wt = weight;	
	}
	
	/**
	 * Gets the start vertex of an edge
	 * @return source vertex
	 */
	public Vertex getSource()
	{
		return src;
	}
	
	/**
	 * Gets the end vertex of an edge
	 * @return end vertex
	 */
	public Vertex getDestination()
	{
		return dest;
	}
	
	/**
	 * Gets the weight of an edge
	 * @return weight of edge
	 */
	public int getWeight()
	{
		return wt;
	}
	
	/**
	 * Gets x-coordinate of destination vertex
	 * @return x-coordinate of destination vertex
	 */
	public int getDestX()
	{
		return dest.getX();
	}
	
	/**
	 * Gets y-coordinate of destination vertex
	 * @return y-coordinate of destination vertex
	 */
	public int getDestY()
	{
		return dest.getY();
	}
	
	/**
	 * Returns the name of the edge which constitues the x and y coordinate
	 * in string format
	 * @return name of edge
	 */
	public String getEdgeName()
	{
	return this.getDestX()+""+this.getDestY();	
	}


}
