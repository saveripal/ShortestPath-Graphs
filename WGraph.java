
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * Generates a graph from an input file.
 * Calculates shortest path in methods V2V, V2S, S2S
 * 
 * @author Saveri Pal
 *
 */
public class WGraph {
	
	// Graph in the form of a hashmap of Object Vertex and an ArralyList of Object Edge
	public Map<Vertex, ArrayList<Edge>> graph;
	// Arraylist of Object Distance
	private ArrayList<Distance> distance;
	// PQ to be used in Dijkstras
	private PriorityQueue<Distance> unResolved;
	// Maps a vertex to its parent vertex
		private Map<Vertex, Vertex> parent;
	// PQ to be used in V2S
	PriorityQueue<Distance> V2Sdist;
	// Maps a vertex to its path to source in S2S
	Map<Vertex, ArrayList<Integer>> S2SparentList;
	// PQ to be used in S2S for inner loop
	PriorityQueue<Distance> S2Sdist;
	PriorityQueue<Distance> S2Smin;
	Map<Vertex,Vertex> destSrcMap;
	// No. of vertices in the graph
	private int numVertex;
	// No. of edges in the graph
	private int numEdge;
	//Importance Matrix
	ArrayList<ArrayList<Integer>> I;

	
	/**
	 * Constructs a graph from a given input file located in the same directory
	 * 
	 * @param FName - name of input file
	 * @throws FileNotFoundException
	 */
	public WGraph(String FName) throws FileNotFoundException
	{
		File f = new File(FName);
		Scanner in = new Scanner(f);
		PQcomparator com = new PQcomparator();
		unResolved = new PriorityQueue<>(com);
		V2Sdist = new PriorityQueue<>(com);
		S2Sdist = new PriorityQueue<>(com);
		S2Smin = new PriorityQueue<>(com);
	
		parent = new HashMap<Vertex,Vertex>();
		distance = new ArrayList<Distance>();
		
		if(in.hasNextLine())
		{
			numVertex = Integer.parseInt(in.nextLine());
			numEdge = Integer.parseInt(in.nextLine());
			graph = new HashMap<>(numVertex);

			for(int i=0;i<numEdge;i++)
			{
				String line = in.nextLine();
				readLines(line);	
			}
		}
		in.close();
	}
	
	
	/**
	 * Reads lines from the input file starting line 3 till end of file
	 * 
	 * @param line - containing ux uy vx vy wt info
	 */
	private void readLines(String line)
	{
		Scanner temp = new Scanner(line);
		while(temp.hasNextInt())
		{
			int ux = temp.nextInt();
			int uy = temp.nextInt();
			int vx = temp.nextInt();
			int vy = temp.nextInt();
			int wt = temp.nextInt();
			
			Vertex u = new Vertex(ux,uy);
			Vertex v = new Vertex(vx,vy);
			
			// if graph doesn't contain source vertex (ux,uy), add new vertex
			if(!graph.containsKey(u))
			{
				addVertex(ux,uy);
			}
			if(!graph.containsKey(v))
			{
				addVertex(vx,vy);
			}

			// add new edge between the vertices
			addEdge(ux,uy,vx,vy,wt);
		}
		temp.close();
	}
	
	/**
	 * Adds a new vertex to the graph. Creates an empty edge arraylist 
	 * associated with the newly added vertex
	 * 
	 * @param u - vertex to be added
	 */
	private void addVertex(int x, int y)
	{
		// Create new vertex
		Vertex newNode = new Vertex(x,y);
		// Add new vertex to graph
		graph.put(newNode, new ArrayList<Edge>());
	}	
	
	/** 
	 * Adds an edge to the arraylist of the source vertex
	 * 
	 * @param u - source vertex
	 * @param v - destination vertex
	 * @param wt - weight of edge between (u,v)
	 */
	private void addEdge(int ux, int uy, int vx, int vy, int wt)
	{
		// Create new edge
		Edge e = new Edge(ux, uy, vx, vy, wt);
		// Add new edge to graph
		Vertex v = new Vertex(ux,uy);
		(graph.get(v)).add(e);	
	}
	
	/**
	 * Given a vertex, generates a list of all adjacent vertices
	 * 
	 * @param v - Vertex
	 * @return - ArrayList of adjacent vertices from the graph
	 */
	public ArrayList<Edge> getAdjEdges(Vertex v)
	{
		return graph.get(v);
	}
	
	/**
	 * Given vertices u and v, find shortest path from u to v
	 * 
	 * @param ux - x coordinate of source
	 * @param uy - y coordinate of source
	 * @param vx - x coordinate of destination
	 * @param vy - y coordinate of destination
	 * @return - - Arraylist containing coordinates of vertices in the shortest path
	 */
	public ArrayList<Integer> V2V(int ux, int uy, int vx, int vy)
	{
		distance.clear();
		parent.clear();
		ArrayList<Integer> output = new ArrayList<>();
		Vertex source = new Vertex(ux,uy);
		Vertex dest = new Vertex(vx,vy);
		
		// Run Dijkstra to find shortest path to all vertices in graph
		dijkstra(source);
		
		// a temp vertex to store the dest, input its coordinates in output list
		Vertex step = dest; 
		output = getPath(step);
		int xx;
		return output;
	}
	
	/**
	 * Given a source and set S2, V2S finds the shortest path from source vertex
	 * to a set of destination vertices. Finds min of the distances among all destination vertices
	 * and outputs the arraylist containing the path
	 * 
	 * @param ux - x coordinate of source
	 * @param uy - y coordinate of source
	 * @param S2 - ArrayList containing vertex coordinates
	 * @return - Arraylist containing coordinates of vertices in the shortest path
	 *  the list has the following structure ux, uy, u1x, u2y, u3x, u4y, vx, vy
	 */
	public ArrayList<Integer> V2S(int ux, int uy, ArrayList<Integer> S2)
	{
		distance.clear();
		parent.clear();
		ArrayList<Integer> output = new ArrayList<>();
		Vertex source = new Vertex(ux,uy);
		
		// Run Dijkstra to find shortest path to all vertices in graph
		dijkstra(source);
		
		// Add the vertices of the destination set S2 into a Priority Queue
		// to find the required min distance between V and S
		for(int i=0; i<S2.size(); i++)
		{
			// Create vertex from input arraylist; i, i+1th position indicate x,y coordinates
			Vertex step = new Vertex(S2.get(i), S2.get(++i));
			// create Distance object with dummy dist=0
			Distance d2 = new Distance(step,0);
			// locate this object in the distance Arraylist <Distance>, and set the correct distance
			int indexOfStep = distance.indexOf(d2);
			d2.setMinDistance(distance.get(indexOfStep).getMinDistance());
			// Add to priority queue
			V2Sdist.add(d2);
		}
		
		// Pull out the vertex with min distance from PQ 
		Distance minVertex = V2Sdist.poll();
		Vertex step = minVertex.getVertex();
		if(source.equals(step))
		{
			output.add(source.getY());
			output.add(0,source.getX());
			return output;
		}
		
		// generate path by backtracking
		output = getPath(step);

		return output;
	}
	
	/**
	 * Given a set S1 and set S2, S2S finds the shortest path from S1 to S2
	 * @param S1 - ArrayList containing source vertex coordinates
	 * @param S2 - ArrayList containing destination vertex coordinates
	 * @return Arraylist containing coordinates of vertices in the shortest path
	 */
	public ArrayList<Integer> S2S(ArrayList<Integer> S1, ArrayList<Integer> S2)
	{
		ArrayList<Integer> output = new ArrayList<>(1);
		ArrayList<Integer> sourceParent = new ArrayList<>();
	    S2SparentList= new HashMap<>();
		
		for(int i=0; i<S1.size(); i++)
		{
			distance.clear();
			parent.clear();
			Vertex source = new Vertex(S1.get(i), S1.get(++i));
			dijkstra(source);
			for(int j=0; j<S2.size(); j++)
			{
				Vertex step = new Vertex(S2.get(j), S2.get(++j));
				Distance d2 = new Distance(step,0);
				int indexOfStep = distance.indexOf(d2);
				d2.setMinDistance(distance.get(indexOfStep).getMinDistance());
				S2Sdist.add(d2);
			}
			// Pick the vertex with min dist in V2S operation add it to a PQ
			Distance minVertex = S2Sdist.poll();
			minVertex.setSrc(source);
			S2Smin.add(minVertex);
			Vertex step = minVertex.getVertex();

			if(source.equals(step))
			{
				output.add(source.getY());
				output.add(0,source.getX());
				return output;
			}
			
			sourceParent = getPath(step);	
			S2SparentList.put(source, sourceParent);
		}
		Vertex v = new Vertex(0,0);
		Distance actualMinVertexDist = new Distance(v,0);
		actualMinVertexDist = S2Smin.poll();
		Vertex actualMinVertex = actualMinVertexDist.getSrc();
		output = S2SparentList.get(actualMinVertex);
		return output;
	}
	
	
	/**
	 * Generates path of a vertex, v from source vertex by backtracking
	 * The generated path is from source to v
	 * @param v - vertex
	 * @return path from source to v
	 */
	public ArrayList<Integer> getPath(Vertex v)
	{
		
		ArrayList<Integer> path = new ArrayList<>();
		// Saving dest to be appended later
		Vertex dest = v;
		Vertex step = v;

		while(parent.get(step)!=null)
		{
			step = parent.get(step);
			path.add(0,step.getY());
			path.add(0,step.getX());
		}
		
		if(!path.isEmpty())
		{
			path.add(path.size(),dest.getX());
			path.add(path.size(),dest.getY());
		}
		
		return path;
	}
	
	/**
	 * Dijkstra's Algorithm
	 * 
	 * @param source - vertex
	 * @param dest - vertex
	 */
	private void dijkstra (Vertex source)
	{
		Set<Vertex> settledVertices = new HashSet<Vertex>();
		// populating the distance ArrayList <Distance>
		for (Vertex key : graph.keySet()) 
			{
				Distance d;
				// source case
				if(key.equals(source))
				{
					// create Distance object with source vertex and set min dist to source as zero
					d = new Distance(key,0);
					distance.add(d);
					// parent of source is null
					parent.put(key,null);
					// Add source vertex to unResolved Priority Queue
					unResolved.add(d);
				}
				
				// all other vertices case
				else
				{
					// min dist from source is set to positive infinity 
					d = new Distance(key,Double.POSITIVE_INFINITY);
					distance.add(d);
				}	
			}

		
		// loops until unresolved is empty
		while (!unResolved.isEmpty())
        {
			// Pulls out the Distance object with min distance from source: comparator used in PQ
        	Distance dMin = unResolved.poll(); 
        	Vertex aNode = dMin.getVertex();
            settledVertices.add(aNode);
            findMinimalDistances(aNode, dMin); 	
        }	
	}
	
	/**
	 * 
	 * @param node
	 */
	private void findMinimalDistances(Vertex node, Distance dist)
	{
		// get all outgoing edges of the given vertex
        ArrayList<Edge> adjacentEdges = getAdjEdges(node);
        for (int i=0; i<adjacentEdges.size(); i++)
        {
        	// get an adjacent vertex
        	Vertex tar = adjacentEdges.get(i).getDestination();
        	Distance target = new Distance(tar,0);
        	// weight of the edge between given vertex and target
        	int weightToTarget = adjacentEdges.get(i).getWeight();
        	// If path distance of target from source is greater than the sum of path distance of current node from source 
        	// and edge weight of (node,target), Update distance of target from source
        	int indexTarget = distance.indexOf(target);
        	int indexNode = distance.indexOf(dist);
        	if (distance.get(indexTarget).getMinDistance() > distance.get(indexNode).getMinDistance() + weightToTarget)
            {
        		// locate the target vertex in distance ArrayList and set its dist to new dist
        		Distance d = distance.get(indexTarget);
        		d.setMinDistance(distance.get(indexNode).getMinDistance() + (double)weightToTarget);
        		// before next is executed, target minDist has already been updated in distance Map: Shallow copy
        		distance.set(indexTarget,d);        	
                parent.put(tar, node);
                unResolved.add(d);
            }
        }

    }

}
