
/**
 * Distance contains two attributes - a vertex and its distance from source
 * @author Saveri
 *
 */
public class Distance
{
	private Vertex v;
	private double minDist;
	private Vertex src;
	
	/**
	 * Constructs a Distance object with the given info
	 * @param v - Vertex
	 * @param d - distance
	 */
	public Distance(Vertex v, double d) {
		this.v = v;
		this.minDist = d;
		src = null;
	}
	/**
	 * set src info for min distance
	 * @param v
	 */
	public void setSrc(Vertex v) {
		src = v;
	}
	/**
	 * set src info for min distance
	 * @param v
	 */
	public Vertex getSrc() {
		return src;
	}
	/**
	 * Sets minimum distance by a given amount
	 * @param d 
	 */
	public void setMinDistance(double d)
	{
		minDist = d;
	}
	
	/**
	 * Gets the minimum distance from source
	 * @return minimum distance from source
	 */
	public double getMinDistance()
	{
		return minDist;
	}
	
	/**
	 * Gets the vertex
	 * @return
	 */
	public Vertex getVertex()
	{
		return v;
	}
	
	// To make vertex comparisons based on x,y-coordinate of the Vertex parameter
	@Override
	public boolean equals(Object o) { 
		  
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
  
        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof Distance)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Distance c = (Distance) o; 
          
        // Compare the data members and return accordingly  
        return (v.getX() == c.v.getX() && v.getY() == c.v.getY()); 
    }
}
