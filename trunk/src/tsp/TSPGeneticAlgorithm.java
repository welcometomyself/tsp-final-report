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
 *   Joe Huang 2013/1/13 Cycle Operator is used in cross over reproduction
 *   History:  
 *   2013/08/10 Do some refactorings
 *   
 */

package tsp;

import graph.Graph;
import graph.TSPPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class TSPGeneticAlgorithm extends AbstractTSPAlgorithm {
	
	
	public TSPGeneticAlgorithm(Graph g) {
		this(g, -1, 100);
	}
	
	public TSPGeneticAlgorithm(Graph g, long randomSeed, int popSize) {
		super(g);
		this.randomSeed = randomSeed;
		this.rnd = new Random(randomSeed);
		this.popSize = popSize;
	}
	
	@Override
	public List<TSPPath> getBestPathList(int maxNumPath) {
		return getBestPathList(maxNumPath, 1000);
	}
	
	public List<TSPPath> getBestPathList(int maxNumPath, int maxNumIteration) {
		Date startTime = new Date();
		long startMiliSec = startTime.getTime();
		
		if (maxNumPath > 0) {
			
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
			
			if (maxNumPath < population.size() && maxNumPath > 0) {
				ArrayList<TSPPath> bestPathList = new ArrayList<TSPPath>(maxNumPath);
				bestPathList.addAll(population.subList(0, maxNumPath));
				return bestPathList;
			} else {
				return population;
			}
			
		}
		else 
			return null;
	}
	
	
	private ArrayList<TSPPath> getRandomPathList(int numPath) {
		if (this.randomSeed >=0)
			return TSPPath.getRandomPathList(numPath, getGraph(), this.randomSeed);
		else 
			return TSPPath.getRandomPathList(numPath, getGraph());
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
		
		double tmpPathCost = TSPPath.getTSPPathCost(newPath1, getGraph());
		population.add(new TSPPath(newPath1, getGraph(), tmpPathCost));
		tmpPathCost = TSPPath.getTSPPathCost(newPath2, getGraph());
		population.add(new TSPPath(newPath2, getGraph(), tmpPathCost));
		
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
	
	private long randomSeed;
	private int popSize;
	private Random rnd;

}
