# ShortestPath-Graphs
Three variants of the shortest path problem in graphs.
## V2V 
Given vertices u and v, find the shortest path to from u to v. Such a path is referred to as V2V (u, v).
## V2S
Given a vertex u and a set of vertices S, find the shortest path from u to some vertex in S. Such a path is referred to as V2S(u,S).
## S2S
Given a set of vertices S1 and a set of vertices S2, find the shortest path from some vertex in S1 to some vertex in S2. Such a path is referred to as S2S(S1,S2).
## File reading format
The class WGraph takes as input a file name and reads the file from the same directory. It creates a graph representation from the file data. The file data is formatted as follows:

(a) First line contains a number indicating the number of vertices in the graph

(b) Second line contains a number indicating the number of edges in the graph

(c) All subsequent lines have five numbers: source vertex coordinates (first two numbers),
destination vertex coordinates (third and fourth numbers) and weight of the edge con-
necting the source vertex to the destination (assume direction of edge from source to
destination)

For example:

4

4

1 2 3 4 10

5 6 1 2 9

3 4 5 6 8

1 2 7 8 2

Numbers in the same line are separated by a single space.


