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
 *   Joe Huang 2013/1/12 
 *	 History:  
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

public class TSPRandomSelection extends AbstractTSPAlgorithm {
	
	public TSPRandomSelection(Graph g) {
		this(g, -1, 1000);
	}
	
	public TSPRandomSelection(Graph g, long randomSeed, int sampleSize) {
		super(g);
		this.randomSeed = randomSeed;
		this.sampleSize = sampleSize;
	}
	
	@Override
	public List<TSPPath> getBestPathList(int numPath) {
		
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
	
	private ArrayList<TSPPath> getRandomPathList(int numPath) {
		if (this.randomSeed >=0)
			return TSPPath.getRandomPathList(numPath, getGraph(), this.randomSeed);
		else 
			return TSPPath.getRandomPathList(numPath, getGraph());
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
	
	private long randomSeed;
	private int sampleSize;

}
