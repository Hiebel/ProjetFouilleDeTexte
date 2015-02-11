package com.siad.blois.textmining;

import java.util.ArrayList;
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
		
		Map<String, Integer> positifTokens = Utils.getTokens(corpus.getPositif());
		Map<String, Integer> negatifTokens = Utils.getTokens(corpus.getNegatif());
		
		for (String string : positifTokens.keySet())
		{
			System.out.println(string + " : " + positifTokens.get(string));
		}
	}
}
