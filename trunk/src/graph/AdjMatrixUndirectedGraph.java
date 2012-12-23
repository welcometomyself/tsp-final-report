/*
 *   Undirected Graph Representation using adjacency matrix
 *   Only the lower triangle of the adjacency matrix is used in the undirected graph
 *   Query for (i,j)-entry will return the (j,i)-entry result if i<j 
 *   
 * 	 This program is free software: you can redistribute it and/or modify
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
 *   Last Update: 2012/12/23
 */

package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Vector;

public class AdjMatrixUndirectedGraph extends Graph {
	
	public AdjMatrixUndirectedGraph(int numVertex) {

		super(numVertex);
		
		edgeCost = new Vector<Vector<Double>>(numVertex);

		for (int i = 0; i < numVertex; i++) {
			Vector<Double> costVector = new Vector<Double>(numVertex);
			for (int j = 0; j <=i; j++)
				costVector.add(new Double(Graph.MAXEDGECOST));
			edgeCost.add(costVector);
		}

	}

	public int getNumEdges() {
		int numEdges = 0;
		double tmpEdgeCost;
		Iterator itx = edgeCost.iterator();
		while (itx.hasNext()) {
			Vector<Double> row = (Vector<Double>) itx.next();
			Iterator ity = row.iterator();
			while (ity.hasNext()) {
				tmpEdgeCost = (Double) ity.next();
				if (tmpEdgeCost > 0 && tmpEdgeCost < Graph.MAXEDGECOST)
					numEdges++;
			}
		}
		return numEdges;
	}

	public double getEdgeCost(int i, int j) {
		if (i<j) return edgeCost.get(j).get(i);
		else return edgeCost.get(i).get(j);
	}

	public void setEdgeCost(int i, int j, double cost) {
		if (i<j) edgeCost.get(j).set(i, cost);
		else edgeCost.get(i).set(j, cost);
	}

	// Edge Cost Adjacency Matrix
	public void printCostMatrix() throws IOException {
		BufferedWriter w = new BufferedWriter(new PrintWriter(System.out));
		printCostMatrix(w);
	}

	public void printCostMatrix(BufferedWriter w) throws IOException {

		w.write("      ");
		for (int i = 0; i < getNumVertex(); i++)
			w.write(String.format("%5s ", getVertexName(i)));
		w.write("\n");
		for (int i = 0; i < getNumVertex(); i++) {
			w.write(String.format("%5s ", getVertexName(i)));
			for (int j = 0; j <=i; j++)
				w.write(String.format("%5.0f ", getEdgeCost(i, j)));
			w.write("\n");
		}
		w.flush();

	}

	// Adjacency Matrix for the edges cost
	private Vector<Vector<Double>> edgeCost;

}
