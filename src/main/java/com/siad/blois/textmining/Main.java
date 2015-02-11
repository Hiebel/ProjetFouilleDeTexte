package com.siad.blois.textmining;

import java.util.ArrayList;


/**
 * Hello world!
 * 
 */
public class Main
{
	public static void main(String[] args)
	{
		Corpus corpus = Corpus.getInstance();
		
		ArrayList<String> positifTokens = (ArrayList<String>) Utils.getTokens(corpus.getPositif());
		ArrayList<String> negatifTokens = (ArrayList<String>) Utils.getTokens(corpus.getNegatif());
	}
}
