/*   
 *   TSPNearestNeighbor
 *   Solve Traveling Salesmen's Problem By Nearest Neighbor Algorithm
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
 *   Joe Huang 2013/1/13 
 *	 History:  
 *   2013/08/10 Do some refactorings
 *   
 */

package tsp;

import graph.Graph;
import graph.TSPPath;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class TSPNearestNeighbor extends AbstractTSPAlgorithm {

	
	public TSPNearestNeighbor(Graph g) {
		super(g);
	}
	
	@Override
	public List<TSPPath> getBestPathList(int maxNumPath) {
		List<TSPPath> bestPathList = new ArrayList<TSPPath>(1);
		TSPPath bestPath = getBestPath();
		if (bestPath != null) {
			bestPathList.add(bestPath);
			return bestPathList;
		} else return null;
	}
	
	@Override
	public TSPPath getBestPath() {
		
		Date startTime = new Date();
		long startMiliSec = startTime.getTime();
		
		boolean[] isVisited = new boolean[getGraph().getNumVertex()];
		for (int i=0; i<getGraph().getNumVertex(); i++) isVisited[i] = false;
		
		int currentVertex=0;
		int nextVertex;
		isVisited[currentVertex]=true;
		Integer[] nearestNeighborPath = new Integer[getGraph().getNumVertex()];
		nearestNeighborPath[0]=currentVertex;
		int numVisitedVertex=1;
		
		while(numVisitedVertex<getGraph().getNumVertex()) {
			nextVertex = getNearestVertex(currentVertex, isVisited);
			isVisited[nextVertex]=true;
			currentVertex = nextVertex;
			nearestNeighborPath[numVisitedVertex]=nextVertex;
			numVisitedVertex++;
		}
		
		Date endTime = new Date();
		long endMiliSec = endTime.getTime();
		System.out.printf("Time Elapsed: %d (ms)\n", (endMiliSec-startMiliSec));
		
		return new TSPPath(nearestNeighborPath, getGraph());
	}
	
	
	private Integer getNearestVertex(Integer currentVertex, boolean[] isVisited) {
		
		if (isVisited.length < this.getGraph().getNumVertex()) return currentVertex;
		double minEdgeCost = Graph.MAXEDGECOST;
		Integer minCostVertex = currentVertex;
		int numVertex = getGraph().getNumVertex();
		for (int j=0; j<numVertex; j++) {
			if (getGraph().getEdgeCost(currentVertex,j) < minEdgeCost && !isVisited[j]) { 
				minEdgeCost = getGraph().getEdgeCost(currentVertex,j);
				minCostVertex = j;
			}
		}
		return minCostVertex;
	}

}
