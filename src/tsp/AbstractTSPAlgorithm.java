/*   
 *   AbstractTSPAlgorithm 
 *   Basic implementation of ITSPAlgorithm
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
 *   Joe Huang 2013/08/10
 *   
 */

package tsp;

import graph.Graph;
import graph.TSPPath;

import java.util.List;

public abstract class AbstractTSPAlgorithm implements ITSPAlgorithm {

	public final static int DEFAULT_MAXNUM_OF_BESTPATH = 5;
	private Graph graph;
	
	public AbstractTSPAlgorithm() {}
	public AbstractTSPAlgorithm(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	public TSPPath[] getBestPaths() {
		return getBestPaths(DEFAULT_MAXNUM_OF_BESTPATH);
	}
	
	@Override
	public TSPPath[] getBestPaths(int maxNumPath) {
		List<TSPPath> pathList= getBestPathList(maxNumPath);
		return pathList.toArray(new TSPPath[pathList.size()]);
	}

	@Override
	public List<TSPPath> getBestPathList() {
		return getBestPathList(DEFAULT_MAXNUM_OF_BESTPATH);
	}
	
	@Override
	public TSPPath getBestPath() {
		List<TSPPath> pathList = getBestPathList(1);
		if (pathList != null) return pathList.get(0);
		else return null;
	}
	
	@Override
	public Graph getGraph() {
		return this.graph;
	}

	@Override
	public void setGraph(Graph graph) {
		this.graph = graph;
	}

}
