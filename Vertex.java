

import java.util.Objects;

/**
 * A vertex with x and y coordinate
 * @author Saveri
 *
 */
public class Vertex 
 {
		// Vertex coordinates
		private int x;
		private int y;

		/**
		 * Constructs a new vertex with given name
		 * @param vertName
		 */
		public Vertex(int xCoord, int yCoord)
		{
			x = xCoord;
			y = yCoord;
		}
		
		/**
		 * Gets x-coordinate of vertex
		 * @return x-coordinate of vertex
		 */
		public int getX()
		{
			return x;
		}
		
		/**
		 * Gets y-coordinate of vertex
		 * @return y-coordinate of vertex
		 */
		public int getY()
		{
			return y;
		}

		// To make vertex comparisons based on x,y-coordinate of a vertex 
		@Override
		public boolean equals(Object o) { 
			  
	        // If the object is compared with itself then return true   
	        if (o == this) { 
	            return true; 
	        } 
	  
	        /* Check if o is an instance of Complex or not 
	          "null instanceof [type]" also returns false */
	        if (!(o instanceof Vertex)) { 
	            return false; 
	        } 
	          
	        // typecast o to Complex so that we can compare data members  
	        Vertex c = (Vertex) o; 
	          
	        // Compare the data members and return accordingly  
	        return (x == c.x) && (y == c.y); 
	    }
		
		@Override
		public int hashCode() {
	        return Objects.hash(x,y);
	    }
}
