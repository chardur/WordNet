/**
 * 
 */
package A06WordNet;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.introcs.In;

/**
 * @author Charles Durfee
 *
 */
public class WordNet {
	
	private SAP mySAP;
	// synsets symbol table
	private ST<String, List<Integer>> synsetST;
	// integer to noun symbol table
	private ST<Integer, String> intnoun;

	// constructor takes the name of the two input files
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null)
			throw new IllegalArgumentException("argument to constructor is null");
		
		int graphlength = 0;
		
		// synsets symbol table
		synsetST = new ST<String, List<Integer>>();
		
		// integer to noun symbol table
		intnoun = new ST<Integer, String>();
		
		// synsetsFile data stream
		In inSyn = new In(synsets);
		while(inSyn.hasNextLine()){
			String strings = inSyn.readLine();
			String[] values = strings.split(",", strings.length());
			List<Integer> val = new ArrayList<Integer>();
			int intVal = Integer.parseInt(values[0]);
			String key[] = values[1].split(" ");
			
			for (int i = 0; i<key.length; i++){
			if (synsetST.contains(key[i])) synsetST.get(key[i]).add(intVal);
			else{
				val.add(intVal);
				synsetST.put(key[i], val);
			}
			
			intnoun.put(intVal, key[i]);
			
			graphlength++;
			}
			//System.out.println(graphlength);
			//System.out.println("key " + key +" Values " +val);
		}
		//System.out.println(synsetST.get("three"));
		
		// create a digraph
		Digraph myDigraph = new Digraph(graphlength);
				
		// hypernymsFile data stream
		In inHype = new In(hypernyms);
		while(inHype.hasNextLine()){
			String strings = inHype.readLine();
			String[] values = strings.split(",", strings.length());
			int v = Integer.parseInt(values[0]);
			for (int i = 1; i < values.length; i++) {
				myDigraph.addEdge(v, Integer.parseInt(values[i]));
			}
		}
		

		// create SAP				
		mySAP = new SAP(myDigraph);
		
		// check to see if digraph is a rooted DAG
		//System.out.println(mySAP.isRootedDAG());
		if (!mySAP.isRootedDAG())
		 throw new IllegalArgumentException("argument to constructor is not a rooted DAG");
	
	}

	// returns all WordNet nouns
	public Iterable<String> nouns() {
		
		
		return synsetST.keys();

	}

	// is the word a WordNet noun?
	public boolean isNoun(String word) {
		if (word == null)
			throw new IllegalArgumentException("argument to is null");
		
		
		return synsetST.contains(word);

	}

	// distance between nounA and nounB
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new IllegalArgumentException("argument to distance is null");

		if (!isNoun(nounA) || !isNoun(nounB))
		throw new IllegalArgumentException("argument to distance is not a wordnet noun");
		
		int dist = mySAP.length(synsetST.get(nounA), synsetST.get(nounB));

		return dist;

	}

	// a synset (second field of synsets.txt) that is the common ancestor of
	// nounA and nounB
	// in a shortest ancestral path (defined below)
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null)
			throw new IllegalArgumentException("argument to sap is null");

		if (!isNoun(nounA) || !isNoun(nounB))
		throw new IllegalArgumentException("argument to sap is not a wordnet noun");
		
		int ancestor = mySAP.ancestor(synsetST.get(nounA), synsetST.get(nounB));

		return intnoun.get(ancestor);

	}

	public static void main(String[] args) {
		
		String hypernymsFile = "C:/Users/Student/Google Drive/eclipse projects/A06 WordNet/src/A06WordNet/hypernyms.txt";
		String synsetsFile = "C:/Users/Student/Google Drive/eclipse projects/A06 WordNet/src/A06WordNet/synsets.txt";
		
		// test constructor
		WordNet myWordNet = new WordNet(synsetsFile, hypernymsFile);
		
		//test nouns
		//System.out.println(myWordNet.nouns());
		
		//test isnoun
		System.out.println(myWordNet.isNoun("jug"));
		
		// test distance
		System.out.println(myWordNet.distance("punk", "jug"));
		
		// test sap
		System.out.println(myWordNet.sap("worm", "bird"));
		
		
	}

}
