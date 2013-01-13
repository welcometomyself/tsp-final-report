/*   
 *   TSPPath - Manage the path of TSP
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
 *   2012/12/29
 *   
 *   History:
 *   2013/01/12 
 *   	1. Fix bug for compareTo()
 *   	2. Add getRandomPathList() and static method getTSPPathCost()
 *   	3. Add printPathStartingFrom(int vertex)/printPathNamesStartingFrom(int vertex)			
 */

package graph;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

//import tsp.TSPNearestNeighbor; // tmp test

import tsp.util.Permutation;

public class TSPPath implements java.lang.Comparable {

	/* 
	 * Note:
	 * 1. Do NOT provide the final node in constructor's parameter pathNodes,
	 *    since the final destination(node) must be the same as the starting node for TSP,
	 *    and this characteristic has been taken care of by this class.
	 * 2. pathNodes will be cloned while Graph g will NOT
	 */
	public TSPPath(Integer[] pathNodes, Graph g) {
		if (pathNodes == null) this.pathNodes =null;
		else this.pathNodes = pathNodes.clone(); // I don't want the modified pathNodes to influence this object
		this.g = g; // Modifications of graph can influence this object 
		this.isValid = _isValid();
		this.tspCost = Double.NEGATIVE_INFINITY;
	}
	
	public TSPPath(Integer[] pathNodes, Graph g, double cost) {
		if (pathNodes == null) this.pathNodes =null;
		else this.pathNodes = pathNodes.clone(); // I don't want the modified pathNodes to influence this object
		this.g = g; // Modifications of graph can influence this object 
		this.isValid = _isValid();
		this.tspCost = cost;
	}
	
	public boolean isValid() {
		return this.isValid;
	}
	
	// Tell whether the path is a valid closed path of TSP problem or not
	private boolean _isValid() {
		if (pathNodes == null || pathNodes.length != g.getNumVertex() || pathNodes.length<2 ) return false;
		
		// test if there is any city gets visited more than once
		Set<Integer> set = new TreeSet();
		for (int i=0; i<pathNodes.length; i++)
			set.add(pathNodes[i]);
		if (set.size()<pathNodes.length) return false;
		else return true;
	}
	
	public String getNameAt(int index) {
		return g.getVertexName(index);
	}
	
	public int getVertexAt(int index) {
		if (index<0 || index>=this.g.getNumVertex()) return -1;
		else return pathNodes[index];
	}
	
	// return the index of a given vertex in a path
	public int findIndexOfVertex(int vertex) {
		for (int i=0; i<pathNodes.length; i++)
			if (pathNodes[i]==vertex) return i;
		return -1;
	}
	
	public int getNumVertex() {
		return pathNodes.length;
	}
	
	public Integer[] getIntArray() {
		return pathNodes.clone();
	}
	
	// Get a list of random paths(chromosomes), may be used as the initial population of genetic algorithm
	public static ArrayList<TSPPath> getRandomPathList(int numPath, Graph g) {
		Random rnd = new Random();
		return getRandomPathList(numPath, g, rnd);
	}
	
	public static ArrayList<TSPPath> getRandomPathList(int numPath, Graph g, long seed) {
		Random rnd = new Random(seed);
		return getRandomPathList(numPath, g, rnd);
	}
	
	private static ArrayList<TSPPath> getRandomPathList(int numPath, Graph g, Random rnd) {
		
		final int ITERATION_LIMIT=9999999;
		
		int numVertex = g.getNumVertex();
		ArrayList<TSPPath> pathList = new ArrayList<TSPPath>(numPath);
		
		// tmp test Apply Nearest Neighbor to find initial pathNodes[]
		Integer[] pathNodes = new Integer[numVertex];
		for (int i=0; i<numVertex; i++) pathNodes[i] = i; // initial path
		//TSPNearestNeighbor tsp = new TSPNearestNeighbor(g);
		//Integer[] pathNodes = tsp.getBestPath().getIntArray();
		
		//int numSwap=numVertex>200?30:numVertex/10+10;
		int numSwap=3;
		int iterationCount=0;
		int numPathFound=0;
		while (numPathFound < numPath && iterationCount < ITERATION_LIMIT) {
			
			int i,j;
			// swap several times
			for (int count=0; count<numSwap; count++) {
				i=rnd.nextInt(numVertex-1);
				j=rnd.nextInt(numVertex-1);
				Permutation.swap(pathNodes, i, j);
			}
			
			TSPPath tspPath = new TSPPath(pathNodes, g);
			if (tspPath.getCost()<Graph.MAXEDGECOST) {
				pathList.add(tspPath);
				numPathFound++;
			}
			iterationCount++; 
		}
		// System.out.println("numSwap:"+numSwap);
		return pathList;
	}
	
	
	/* 
	 * Note: Do NOT provide the final node of TSP for parameter pathNodes
	 * since the final destination(node) must be the same as the starting node for TSP,
	 * and this characteristic has been taken care of by this method.
	*/
	public static double getTSPPathCost(Integer[] pathNodes, Graph g) {
		if (pathNodes.length<2) return Graph.MAXEDGECOST;
		int i;
		double tmpCost;
		double pathCost=0;
		for (i=0; i<pathNodes.length-1; i++) {
			pathCost += g.getEdgeCost(pathNodes[i], pathNodes[i+1]);
		}
		pathCost += g.getEdgeCost(pathNodes[pathNodes.length-1], pathNodes[0]);
		
		return pathCost;
	}
	
	public double getCost() {
		return this.getCost(true);
	}
	
	public double getCost(boolean useCache) {
		if (!useCache || tspCost == Double.NEGATIVE_INFINITY) {
			this.tspCost = this._getCost(); // Recalculate the cost and update the cached tspCost
			return this.tspCost;
		} else return tspCost;
	}
	
	// get the cost of the closed TSP path depending on the graph
	private double _getCost() {
		
		if (pathNodes.length<2) return Graph.MAXEDGECOST;
		if (!isValid) return Graph.MAXEDGECOST;
		
		int i;
		double tmpCost;
		double pathCost=0;
		for (i=0; i<pathNodes.length-1; i++) {
			tmpCost = g.getEdgeCost(pathNodes[i], pathNodes[i+1]);
			if (tmpCost == Graph.MAXEDGECOST) return Graph.MAXEDGECOST;
			pathCost += tmpCost;
		}
		tmpCost = g.getEdgeCost(pathNodes[pathNodes.length-1], pathNodes[0]);
		if (tmpCost == Graph.MAXEDGECOST) return Graph.MAXEDGECOST;
		pathCost += tmpCost;
		
		return pathCost;
	}
	
	@Override
	public int compareTo(Object tspPath) {

		if (tspPath instanceof TSPPath) {
			if (this.getCost() < ((TSPPath)tspPath).getCost()) return -1;
			else if (this.getCost() == ((TSPPath)tspPath).getCost()) return 0;
			else return 1;
		} else throw new ClassCastException("Given object is NOT an instance of TSPPath to compareTo().");
		
	}
	
	public void printPath() {
		System.out.println(this);
	}
	
	public void printPathStartingFrom(int vertex) {
		System.out.println(this.toStringStartingFrom(vertex));
	}
	
	public void printPathNames() {
		System.out.println(this.toStringOfVertexNames());
	}
	
	public void printPathNamesStartingFrom(int vertex) {
		System.out.println(this.toStringOfVertexNamesStartingFrom(vertex));
	}
	
	public String toStringOfVertexNames() {
		if (pathNodes == null) return "NULL_TSP_PATH";
		else {
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<pathNodes.length; i++) {
				sb.append(g.getVertexName(pathNodes[i]));
				sb.append(" ");
			}
			sb.append(String.format("Cost = %.1f", this.getCost()));
			return sb.toString();
		}
	}
	
	public String toStringOfVertexNamesStartingFrom(int vertex) {
		StringBuilder sb = new StringBuilder();
		int indexOfVertex=-1;
		for (int i=0; i<pathNodes.length; i++) {
			if (pathNodes[i]==vertex) { indexOfVertex=i; break;}
		}
		
		if (indexOfVertex==-1) return "INVALID_STARTING_VERTEX";
		
		for (int i=indexOfVertex; i<pathNodes.length; i++) {
			sb.append(g.getVertexName(pathNodes[i]));
			sb.append(" ");
		}
		for (int i=0; i<indexOfVertex; i++) {
			sb.append(g.getVertexName(pathNodes[i]));
			sb.append(" ");
		}
		sb.append(String.format("Cost = %.1f", this.getCost()));
		return sb.toString();
	}
	
	@Override
	public String toString() {
		if (pathNodes == null) return "NULL_TSP_PATH";
		else {
			StringBuilder sb = new StringBuilder();
			for (int i=0; i<pathNodes.length; i++) 
				sb.append(String.format("%3d, ", pathNodes[i]));
			sb.append(String.format("Cost = %.1f", this.getCost()));
			return sb.toString();
		}
	}
	
	public String toStringStartingFrom(int vertex) {
		if (pathNodes == null) return "NULL_TSP_PATH";
		else {
			StringBuilder sb = new StringBuilder();
			int indexOfVertex=-1;
			for (int i=0; i<pathNodes.length; i++) {
				if (pathNodes[i]==vertex) { indexOfVertex=i; break;}
			}
			
			if (indexOfVertex==-1) return "INVALID_STARTING_VERTEX";
			
			for (int i=indexOfVertex; i<pathNodes.length; i++) 
				sb.append(String.format("%3d, ", pathNodes[i]));
			for (int i=0; i<indexOfVertex; i++) 
				sb.append(String.format("%3d, ", pathNodes[i]));
			sb.append(String.format("Cost = %.1f", this.getCost()));
			return sb.toString();
		}
	}
	
	private Integer[] pathNodes;
	private boolean isValid;
	private double tspCost;
	private Graph g;
	
}
