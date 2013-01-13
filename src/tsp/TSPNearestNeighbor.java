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
 *   2013/1/13 
 *	 
 */

package tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.Random;

import graph.*;
import tsp.util.*;

public class TSPNearestNeighbor implements TSPAlgorithm {
	
	public static void main(String[] args) throws IOException {
		
		// AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		// AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10, 0); // Given numVertex(cities) and random seed as input parameter
		AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10,0);  // Given numVertex(cities) and random seed as input parameter

		System.out.println("numVertex: " + g.getNumVertex());
		System.out.println("numEdges: " + g.getNumEdges());
		
		System.out.println("Edge Cost Adjacency Matrix:");
		g.printCostMatrix();
		System.out.println("");
		
		TSPNearestNeighbor tsp = new TSPNearestNeighbor(g);
		
		System.out.printf("The Best Routes Found (Approximation By Nearest Neighbor):\n");
		/*
		ArrayList<TSPPath> bestPathList = tsp.getBestPathList();
		Iterator it = bestPathList.iterator();
		while (it.hasNext()) {
			 ((TSPPath)it.next()).printPath();
			// ((TSPPath)it.next()).printPathStartingFrom(0);
		}
		*/
		
		TSPPath bestPath = tsp.getBestPath();
		bestPath.printPath();
	}
	
	public TSPNearestNeighbor(Graph g) {
		this.g = g;
	}
	
	public ArrayList<TSPPath> getBestPathList() {
		ArrayList<TSPPath> bestPathList = new ArrayList<TSPPath>(1);
		bestPathList.add(getBestPath());
		return bestPathList;
	}
	
	@Override
	public ArrayList<TSPPath> getBestPathList(int numPath) {
		return getBestPathList();
	}
	
	@Override
	public TSPPath getBestPath() {
		
		Date startTime = new Date();
		long startMiliSec = startTime.getTime();
		
		boolean[] isVisited = new boolean[this.g.getNumVertex()];
		for (int i=0; i<this.g.getNumVertex(); i++) isVisited[i] = false;
		
		int currentVertex=0;
		int nextVertex;
		isVisited[currentVertex]=true;
		Integer[] nearestNeighborPath = new Integer[this.g.getNumVertex()];
		nearestNeighborPath[0]=currentVertex;
		int numVisitedVertex=1;
		
		while(numVisitedVertex<this.g.getNumVertex()) {
			nextVertex = getNearestVertex(currentVertex, isVisited);
			isVisited[nextVertex]=true;
			currentVertex = nextVertex;
			nearestNeighborPath[numVisitedVertex]=nextVertex;
			numVisitedVertex++;
		}
		
		Date endTime = new Date();
		long endMiliSec = endTime.getTime();
		System.out.printf("Time Elapsed: %d (ms)\n", (endMiliSec-startMiliSec));
		
		return new TSPPath(nearestNeighborPath, g);
	}
	
	
	private Integer getNearestVertex(Integer currentVertex, boolean[] isVisited) {
		
		if (isVisited.length < this.g.getNumVertex()) return currentVertex;
		double minEdgeCost = Graph.MAXEDGECOST;
		Integer minCostVertex = currentVertex;
		int numVertex = g.getNumVertex();
		for (int j=0; j<numVertex; j++) {
			if (g.getEdgeCost(currentVertex,j) < minEdgeCost && !isVisited[j]) { 
				minEdgeCost = g.getEdgeCost(currentVertex,j);
				minCostVertex = j;
			}
		}
		return minCostVertex;
	}
	
	private Graph g;

}
