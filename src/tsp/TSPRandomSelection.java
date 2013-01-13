/*   
 *   TSPRandomSelection
 *   Solve Traveling Salesmen's Problem By Random Selection and Ranking of path cost
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
 *   2013/1/12 
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

public class TSPRandomSelection implements TSPAlgorithm {
	
	public static void main(String[] args) throws IOException {
		
		// AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		//AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10, 0); // Given numVertex(cities) and random seed as input parameter
		AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(20,0);  // Given numVertex(cities) and random seed as input parameter

		System.out.println("numVertex: " + g.getNumVertex());
		System.out.println("numEdges: " + g.getNumEdges());
		
		System.out.println("Edge Cost Adjacency Matrix:");
		g.printCostMatrix();
		System.out.println("");
		
		TSPRandomSelection tsp = new TSPRandomSelection(g);
		tsp.setRandomSeed(0);
		System.out.printf("The Best Routes Found (Approximation By Random Selection and Ranking):\n");
		ArrayList<TSPPath> bestPathList = tsp.getBestPathList(10);
		
		Iterator it = bestPathList.iterator();
		while (it.hasNext()) {
			// ((TSPPath)it.next()).printPath();
			((TSPPath)it.next()).printPathStartingFrom(0);
		}
		
	}
	
	public TSPRandomSelection(Graph g) {
		this(g, -1, 1000);
	}
	
	public TSPRandomSelection(Graph g, long randomSeed, int sampleSize) {
		this.g = g;
		this.randomSeed = randomSeed;
		this.sampleSize = sampleSize;
	}
	
	@Override
	public ArrayList<TSPPath> getBestPathList(int numPath) {
		
		Date startTime = new Date();
		long startMiliSec = startTime.getTime();
		
		if (numPath > 0) {
			ArrayList<TSPPath> population = getRandomPathList(this.sampleSize);
			ArrayList<TSPPath> survivors = new ArrayList<TSPPath>(numPath);
			selectFromPopulationByRank(population, survivors, numPath);
			Date endTime = new Date();
			long endMiliSec = endTime.getTime();
			System.out.printf("Time Elapsed: %d (ms)\n", (endMiliSec-startMiliSec));
			return survivors;
		}
		else 
			return null;
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
		if (numPath < pop.size() && numPath > 0) {
			survivors.addAll(pop.subList(0, numPath));
		} else {
			survivors.addAll(pop);
		}

	}
	
	public long getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
	}

	public int getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}
	
	private Graph g;
	private long randomSeed;
	private int sampleSize;

}
