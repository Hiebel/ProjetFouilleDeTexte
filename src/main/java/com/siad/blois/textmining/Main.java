package com.siad.blois.textmining;

import java.util.List;
import java.util.Map;

/**
 * Hello world!
 * 
 */
public class Main
{
	public static void main(String[] args)
	{
		Corpus corpus = Corpus.getInstance();
		
		System.out.println(">>Positifs");

		Map<String, Integer> positifTokens = Utils.getTokens(corpus.getPositif(), "positif_token.txt");
		
		positifTokens = Utils.selectNumberOfFrequency(positifTokens);
		
		List<Word> positifWords = Utils.treeTagger(positifTokens);
		
		positifWords = Utils.setCategory(positifWords, '+');
		
		System.out.println(">>Négatifs");
		
		Map<String, Integer> negatifTokens = Utils.getTokens(corpus.getNegatif(), "negatif_token.txt");
		
		negatifTokens = Utils.selectNumberOfFrequency(negatifTokens);

		negatifTokens = Utils.selectNumberOfFrequency(negatifTokens);
		
		List<Word> negatifWords = Utils.treeTagger(negatifTokens);

		negatifWords = Utils.setCategory(negatifWords, '-');
		
		List<Word> words = Utils.merge(positifWords, negatifWords);
	}
}