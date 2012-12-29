package graph;

import java.util.Set;
import java.util.TreeSet;

public class TSPPath implements java.lang.Comparable {

	/* 
	 * Note: Do NOT provide the final node in constructor's pathNodes,
	 * since the final destination(node) must be the same as the starting node for TSP,
	 * and this characteristic has been taken care of by this class.
	 */
	public TSPPath(Integer[] pathNodes, Graph g) {
		if (pathNodes == null) this.pathNodes =null;
		else this.pathNodes = pathNodes.clone(); // I don't want the modified pathNodes to influence this object
		this.g = g; // Modifications of graph can influence this object 
		this.isValid = _isValid();
		this.tspCost = Double.NEGATIVE_INFINITY;
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
	
	public Integer[] getIntArray() {
		return pathNodes.clone();
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
			else if (this.getCost() < ((TSPPath)tspPath).getCost()) return 0;
			else return 1;
		} else throw new ClassCastException("Given object is NOT an instance of TSPPath to compareTo().");
		
	}
	
	public void printPath() {
		System.out.println(this);
	}
	
	public void printPathNames() {
		System.out.println(this.toStringOfNodeNames());
	}
	
	public String toStringOfNodeNames() {
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
	
	private Integer[] pathNodes;
	private boolean isValid;
	private double tspCost;
	private Graph g;
	
}
