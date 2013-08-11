
/*
 *   GraphFactory
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 	 2012/12/09
 * 
 *   Last Update: 2012/12/23
 * 
 */

package graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class GraphFactory {
	
	// For Unit Test
	public static void main(String[] args) throws IOException {

		//BufferedReader r = new BufferedReader(new FileReader("graph2.txt"));
		//AdjMatrixDirectedGraph g = GraphFactory.read(r);
		
		// AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		
		// AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10); // Given numVertex(cities) as input parameter
		// AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10);  // Given numVertex(cities) as input parameter
		
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(11, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(11, 0);  // Given numVertex(cities), seed as input parameter
	     AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(5, 0);  // Given numVertex(cities), seed as input parameter
		
	     
		System.out.println("numVertex: " + g.getNumVertex());
		System.out.println("numEdges: " + g.getNumEdges());
		
		g.setVertexName(0, "WAS");
		g.setVertexName(1, "BOS");
		g.setVertexName(2, "NYY");
		g.setVertexName(3, "LOS");
		g.setVertexName(4, "TPE");
		
		g.printGraph();
		
		System.out.println("");
		
		AdjMatrixUndirectedGraph g2 = GraphFactory.directed2Undirected(g);
		g2.printGraph();
		
		System.out.println("");
		
		AdjMatrixDirectedGraph g3 = GraphFactory.undirected2Directed(g2);
		g3.printGraph();
		
	}
	
	
	// Get random undirected adjacency matrix graph
	public static AdjMatrixUndirectedGraph getRandomUndirectedGraph(int numVertex) {
		int maxEdgeCost = numVertex/2;
		if (maxEdgeCost > 100) maxEdgeCost=100;
		Random rnd = new Random();
		return getRandomUndirectedGraph(numVertex, maxEdgeCost, rnd);
	}
	
	public static AdjMatrixUndirectedGraph getRandomUndirectedGraph(int numVertex, long seed) {
		int maxEdgeCost = numVertex/2;
		if (maxEdgeCost > 100) maxEdgeCost=100;
		return getRandomUndirectedGraph(numVertex, maxEdgeCost, seed);
	}
	
	public static AdjMatrixUndirectedGraph getRandomUndirectedGraph(int numVertex, int maxEdgeCost, long seed) {
		Random rnd = new Random(seed);
		return getRandomUndirectedGraph(numVertex, maxEdgeCost, rnd);
	}

	private static AdjMatrixUndirectedGraph getRandomUndirectedGraph(int numVertex, int maxEdgeCost, Random rnd) {
		AdjMatrixUndirectedGraph g = new AdjMatrixUndirectedGraph(numVertex);
		int tmpCost;
		for (int i=0; i<numVertex; i++) {
			for (int j=0; j<=i; j++) {
				tmpCost = (int)(rnd.nextInt(maxEdgeCost)+1);
				if (tmpCost > 0 && i!=j) g.setEdgeCost(i, j, tmpCost);
			}
		}
		return g;
	}
	
	
	
	// Get random directed adjacency matrix graph
	public static AdjMatrixDirectedGraph getRandomDirectedGraph(int numVertex) {
		int maxEdgeCost = numVertex/2;
		if (maxEdgeCost > 100) maxEdgeCost=100;
		Random rnd = new Random();
		return getRandomDirectedGraph(numVertex, maxEdgeCost, rnd);
	}
	
	public static AdjMatrixDirectedGraph getRandomDirectedGraph(int numVertex, long seed) {
		int maxEdgeCost = numVertex/2;
		if (maxEdgeCost > 100) maxEdgeCost=100;
		return getRandomDirectedGraph(numVertex, maxEdgeCost, seed);
	}
	
	public static AdjMatrixDirectedGraph getRandomDirectedGraph(int numVertex, int maxEdgeCost, long seed) {
		Random rnd = new Random(seed);
		return getRandomDirectedGraph(numVertex, maxEdgeCost, rnd);
	}

	private static AdjMatrixDirectedGraph getRandomDirectedGraph(int numVertex, int maxEdgeCost, Random rnd) {
		AdjMatrixDirectedGraph g = new AdjMatrixDirectedGraph(numVertex);
		int tmpCost;
		for (int i=0; i<numVertex; i++) {
			for (int j=0; j<numVertex; j++) {
				tmpCost = (int)(rnd.nextInt(maxEdgeCost)+1);
				if (tmpCost > 0 && i!=j) g.setEdgeCost(i, j, tmpCost);
			}
		}
		return g;
	}
	
	
	/*
	 * Degrade given directed graph into an undirected graph, removing the upper triangular entries
	 */
	public static AdjMatrixUndirectedGraph directed2Undirected(AdjMatrixDirectedGraph directedGraph) {
		int numVertex = directedGraph.getNumVertex();
		AdjMatrixUndirectedGraph g = new AdjMatrixUndirectedGraph(numVertex);
		for (int i=0; i<numVertex; i++) {
			g.setVertexName(i, directedGraph.getVertexName(i));
			for (int j=0; j<=i; j++) {
				 g.setEdgeCost(i, j, directedGraph.getEdgeCost(i, j));
			}
		}
		return g;
	}
	
	
	/*
	 * Upgrade given undirected graph into a symmetric directed graph
	 */
	public static AdjMatrixDirectedGraph undirected2Directed(AdjMatrixUndirectedGraph undirectedGraph) {
		int numVertex = undirectedGraph.getNumVertex();
		AdjMatrixDirectedGraph g = new AdjMatrixDirectedGraph(numVertex);
		for (int i=0; i<numVertex; i++) {
			g.setVertexName(i, undirectedGraph.getVertexName(i));
			for (int j=0; j<=i; j++) {
				 g.setEdgeCost(i, j, undirectedGraph.getEdgeCost(i, j));
			}
			for (int j=i+1; j<numVertex; j++) {
				g.setEdgeCost(i, j, undirectedGraph.getEdgeCost(j, i));
			}
		}
		return g;
	}
	
	
	/*
	 *   Reads and returns a graph defined by lines of texts:
	 *   1. The first line contains only an integer representing the number of vertices
	 *   2. The following lines contain the edges represented by three columns i:Integer, j:Integer, edge_cost:Double delimited by spaces
	 *      Where i is the start node and j is the end node of the edge
	*/
	public static AdjMatrixDirectedGraph readFromFile(String filePath) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(filePath));
		return read(r);
	}
	
	public static AdjMatrixDirectedGraph read(BufferedReader r) throws IOException {
		//return new Graph(20);
		String line;
		
		String [] edge = new String [3];
		int i, j;
		double edgeCost;
		int numVertex;
		numVertex = Integer.parseInt(r.readLine());
		AdjMatrixDirectedGraph g = new AdjMatrixDirectedGraph(numVertex);
				
		while (true) {
			
			line = r.readLine();
			if (line == null) break;
			edge = line.split(" ", 3);
			i = Integer.parseInt(edge[0]);
			j = Integer.parseInt(edge[1]);
			edgeCost = Double.parseDouble(edge[2]);
			g.setEdgeCost(i, j, edgeCost);
			
		}
		
		return g;
		
	}
	
}
