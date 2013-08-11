/*
 *   Graph Representation
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
 *   Joe Huang 2012/12/09
 *   
 *   History:
 *   2013/08/10 Add printGraph()
 *   
 */

package graph;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public abstract class Graph {

	public static final int MAXEDGECOST = 999999;
	
	public Graph(int numVertex) {

		vertexName = new Vector<String>(numVertex);
		this.numVertex = numVertex;

		for (int i = 0; i < numVertex; i++) {
			vertexName.add("c" + i);
		}

	}

	public int getNumVertex() {
		return numVertex;
	}

	public String getVertexName(int vertexIndex) {
		return vertexName.get(vertexIndex);
	}

	public void setVertexName(int vertexIndex, String name) {
		vertexName.set(vertexIndex, name);
	}

	public abstract int getNumEdges();
	public abstract double getEdgeCost(int i, int j);
	public abstract void setEdgeCost(int i, int j, double cost);
	
	// Print Graph as adjacency matrix by default
	public void printGraph() throws IOException {
		BufferedWriter w = new BufferedWriter(new PrintWriter(System.out));
		printGraph(w);
	}

	public void printGraph(BufferedWriter w) throws IOException {

		double tmpCost;
		w.write("Graph Represented By Adjacency Matrix:");
		w.write("\nnumVertex: " + getNumVertex() + "   numEdges: " + getNumEdges());
		w.write("\n      ");
		for (int i = 0; i < getNumVertex(); i++)
			w.write(String.format("%5s ", getVertexName(i)));
		w.write("\n");
		for (int i = 0; i < getNumVertex(); i++) {
			w.write(String.format("%5s ", getVertexName(i)));
			for (int j = 0; j < getNumVertex(); j++) {
				tmpCost=getEdgeCost(i,j);
				if (tmpCost<Graph.MAXEDGECOST)
					w.write(String.format("%5.0f ", tmpCost));
				else w.write(String.format("  INF "));
			}
			w.write("\n");
		}
		w.write("\n");
		w.flush();

	}
	
	
	private int numVertex;
	private Vector<String> vertexName;

}
