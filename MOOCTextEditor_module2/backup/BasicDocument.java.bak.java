package document;

import java.util.List;

/** 
 * A naive implementation of the Document abstract class. 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class BasicDocument extends Document 
{
	/** Create a new BasicDocument object
	 * 
	 * @param text The full text of the Document.
	 */
	public BasicDocument(String text)
	{
		super(text);
	}
	
	
	/**
	 * Get the number of words in the document.
	 * "Words" are defined as contiguous strings of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z
	 * 
	 * @return The number of words in the document.
	 */
	@Override
	public int getNumWords()
	{
		//Implement this method.  See the Module 1 support videos 
	    // if you need help.
		return this.getTokens("[a-zA-Z]+").size();
		//return this.getTokens("[a-z]+|[()0-9]+").size();
	}
	
	/**
	 * Get the number of sentences in the document.
	 * Sentences are defined as contiguous strings of characters ending in an 
	 * end of sentence punctuation (. ! or ?) or the last contiguous set of 
	 * characters in the document, even if they don't end with a punctuation mark.
	 * 
	 * @return The number of sentences in the document.
	 */
	@Override
	public int getNumSentences()
	{
	    //Implement this method.  See the Module 1 support videos 
        // if you need help.
		int NumOfSentences = 0;
		NumOfSentences = this.getTokens("[^.!?]+").size();
		return NumOfSentences;
	}
	
	/**
	 * Get the number of sentences in the document.
	 * Words are defined as above.  Syllables are defined as:
	 * a contiguous sequence of vowels, except for an "e" at the 
	 * end of a word if the word has another set of contiguous vowels, 
	 * makes up one syllable.   y is considered a vowel.
	 * @return The number of syllables in the document.
	 */
	@Override
	public int getNumSyllables()
	{
	    //Implement this method.  See the Module 1 support videos 
        // if you need help.
		int SyllablesCount = 0;
		for (String word : this.getTokens("[a-zA-Z]+")) {
			SyllablesCount += this.countSyllables(word);
		}
		/*
		System.out.println("Syllables=[double]= " + this.countSyllables("double"));
		System.out.println("Syllables=[contiguous]= " + this.countSyllables("contiguous"));
		System.out.println("Syllables=[sleepy]= " + this.countSyllables("sleepy"));
		System.out.println("Syllables=[obvious]= " + this.countSyllables("obvious"));
		System.out.println("Syllables=[toga]= " + this.countSyllables("toga"));
		System.out.println("Syllables=[cave]= " + this.countSyllables("cave"));
		System.out.println("Syllables=[segue]= " + this.countSyllables("segue"));*/
		return SyllablesCount;
	}
	
	
	/* The main method for testing this class. 
	 * You are encouraged to add your own tests.  */
	public static void main(String[] args)
	{
		testCase(new BasicDocument("This is a test.  How many???  "
		        + "Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  "
		        + "(And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
	}
	
}
