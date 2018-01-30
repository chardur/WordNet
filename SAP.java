/**
 * 
 */
package A06WordNet;

import java.util.Arrays;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.introcs.In;

/**
 * @author Charles Durfee
 *
 */
public class SAP {

	// All Digraph operations take constant time (in the worst case) except
	// iterating over the vertices adjacent from a given vertex, which takes
	// time proportional to the number of such vertices.
	private Digraph graph;

	// The DirectedCycle constructor takes time proportional to V + E (in the
	// worst case)
	private DirectedCycle finder;

	// Runs in O(E + V) time.
	private Topological topological;

	// Runs in O(E + V) time.
	private BreadthFirstDirectedPaths pathV;
	private BreadthFirstDirectedPaths pathW;

	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G) {

		// set global graph to G
		graph = G;

	}

	// is the digraph a directed acyclic graph?
	public boolean isDAG() {

		finder = new DirectedCycle(graph);

		return !finder.hasCycle();
	}

	// is the digraph a rooted DAG?
	public boolean isRootedDAG() {

		topological = new Topological(graph);

		return topological.hasOrder();

	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {

		// if (v == null || w == null)
		// throw new IllegalArgumentException("argument to length is null");

		if (v < 0 || v > graph.V() - 1 || w < 0 || w > graph.V() - 1)
			throw new IndexOutOfBoundsException("argument to length is out of bounds");

		pathV = new BreadthFirstDirectedPaths(graph, v);
		pathW = new BreadthFirstDirectedPaths(graph, w);

		int ancestor = ancestor(v, w);

		int distance = -1;
		distance = pathV.distTo(ancestor) + pathW.distTo(ancestor);

		return distance;

	}

	// a common ancestor of v and w that participates in a shortest ancestral
	// path; -1 if no such path
	public int ancestor(int v, int w) {

		if (v < 0 || v > graph.V() - 1 || w < 0 || w > graph.V() - 1)
			throw new IndexOutOfBoundsException("argument to ancestor is out of bounds");

		// if (v == null || w == null)
		// throw new IllegalArgumentException("argument to length is null");

		pathV = new BreadthFirstDirectedPaths(graph, v);
		pathW = new BreadthFirstDirectedPaths(graph, w);

		int shortpath = Integer.MAX_VALUE;
		int commonAn = -1;

		for (int i = 0; i < graph.V(); i++) {
			if (pathV.hasPathTo(i) && pathW.hasPathTo(i) && pathV.distTo(i) + pathW.distTo(i) < shortpath) {

				// if there is a path and the path is the shortest, update vars
				shortpath = pathV.distTo(i) + pathW.distTo(i);
				commonAn = i;
			}
		}
		return commonAn;

	}

	// length of shortest ancestral path between any vertex in v and any vertex
	// in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w) {

		// if (v == null || w == null)
		// throw new IllegalArgumentException("argument to length is null");
		int distance = -1;
		// this will be the shortest distance from all the ints in the iterator
		int shortestIterableDistance = Integer.MAX_VALUE;
		for (int i : v) {
			for (int j : w) {
				distance = length(i, j);
				if (distance < shortestIterableDistance)
					shortestIterableDistance = distance;
			}
		}

		return shortestIterableDistance;
	}

	// a common ancestor that participates in shortest ancestral path; -1 if no
	// such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

		// if (v == null || w == null)
		// throw new IllegalArgumentException("argument to length is null");
		int commonAn = -1;
		int shortestIterableDistance = Integer.MAX_VALUE;
		
		for (int i : v) {
			for (int j : w) {
				if(length(i,j) < shortestIterableDistance){
					commonAn = ancestor(i, j);
					shortestIterableDistance = length(i,j);
				}
			}
		}
		
		return commonAn;
	}

	public static void main(String[] args) {

		String hypernymsFile = "C:/Users/Student/Google Drive/eclipse projects/A06 WordNet/src/A06WordNet/digraph1.txt";
		// hypernymsFile data stream
		In inHype = new In(hypernymsFile);
		// create a digraph
		Digraph myDigraph = new Digraph(inHype);

		// test constructor SAP
		SAP mySAP = new SAP(myDigraph);

		// check to see if digraph is DAG
		System.out.println("is dag? " + mySAP.isDAG());

		// check to see if digraph is a rooted DAG
		System.out.println("is rooted? " + mySAP.isRootedDAG());

		// check length
		System.out.println("Length " + mySAP.length(3, 11));

		// check common ancestor
		System.out.println("Common Ancestor " + mySAP.ancestor(3, 11));

		// iterable ancestor
		Integer[] array = new Integer[] { 3, 5 };
		Integer[] array2 = new Integer[] { 11 };

		Iterable<Integer> iterable = Arrays.asList(array);
		Iterable<Integer> iterable2 = Arrays.asList(array2);

		for (int i : iterable) {
			for (int j : iterable2) {
				System.out.println("iterable ancestor " + mySAP.ancestor(i, j) + " " + i + " " + j);
			}
		}

	}

}
