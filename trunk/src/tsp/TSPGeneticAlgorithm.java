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
 *   Last Update: 2012/12/29
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
		 AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10);  // Given numVertex(cities) as input parameter
		
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
		
		ArrayList<TSPPath> population = tsp.getRandomPathList(50);
		System.out.printf("Found %d randomly generated routes:\n", population.size());
		Iterator it = population.iterator();
		while (it.hasNext()) {
			((TSPPath)it.next()).printPath();
		}
		
		
	}
	
	public TSPGeneticAlgorithm(Graph g) {
		this.g = g;
	}
	
	
	// Get a list of random paths(chromosomes), may be used as the initial population of genetic algorithm
	public ArrayList<TSPPath> getRandomPathList(int numPath) {
		
		int numVertex = g.getNumVertex();
		ArrayList<TSPPath> pathList = new ArrayList<TSPPath>(numPath);
		
		Integer[] pathNodes = new Integer[numVertex];
		for (int i=0; i<numVertex; i++) pathNodes[i] = i; // initial path
		
		Random rnd = new Random();
		
		// iterationLimit is to prevent the situation when the number of cities is large and the number of INF-distance edges is also large
		final int iterationLimit=9999999;
		//int numSwap=numVertex>200?30:numVertex/10+10;
		int numSwap=3;
		int iterationCount=0;
		int numPathFound=0;
		while (numPathFound < numPath && iterationCount < iterationLimit) {
			
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
	
	public void selectFromPopulation(ArrayList<Integer[]> pop, ArrayList<Integer[]> survivors) {
		int numSurvivors = survivors.size();
		int numPop = pop.size();
		
		if (numPop<=numSurvivors) {
			
		}
		
	}
	
	private Graph g;

}
