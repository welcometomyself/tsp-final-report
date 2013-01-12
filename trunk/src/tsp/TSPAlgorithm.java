package tsp;

import graph.TSPPath;

import java.util.ArrayList;

public interface TSPAlgorithm {
	public ArrayList<TSPPath> getBestPathList(int numPath);
	public TSPPath getBestPath();
}
