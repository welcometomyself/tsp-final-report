/*
 *   Graph Representation using adjacency matrix
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
 *   Joe Huang 2012/12/09
 *   
 *   History:
 *   2013/08/10 Move printCostMatrix() to Graph, renamed as printGraph() 
 *   
 */

package graph;

import java.util.Iterator;
import java.util.Vector;

public class AdjMatrixDirectedGraph extends Graph {
	
	public AdjMatrixDirectedGraph(int numVertex) {

		super(numVertex);
		
		edgeCost = new Vector<Vector<Double>>(numVertex);

		for (int i = 0; i < numVertex; i++) {
			Vector<Double> costVector = new Vector<Double>(numVertex);
			for (int j = 0; j < numVertex; j++)
				costVector.add(new Double(Graph.MAXEDGECOST));
			edgeCost.add(costVector);
		}

	}

	@Override
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

	@Override
	public double getEdgeCost(int i, int j) {
		return edgeCost.get(i).get(j);
	}

	@Override
	public void setEdgeCost(int i, int j, double cost) {
		edgeCost.get(i).set(j, cost);
	}

	// Adjacency Matrix for the edges cost
	private Vector<Vector<Double>> edgeCost;

}
