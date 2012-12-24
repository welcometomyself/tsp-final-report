/*   
 *   TSPGeneticAlgorithm
 *   Solve Traveling Salesmen's Problem By Genetic Algorithm
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
 *   2012/12/24
 *   
 *   Last Update: 2012/12/24
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
import java.util.Random;

import graph.*;
import tsp.util.*;

public class TSPGeneticAlgorithm {

	public static void main(String[] args) throws IOException {
		
		// AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		
		// AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(100); // Given numVertex(cities) as input parameter
		 AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(100);  // Given numVertex(cities) as input parameter
		
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(11, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(11, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10, 0);  // Given numVertex(cities), seed as input parameter
	     
		
		System.out.println("numVertex: " + g.getNumVertex());
		System.out.println("numEdges: " + g.getNumEdges());
		
		System.out.println("Edge Cost Adjacency Matrix:");
		g.printCostMatrix();
		System.out.println("");
		
		TSPGeneticAlgorithm tsp = new TSPGeneticAlgorithm(g);
		
		//tsp.printAllPaths();
		
		ArrayList<Integer[]> population = tsp.getRandomPathList(50);
		System.out.printf("Found %d randomly generated routes:\n", population.size());
		Iterator it = population.iterator();
		while (it.hasNext()) {
			tsp.printPath((Integer[])it.next());
			//tsp.printPathNames((Integer[])it.next());
		}
		
		
	}
	
	public TSPGeneticAlgorithm(Graph g) {
		this.g = g;
	}
	
	
	// Get a list of random paths(chromosomes), may be used as the initial population of genetic algorithm
	public ArrayList<Integer[]> getRandomPathList(int numPath) {
		
		int numVertex = g.getNumVertex();
		ArrayList<Integer[]> pathList = new ArrayList<Integer[]>(numPath);
		Integer[] path = new Integer[numVertex];

		for (int i=0; i<numVertex; i++) path[i] = i; // initial path
		
		Random rnd = new Random();
		
		// iterationLimit is to prevent the situation when the number of cities is large and the number of INF-distance edges is also large
		final int iterationLimit=9999999;
		int numSwap=numVertex>200?30:numVertex/10+10;
		int iterationCount=0;
		int numPathFound=0;
		while ((path = (Integer[])Permutation.nextPermutation(path)) != null && numPathFound < numPath && iterationCount < iterationLimit) {
			Integer[] randomPath = path.clone();
			int i,j;
			
			// swap vertex-1 with another random vertex
			i=1;
			j=rnd.nextInt(numVertex-1);
			Permutation.swap(randomPath, i, j);
			
			// swap again several times (swap more times if the numVertex is large)
			for (int count=0; count<numSwap; count++) {
				i=rnd.nextInt(numVertex-1);
				j=rnd.nextInt(numVertex-1);
				Permutation.swap(randomPath, i, j);
			}
			
			if (getPathCost(randomPath)<Graph.MAXEDGECOST) {
				pathList.add(randomPath);
				numPathFound++;
			}
			iterationCount++; 
		}
		System.out.println("numSwap:"+numSwap);
		return pathList;
	}
	
	// Get a list of random paths(chromosomes) along with greedy search, may be used as the initial population of genetic algorithm
	//public ArrayList<Integer[]> getRandomGreedyPathList(int numPath) {
	//	
	//}
	
	
	
	/*
	public Integer[] getBestPath() {
		return getBestPath(9999999);
	}
	*/
	
	/*
	public Integer[] getBestPath(int iterationLimit) {
		
	}
	
	*/
	
	public double getPathCost(Integer[] pathNodes) {
		
		if (pathNodes.length<2) return Graph.MAXEDGECOST;
		int i;
		double tmpCost;
		double pathCost=0;
		for (i=0; i<pathNodes.length-1; i++) {
			//tmpCost = g.getEdgeCost(pathNodes[i], pathNodes[i+1]);
			//if (tmpCost == Graph.MAXEDGECOST) return Graph.MAXEDGECOST;
			//pathCost += tmpCost;
			pathCost += g.getEdgeCost(pathNodes[i], pathNodes[i+1]);
		}
		//tmpCost = g.getEdgeCost(pathNodes[pathNodes.length-1], pathNodes[0]);
		//if (tmpCost == Graph.MAXEDGECOST) return Graph.MAXEDGECOST;
		//pathCost += tmpCost;
		pathCost += g.getEdgeCost(pathNodes[pathNodes.length-1], pathNodes[0]);
		return pathCost;
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
