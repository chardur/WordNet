/**
 * 
 */
package A06WordNet;

/**
 * @author max perrigo
 *
 */
public class Outcast {
	
	private WordNet word;
	private String outcast;
	private int distance;

	public Outcast(WordNet wordnet) {
		// constructor takes a WordNet object
		word = wordnet;;

	}

	public String outcast(String[] nouns) {
		// given an array of WordNet nouns, return an outcast
	      int curDistance = 0;
	      String curOutacast = nouns[0];
	      for (String thisNoun : nouns) {
	         int thisNounDistance = 0;
	         for (String thatNoun : nouns) {
	        	 
	            thisNounDistance += word.distance(thisNoun, thatNoun);
	         }
	         if (thisNounDistance > curDistance) {
	            curDistance = thisNounDistance;
	            curOutacast = thisNoun;
	         }
	      }
	      distance = curDistance;
	      outcast = curOutacast;
	      return outcast;
	}

	public static void main(String[] args) {
		String hypernymsFile = "C:/Users/Student/Google Drive/eclipse projects/A06 WordNet/src/A06WordNet/hypernyms.txt";
		String synsetsFile = "C:/Users/Student/Google Drive/eclipse projects/A06 WordNet/src/A06WordNet/synsets.txt";
		WordNet myWordNet = new WordNet(synsetsFile, hypernymsFile);
		
		Outcast outcast = new Outcast(myWordNet);
		String [] nouns = {"table","horse","zebra","cat","bear"};
		System.out.println(outcast.outcast(nouns));
	}

}
