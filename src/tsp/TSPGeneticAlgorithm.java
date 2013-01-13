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
 *   2013/1/13 Cycle Operator is used in cross over reproduction
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
import java.util.Vector;
import java.util.Random;

import graph.*;
import tsp.util.*;

public class TSPGeneticAlgorithm implements TSPAlgorithm {
	
	public static void main(String[] args) throws IOException {
		
		// AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		//AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10,0); // Given numVertex(cities) and random seed as input parameter
		AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10,0);  // Given numVertex(cities) and random seed as input parameter

		System.out.println("numVertex: " + g.getNumVertex());
		System.out.println("numEdges: " + g.getNumEdges());
		
		System.out.println("Edge Cost Adjacency Matrix:");
		g.printCostMatrix();
		System.out.println("");
		
		TSPGeneticAlgorithm tsp = new TSPGeneticAlgorithm(g);
		tsp.setRandomSeed(0);
		
		System.out.printf("The Best Routes Found (Approximation By Genetic Algorithm):\n");
		ArrayList<TSPPath> bestPathList = tsp.getBestPathList(10, 100); // with crossover reproduction
		Iterator it = bestPathList.iterator();
		while (it.hasNext()) {
			((TSPPath)it.next()).printPath();
			//((TSPPath)it.next()).printPathStartingFrom(0);
		}
		
		System.out.println("");
		System.out.printf("The Best Routes Found (Approximation By Genetic Algorithm):\n");
		bestPathList = tsp.getBestPathList(10, 0); // without crossover reproduction
		it = bestPathList.iterator();
		while (it.hasNext()) {
			((TSPPath)it.next()).printPath();
			//((TSPPath)it.next()).printPathStartingFrom(0);
		}
		
		/*
		ArrayList<TSPPath> crossoverTestPop = new ArrayList<TSPPath>();
		tsp.cxCrossover(bestPathList.get(0), bestPathList.get(1), crossoverTestPop);
		System.out.printf("CX Crossover Test:\n");
		it = crossoverTestPop.iterator();
		while (it.hasNext()) {
			((TSPPath)it.next()).printPath();
			//((TSPPath)it.next()).printPathStartingFrom(0);
		}
		*/
	}



	public TSPGeneticAlgorithm(Graph g) {
		this(g, -1, 100);
	}
	
	public TSPGeneticAlgorithm(Graph g, long randomSeed, int popSize) {
		this.g = g;
		this.randomSeed = randomSeed;
		this.rnd = new Random(randomSeed);
		this.popSize = popSize;
	}
	
	public ArrayList<TSPPath> getBestPathList(int numPath, int maxNumIteration) {
		Date startTime = new Date();
		long startMiliSec = startTime.getTime();
		
		if (numPath > 0) {
			
			ArrayList<TSPPath> population = new ArrayList<TSPPath>(popSize+1);
			selectFromPopulationByRank(getRandomPathList(this.popSize*3), population, popSize);
			int iterationCount = 0;
	
			while (iterationCount < maxNumIteration) {
				reproduce(population); // Add offspring to population by reproduction
				mutate(population);    // mutation
				Collections.sort(population);
				iterationCount++;
			}
			System.out.printf("# of Iterations For Reproduction: %d\n", iterationCount);
			
			Date endTime = new Date();
			long endMiliSec = endTime.getTime();
			System.out.printf("Time Elapsed: %d (ms)\n", (endMiliSec-startMiliSec));
			
			if (numPath < population.size() && numPath > 0) {
				ArrayList<TSPPath> bestPathList = new ArrayList<TSPPath>(numPath);
				bestPathList.addAll(population.subList(0, numPath));
				return bestPathList;
			} else {
				return population;
			}
			
		}
		else 
			return null;
	}
	
	@Override
	public ArrayList<TSPPath> getBestPathList(int numPath) {
		return getBestPathList(numPath, 100);
	}

	@Override
	public TSPPath getBestPath() {
		return getBestPathList(1).get(0);
	}
	
	private ArrayList<TSPPath> getRandomPathList(int numPath) {
		if (this.randomSeed >=0)
			return TSPPath.getRandomPathList(numPath, g, this.randomSeed);
		else 
			return TSPPath.getRandomPathList(numPath, g);
	}
	
	private void selectFromPopulationByRank(ArrayList<TSPPath> pop, ArrayList<TSPPath> survivors, int numPath) {
		Collections.sort(pop);
		if (numPath < pop.size() && numPath > 0)
			survivors.addAll(pop.subList(0, numPath));
		else
			survivors.addAll(pop);
	}
	
	// Mutate the population (Not implemented yet)
	private void mutate(ArrayList<TSPPath> sortedPop) {
		
	}
	
	// The first half of the sorted population will 
	// do crossover to create new offsprings and replace the second half
	private void reproduce(ArrayList<TSPPath> sortedPop) {
		
		int halfSize = sortedPop.size()/2;
		for (int i=0; i<halfSize; i++)
			sortedPop.remove(halfSize);
		
		TSPPath path1 = null;
		TSPPath path2 = null;
		
		for (int i=0; i<halfSize; i+=2) {
			path1 = sortedPop.get(i);
			path2 = sortedPop.get(i+1);
			cxCrossover(path1, path2, sortedPop);
		}
	}
	
	
	
	// Cycle Operator Crossover
	// path1 and path2 will be mutated after the operation - cycleOperator
	private void cxCrossover(TSPPath path1, TSPPath path2, ArrayList<TSPPath> population) {
		if (path1 == null || path2 == null) {
			System.out.println("TSPPath for cxCrossover() is null\n");
			return;
		}
		int numVertex = path1.getNumVertex();
		if (numVertex != path2.getNumVertex() || !path1.isValid() || !path2.isValid()) {
			System.out.println("Invalid TSPPath for cxCrossover()\n");
			return;
		}
		
		int currentIndex = this.rnd.nextInt(numVertex-1);
		
		// debug
		//System.out.printf("startIndex=%d in cxCrossover()\n", currentIndex);
		
		Integer[] newPath1 = path1.getIntArray();
		Integer[] newPath2 = path2.getIntArray();
		
		int startVertex = newPath1[currentIndex];
		int nextIndex = path1.findIndexOfVertex(newPath2[currentIndex]);
		
		int tmpVertex;
		int count=0;
		while (count <= numVertex) {
			
			tmpVertex=newPath2[currentIndex];
			newPath2[currentIndex] = newPath1[currentIndex];
			newPath1[currentIndex] = tmpVertex;
			if (tmpVertex == startVertex) break;
			currentIndex = nextIndex;
			nextIndex = path1.findIndexOfVertex(newPath2[currentIndex]);
			count++;
		}
		
		// debug
		//System.out.printf("Count=%d in cxCrossover()\n", count);
		
		double tmpPathCost = TSPPath.getTSPPathCost(newPath1, g);
		population.add(new TSPPath(newPath1, g, tmpPathCost));
		tmpPathCost = TSPPath.getTSPPathCost(newPath2, g);
		population.add(new TSPPath(newPath2, g, tmpPathCost));
		
	}
	
	public long getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}
	
	public int getPopSize() {
		return popSize;
	}

	public void setPopSize(int popSize) {
		this.popSize = popSize;
	}
	
	private Graph g;
	private long randomSeed;
	private int popSize;
	private Random rnd;

}
