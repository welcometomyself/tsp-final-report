/*   
 *   ITSPAlgorithm 
 *   Interface of Algorithms of Traveling Salesmen's Problem
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
 *   Joe Huang 2013/08/10 Do some refactorings
 *   
 */

package tsp;

import java.util.ArrayList;
import java.util.List;

import graph.Graph;
import graph.TSPPath;

public interface ITSPAlgorithm {
	
	// Return an array/list of best paths, 
	// the number of paths to return <= maxNumPath
	public List<TSPPath> getBestPathList(int maxNumPath);
	public TSPPath[] getBestPaths(int maxNumPath);
	
	// Return an array/list of best paths, 
	// the number of paths to return depend on the implementation
	public List<TSPPath> getBestPathList();
	public TSPPath[] getBestPaths();
	public TSPPath getBestPath();
	
	// Underlying graph of the algorithm
	public Graph getGraph();
	public void setGraph(Graph graph);
	
}
