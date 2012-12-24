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
 *   Last Update: 2012/12/23
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

public class TSPBruteForce {

	public static void main(String[] args) throws IOException {
		
		// AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		
		// AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10); // Given numVertex(cities) as input parameter
		// AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10);  // Given numVertex(cities) as input parameter
		
		
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(11, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(11, 0);  // Given numVertex(cities), seed as input parameter
	     AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(5, 0);  // Given numVertex(cities), seed as input parameter
	     
		
		System.out.println("numVertex: " + g.getNumVertex());
		System.out.println("numEdges: " + g.getNumEdges());
		
		System.out.println("Edge Cost Adjacency Matrix:");
		g.printCostMatrix();
		System.out.println("");
		
		//AdjMatrixUndirectedGraph g2 = GraphFactory.directed2Undirected(g);
		//System.out.println("g2 cost matrix");
		//g2.printCostMatrix();
		
		TSPBruteForce tsp = new TSPBruteForce(g);
		//TSPBruteForce tsp = new TSPBruteForce(g2);
		
		//tsp.printAllPaths();
		
		System.out.println("Searching for the best path ... ");
		ArrayList<Integer[]> bestPathList = tsp.getBestPathList();
		
		if (bestPathList.isEmpty()) {
			System.out.println("Best Route Found: None");
		} else {
			System.out.println("Best Route Found:");
			Iterator it = bestPathList.iterator();
			while (it.hasNext()) {
				tsp.printPath((Integer[])it.next());
				//tsp.printPathNames((Integer[])it.next());
			}
		}
		
		
	}
	
	public TSPBruteForce(Graph g) {
		this.g = g;
	}
	
	public ArrayList<Integer[]> getBestPathList() {
		return getBestPathList(9999999);
	}
	
	public ArrayList<Integer[]> getBestPathList(int iterationLimit) {
		
		int iterationCount = 0;
		ArrayList<Integer[]> bestPathList = new ArrayList<Integer[]>(30);
		Integer[] bestPath;
		Integer[] path = new Integer[g.getNumVertex()];
		// Simply use integers starting from 0 to represent all the vertices(cities)
		for (int i=0; i<g.getNumVertex(); i++) path[i] = i;
		
		// Sort the Array path[] to ensure that all permutations will be considered
		Arrays.sort(path);
		
		double tmpPathCost;
		double minPathCost = getPathCost(path);
		if (minPathCost>=Graph.MAXEDGECOST) { 
			bestPath = null;
			minPathCost = Graph.MAXEDGECOST;
		}
		else {
			bestPath = path.clone();
			printPath(path);
			bestPathList.add(bestPath);
		}
		
		while ((path = (Integer[])Permutation.nextPermutation(path)) != null && iterationCount < iterationLimit) {
			
			if (path[0]!=0) break; // Use only the first vertex(city) as starting point for the saleman
			
			tmpPathCost = getPathCost(path);
			if (tmpPathCost < minPathCost) { 
				minPathCost = tmpPathCost;
				bestPath = path.clone();
				printPath(path);
				bestPathList.clear();
				bestPathList.add(bestPath);
			} else if (tmpPathCost == minPathCost && tmpPathCost<Graph.MAXEDGECOST) {
				bestPath = path.clone();
				printPath(path);
				bestPathList.add(bestPath);
			} else {
				// printPath(path);
			}
			
			iterationCount++;
		}
		
		
		if (iterationCount >=  iterationLimit) System.out.printf("Reached max. iteration limit %d and the search has stopped%n", iterationLimit);
		else System.out.printf("Iteration count: %d%n", iterationCount);
		
		return bestPathList;
	}
	
	public double getPathCost(Integer[] pathNodes) {
		
		if (pathNodes.length<2) return 999;
		int i;
		double tmpCost;
		double pathCost=0;
		for (i=0; i<pathNodes.length-1; i++) {
			//tmpCost = g.getEdgeCost(pathNodes[i], pathNodes[i+1]);
			//if (tmpCost < MAXCOST) pathCost += tmpCost;
			pathCost += g.getEdgeCost(pathNodes[i], pathNodes[i+1]);
		}
		pathCost += g.getEdgeCost(pathNodes[pathNodes.length-1], pathNodes[0]);
		
		return pathCost;
		
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
	
	
	public void printPath(Integer[] path) {
		if (path == null)  System.out.println("Path NOT Exists");
		else {
			for (int i=0; i<path.length; i++) System.out.printf("%3d, ", path[i]);
			System.out.println(" cost = " + getPathCost(path));
		}
		
	}
	
	public void printPathNames(Integer[] path) {
		
		if (path == null)  System.out.println("Path NOT Exists");
		else {
			for (int i=0; i<path.length; i++) System.out.printf("%5s, ", g.getVertexName(path[i]));
			System.out.println(" cost = " + getPathCost(path));
		}
		
	}
	
	private Graph g;

}
