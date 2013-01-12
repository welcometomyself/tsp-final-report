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
 *   2012/12/09
 *   
 *   History: 
 *   2012/12/23 XXXXX
 *   2013/1/12 Utilize TSPPath instead of Integer[] as the path representation of TSP
 */

package tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import graph.*;
import tsp.util.*;

public class TSPBruteForce implements TSPAlgorithm {

	public static final int ITERATION_LIMIT=9999999;
	
	public static void main(String[] args) throws IOException {
		
		// AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		//AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10, 0); // Given numVertex(cities) and random seed as input parameter
		AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10,0);  // Given numVertex(cities) and random seed as input parameter

		System.out.println("numVertex: " + g.getNumVertex());
		System.out.println("numEdges: " + g.getNumEdges());
		
		System.out.println("Edge Cost Adjacency Matrix:");
		g.printCostMatrix();
		System.out.println("");
		
		TSPAlgorithm tsp = new TSPBruteForce(g);
		
		System.out.println("Searching for the best path ... ");
		ArrayList<TSPPath> bestPathList = tsp.getBestPathList(20);
		
		if (bestPathList.isEmpty()) {
			System.out.println("Best Route Found: None");
		} else {
			System.out.println("Best Route Found:");
			Iterator it = bestPathList.iterator();
			while (it.hasNext()) {
				((TSPPath)it.next()).printPath();
			}
		}
		
		//TSPPath bp = tsp.getBestPath();
		//System.out.println("(test) Best Route Found:");
		//bp.printPath();
		
	}
	
	public TSPBruteForce(Graph g) {
		this.g = g;
	}
	
	
	@Override
	public TSPPath getBestPath() {
		return getBestPathList(1).get(0);
	}
	
	@Override
	public ArrayList<TSPPath> getBestPathList(int numPath) {
		ArrayList<TSPPath> allBestPathList = getBestPathList();
		if (numPath < allBestPathList.size() && numPath > 0) {
			ArrayList<TSPPath> bestPathList = new ArrayList<TSPPath>(numPath);
			bestPathList.addAll(allBestPathList.subList(0, numPath));
			return bestPathList;
		} else {
			return allBestPathList;
		}
	}
	
	public ArrayList<TSPPath> getBestPathList() {
		
		int iterationCount = 0;
		ArrayList<TSPPath> bestPathList = new ArrayList<TSPPath>(10);
		TSPPath bestPath;
		Integer[] path = new Integer[g.getNumVertex()];
		// Simply use integers starting from 0 to represent all the vertices(cities)
		for (int i=0; i<g.getNumVertex(); i++) path[i] = i;
		
		// Sort the Array path[] to ensure that all permutations will be considered
		Arrays.sort(path);
		
		double tmpPathCost;
		double minPathCost = TSPPath.getTSPPathCost(path, g);
		if (minPathCost>=Graph.MAXEDGECOST) { 
			bestPath = null;
			minPathCost = Graph.MAXEDGECOST;
		}
		else {
			bestPath = new TSPPath(path, g, minPathCost);
			//bestPath.printPath();
			bestPathList.add(bestPath);
		}
		
		while ((path = (Integer[])Permutation.nextPermutation(path)) != null && iterationCount < ITERATION_LIMIT) {
			
			if (path[0]!=0) break; // Use only the first vertex(city) as starting point for the saleman
			
			tmpPathCost = TSPPath.getTSPPathCost(path, g);
			if (tmpPathCost < minPathCost) { 
				minPathCost = tmpPathCost;
				bestPath = new TSPPath(path, g, tmpPathCost);
				// bestPath.printPath();
				bestPathList.clear();
				bestPathList.add(bestPath);
			} else if (tmpPathCost == minPathCost && tmpPathCost<Graph.MAXEDGECOST) {
				bestPath = new TSPPath(path, g, tmpPathCost);
				// bestPath.printPath();
				bestPathList.add(bestPath);
			} else {
				// do nothing
			}
			
			iterationCount++;
		}
		
		if (iterationCount >=  ITERATION_LIMIT) System.out.printf("Reached max. iteration limit %d and the search has stopped%n", ITERATION_LIMIT);
		else System.out.printf("Iteration count: %d%n", iterationCount);
		
		return bestPathList;
	}
	
	
	// print all paths of the graph (of cities for salesmen to visit)
	public void printAllPaths() {
		
		Integer[] path = new Integer[g.getNumVertex()];
		
		// Simply use integers starting from 0 to represent all the vertices(cities)
		for (int i=0; i<g.getNumVertex(); i++) path[i] = i;
		// Sort the Array path[] to ensure that all permutations will be considered
		// Arrays.sort(path); // moved to printPermutations()		 
		 Permutation.printPermutations(path);
		
	}
	
	private Graph g;

}
