/*   
 *   TSPBruteForce
 *   Solve Traveling Salesmen's Problem By Brute Force
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
 *   2013/01/12 1. Utilize TSPPath instead of Integer[] as the path representation of TSP
 *   			2. Add computation of time elapsed
 *   2013/08/10 Do some refactorings
 *   
 */

package tsp;

import graph.Graph;
import graph.TSPPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import tsp.util.Permutation;

public class TSPBruteForce extends AbstractTSPAlgorithm {

	public static final int ITERATION_LIMIT=9999999;
	
	public TSPBruteForce(Graph g) {
		super(g);
	}
	
	// print all paths of the graph (of cities for salesmen to visit)
	public void printAllPaths() {
		
		Integer[] path = new Integer[getGraph().getNumVertex()];
		
		// Simply use integers starting from 0 to represent all the vertices(cities)
		for (int i=0; i<getGraph().getNumVertex(); i++) path[i] = i;
		// Sort the Array path[] to ensure that all permutations will be considered
		// Arrays.sort(path); // moved to printPermutations()		 
		 Permutation.printPermutations(path);
		
	}

	
	@Override
	public List<TSPPath> getBestPathList(int maxNumPath) {
		List<TSPPath> allBestPathList = getBestPathListInternal();
		if (maxNumPath < allBestPathList.size() && maxNumPath > 0) {
			ArrayList<TSPPath> bestPathList = new ArrayList<TSPPath>(maxNumPath);
			bestPathList.addAll(allBestPathList.subList(0, maxNumPath));
			return bestPathList;
		} else {
			return allBestPathList;
		}
	}
	
	private List<TSPPath> getBestPathListInternal() {
		Date startTime = new Date();
		long startMiliSec = startTime.getTime();
		
		int iterationCount = 0;
		ArrayList<TSPPath> bestPathList = new ArrayList<TSPPath>(10);
		TSPPath bestPath;
		Integer[] path = new Integer[getGraph().getNumVertex()];
		// Simply use integers starting from 0 to represent all the vertices(cities)
		for (int i=0; i<getGraph().getNumVertex(); i++) path[i] = i;
		
		// Sort the Array path[] to ensure that all permutations will be considered
		Arrays.sort(path);
		
		double tmpPathCost;
		double minPathCost = TSPPath.getTSPPathCost(path, getGraph());
		if (minPathCost>=Graph.MAXEDGECOST) { 
			bestPath = null;
			minPathCost = Graph.MAXEDGECOST;
		}
		else {
			bestPath = new TSPPath(path, getGraph(), minPathCost);
			//bestPath.printPath();
			bestPathList.add(bestPath);
		}
		
		while ((path = (Integer[])Permutation.nextPermutation(path)) != null && iterationCount < ITERATION_LIMIT) {
			
			if (path[0]!=0) break; // Use only the first vertex(city) as starting point for the saleman
			
			tmpPathCost = TSPPath.getTSPPathCost(path, getGraph());
			if (tmpPathCost < minPathCost) { 
				minPathCost = tmpPathCost;
				bestPath = new TSPPath(path, getGraph(), tmpPathCost);
				// bestPath.printPath();
				bestPathList.clear();
				bestPathList.add(bestPath);
			} else if (tmpPathCost == minPathCost && tmpPathCost<Graph.MAXEDGECOST) {
				bestPath = new TSPPath(path, getGraph(), tmpPathCost);
				// bestPath.printPath();
				bestPathList.add(bestPath);
			} else {
				// do nothing
			}
			
			iterationCount++;
		}
		
		if (iterationCount >=  ITERATION_LIMIT) System.out.printf("Reached max. iteration limit %d and the search has stopped%n", ITERATION_LIMIT);
		else System.out.printf("Iteration count: %d%n", iterationCount);
		
		Date endTime = new Date();
		long endMiliSec = endTime.getTime();
		System.out.printf("Time Elapsed: %d (ms)\n", (endMiliSec-startMiliSec));
		
		return bestPathList;
	}

}
