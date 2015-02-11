package com.siad.blois.textmining;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class Utils
{
	static Analyzer analyzer;

	static
	{
		analyzer = new StandardAnalyzer();
	}

	public static List<String> tokenize(String entry)
	{
		TokenStream stream = null;

		List<String> luceneList = new ArrayList<String>();		

		try
		{
			stream = analyzer.tokenStream("", new StringReader(entry));
			
			stream.reset();

			while (stream.incrementToken())
			{
				luceneList.add(stream.getAttribute(CharTermAttribute.class).toString());
			}
			
			if (stream != null)
			{
				stream.close();
			}
			
		} catch (Exception e)
		{
			System.out.println("Problem in Utils.tokenize:\n" + e.getMessage());
		}
		
		return luceneList;
	}
	
	public static List<String> getTokens(List<String> list)
	{
		List<String> tokens = new ArrayList<String>();

		for (String entry : list)
		{
			tokens.addAll(Utils.tokenize(entry));
		}

		HashSet<String> hashSet = new HashSet<String>();
		hashSet.addAll(tokens);
		tokens.clear();
		tokens.addAll(hashSet);

		return tokens;
	}
}