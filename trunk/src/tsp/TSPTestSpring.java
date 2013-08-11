/*
 *  Test using Spring Framework to create concrete TSP Algorithm instances,
 *  the dependent graphs of TSP are also managed by Spring Context
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
 */


package tsp;

import graph.AdjMatrixUndirectedGraph;
import graph.GraphFactory;
import graph.TSPPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TSPTestSpring {

	public static void main(String[] args) throws IOException {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		ITSPAlgorithm tspBruteForce = (ITSPAlgorithm) context.getBean("tspBruteForce");
		ITSPAlgorithm tspNearestNeighbor = (ITSPAlgorithm) context.getBean("tspNearestNeighbor");
		ITSPAlgorithm tspRandomSelection = (ITSPAlgorithm) context.getBean("tspRandomSelection");
		ITSPAlgorithm tspGeneticAlgorithm = (ITSPAlgorithm) context.getBean("tspGeneticAlgorithm");
		
		// test
		ITSPAlgorithm tspGeneticAlgorithm2 = (ITSPAlgorithm) context.getBean("tspGeneticAlgorithm");
		if (tspGeneticAlgorithm == tspGeneticAlgorithm2)
			System.out.println("[TEST] The Same instance!!!");
			
		
		List<TSPPath> bestPathList;
		Iterator it;
		
		
		tspBruteForce.getGraph().printGraph();
		
		// Brute Force
		// if (g.getNumVertex()>100) return;
		System.out.printf("The Best Routes Found By Brute Force:\n");
		bestPathList = tspBruteForce.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			TSPPath path = ((TSPPath)it.next());
			path.printPathStartingFrom(0);
		}
		System.out.println("");
		
		// Select the best XXX routes from a randomly generated sample pool
		System.out.printf("The Best Routes Found (Approximation By Random Selection and Ranking):\n");
		// System.out.printf("Sample Size: %d\n", tspRandomSelection.getSampleSize());
		bestPathList = tspRandomSelection.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			TSPPath path = ((TSPPath)it.next());
			path.printPathStartingFrom(0);
		}
		System.out.println("");
		
		// Select the best XXX routes by Nearest Neighbor
		System.out.printf("The Best Routes Found (Approximation By Nearest Neighbor):\n");
		bestPathList = tspNearestNeighbor.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			TSPPath path = ((TSPPath)it.next());
			path.printPathStartingFrom(0);
		}
		System.out.println("");
		
		// Select the best XXX routes by Genetic Algorithm
		System.out.printf("The Best Routes Found (Approximation By Genetic Algorithm):\n");
		// System.out.printf("Population Size: %d\n", tspGeneticAlgorithm.getPopSize());
		bestPathList = tspGeneticAlgorithm.getBestPathList(3);
		it = bestPathList.iterator();
		while (it.hasNext()) {
			TSPPath path = ((TSPPath)it.next());
			path.printPathStartingFrom(0);
		}
		System.out.println("");
		
	}

}
