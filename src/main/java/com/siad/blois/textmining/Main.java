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

		Map<String, Integer> positifTokens = Utils.getTokens(corpus.getPositif(), "positif_token.txt");
		
		List<Word> positifWords = Utils.treeTagger(positifTokens);
		
		positifWords = Utils.setCategory(positifWords, '+');
		
		Map<String, Integer> negatifTokens = Utils.getTokens(corpus.getNegatif(), "negatif_token.txt");

		List<Word> negatifWords = Utils.treeTagger(negatifTokens);

		negatifWords = Utils.setCategory(negatifWords, '-');
	}
}