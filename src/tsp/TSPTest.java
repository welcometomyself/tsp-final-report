package tsp;

import graph.AdjMatrixUndirectedGraph;
import graph.GraphFactory;
import graph.TSPPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class TSPTest {

	public static void main(String[] args) throws IOException {
		// AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		
		// AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(100); // Given numVertex(cities) as input parameter
		 AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10,2);  // Given numVertex(cities) as input parameter
		
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(11, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(10, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(11, 0);  // Given numVertex(cities), seed as input parameter
	     //AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10, 0);  // Given numVertex(cities), seed as input parameter
		
		System.out.println("numVertex: " + g.getNumVertex());
		System.out.println("numEdges: " + g.getNumEdges());
		
		System.out.println("Edge Cost Adjacency Matrix:");
		g.printCostMatrix();
		System.out.println("");
		
		TSPBruteForce tspBruteForce = new TSPBruteForce(g);
		TSPRandomSelection tspRandomSelection = new TSPRandomSelection(g);
		tspRandomSelection.setRandomSeed(0);
		// TSPGeneticAlgorithm tspGeneticAlgorithm = new TSPGeneticAlgorithm(g);
		
		ArrayList<TSPPath> bestPathList;
		Iterator it;
		
		// Brute Force
		System.out.printf("The Best Routes Found By Brute Force:\n");
		bestPathList = tspBruteForce.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			((TSPPath)it.next()).printPath();
		}
		//System.out.println(tspBruteForce.getBestPath());
		System.out.println("");
		
		
		// Select the best XXX routes from a randomly generated sample pool
		System.out.printf("The Best Routes Found (Approximation By Random Selection and Ranking):\n");
		int sampleSize=100;
		System.out.printf("Sample Size: %d\n", sampleSize);
		tspRandomSelection.setSampleSize(sampleSize);
		bestPathList = tspRandomSelection.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			((TSPPath)it.next()).printPath();
		}
		//System.out.println(tspRandomSelection.getBestPath());
		System.out.println("");
		
		// Select the best XXX routes from a randomly generated sample pool
		sampleSize=1000;
		System.out.printf("The Best Routes Found (Approximation By Random Selection and Ranking):\n");
		System.out.printf("Sample Size: %d\n", sampleSize);
		tspRandomSelection.setSampleSize(sampleSize);
		bestPathList = tspRandomSelection.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			((TSPPath)it.next()).printPath();
		}
		//System.out.println(tspRandomSelection.getBestPath());
		System.out.println("");
		
		// Select the best XXX routes from a randomly generated sample pool
		sampleSize=10000;
		System.out.printf("The Best Routes Found (Approximation By Random Selection and Ranking):\n");
		System.out.printf("Sample Size: %d\n", sampleSize);
		tspRandomSelection.setSampleSize(sampleSize);
		bestPathList = tspRandomSelection.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			((TSPPath)it.next()).printPath();
		}
		//System.out.println(tspRandomSelection.getBestPath());
		System.out.println("");
		
		
	}

}
