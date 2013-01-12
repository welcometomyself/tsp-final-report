package tsp;

import graph.AdjMatrixUndirectedGraph;
import graph.GraphFactory;
import graph.TSPPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class TSPTest {

	public static void main(String[] args) throws IOException {
		
		final long randomSeed = 0;
		
		//AdjMatrixDirectedGraph g = GraphFactory.readFromFile("graph2.txt");
		//AdjMatrixDirectedGraph g = GraphFactory.getRandomDirectedGraph(10, graphRandomSeed); // Given numVertex(cities) and random seed as input parameter
		AdjMatrixUndirectedGraph g = GraphFactory.getRandomUndirectedGraph(100, randomSeed);  // Given numVertex(cities) and random seed as input parameter
		
		System.out.println("randomSeed: " + randomSeed);
		System.out.println("Number Of Cities: " + g.getNumVertex());
		System.out.println("Number Of Edges: " + g.getNumEdges());
		
		System.out.println("Edge Cost Adjacency Matrix:");
		g.printCostMatrix();
		System.out.println("");
		
		TSPBruteForce tspBruteForce = new TSPBruteForce(g);
		TSPRandomSelection tspRandomSelection = new TSPRandomSelection(g);
		tspRandomSelection.setRandomSeed(randomSeed);
		// TSPGeneticAlgorithm tspGeneticAlgorithm = new TSPGeneticAlgorithm(g);
		
		ArrayList<TSPPath> bestPathList;
		Iterator it;
		
		// Select the best XXX routes from a randomly generated sample pool
		System.out.printf("The Best Routes Found (Approximation By Random Selection and Ranking):\n");
		int sampleSize=100;
		System.out.printf("Sample Size: %d\n", sampleSize);
		tspRandomSelection.setSampleSize(sampleSize);
		bestPathList = tspRandomSelection.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			// ((TSPPath)it.next()).printPath();
			((TSPPath)it.next()).printPathStartingFrom(0);
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
			// ((TSPPath)it.next()).printPath();
			((TSPPath)it.next()).printPathStartingFrom(0);
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
			// ((TSPPath)it.next()).printPath();
			((TSPPath)it.next()).printPathStartingFrom(0);
		}
		//System.out.println(tspRandomSelection.getBestPath());
		System.out.println("");
		
	
		// Brute Force
		System.out.printf("The Best Routes Found By Brute Force:\n");
		bestPathList = tspBruteForce.getBestPathList();
		it = bestPathList.iterator();
		while (it.hasNext()) {
			// ((TSPPath)it.next()).printPath();
			((TSPPath)it.next()).printPathStartingFrom(0);
		}
		//System.out.println(tspBruteForce.getBestPath());
		System.out.println("");
		
		
	}

}
