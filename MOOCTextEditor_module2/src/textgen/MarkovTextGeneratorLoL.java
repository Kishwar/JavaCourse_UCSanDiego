package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		//Implement this method
		ListNode root, sel;
		starter = "";
		//check if wordList is empty. start by ""
		if(wordList.size() == 0) {
			root = new ListNode(starter);
			wordList.add(root);
		}
		String[] str = sourceText.split(" +");
		root = wordList.get(0);
		for(String w: str) {
			for(int c=0; c<wordList.size(); c++) {
				sel = wordList.get(c);
				if(sel.getWord().equals(starter)) {
					root = sel;
					break;
				}
			}
			if(root.getWord().equals(starter)) {
				root.addNextWord(w);
			}else {
				root = new ListNode(starter);
				root.addNextWord(w);
				wordList.add(root);
			}
			starter = w;
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    //Implement this method
		String Output = "";
		
		if(wordList.size() < 1)
			return Output;
		
		int NumOfWordsGen = 0;
		starter = "";
		ListNode root;
		while(NumOfWordsGen < numWords) {
			//set the root
			root = wordList.get(0);
			
			//find starter
			for(int c=0; c<wordList.size(); c++) {
				if(wordList.get(c).getWord().equals(starter)) {
					root = wordList.get(c);
					break;
				}
			}
			
			//we found starter..
			starter = root.getRandomNextWord(rnGenerator);
			if(!starter.equals("")) {
				if(Output.equals(""))
					Output = starter;
				else
					Output = Output + " " +  starter;
			}
			NumOfWordsGen++;
		}
		return Output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// Implement this method.
		clearList();
		train(sourceText);
	}
	
	// Add any private helper methods you need here.
	private void clearList() {
		if(wordList.size() > 0) {
			wordList.clear();
		}
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		/*
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "hi there hi Leo";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(4));
		*/
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
		int rn = generator.nextInt(this.nextWords.size());
	    return this.nextWords.get(rn);
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


