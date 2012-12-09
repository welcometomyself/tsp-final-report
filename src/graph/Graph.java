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
 *   Last Update: 2012/12/09
 *   
 */

package graph;

import java.util.Vector;

public abstract class Graph {

	public static final int MAXEDGECOST = 999;
	
	public Graph(int numVertex) {

		vertexName = new Vector<String>(numVertex);
		this.numVertex = numVertex;

		for (int i = 0; i < numVertex; i++) {
			vertexName.add("v" + i);
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

	private int numVertex;
	private Vector<String> vertexName;

}
