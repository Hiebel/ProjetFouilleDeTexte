package com.siad.blois.textmining;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.annolab.tt4j.TokenHandler;
import org.annolab.tt4j.TreeTaggerWrapper;
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

	public static Map<String, Integer> getTokens(List<String> list)
	{
		List<String> tokens = new ArrayList<String>();

		for (String entry : list)
		{
			tokens.addAll(Utils.tokenize(entry));
		}

		Set<String> uniqueToken = new HashSet<String>(tokens);
		Map<String, Integer> tokenMap = new HashMap<String, Integer>();

		for (String token : uniqueToken)
		{
			tokenMap.put(token,  Collections.frequency(tokens, token));
		}

		return tokenMap;
	}

	@SuppressWarnings("unchecked")
	public static List<Word> treeTagger(final HashMap<String, Integer> map)
	{	
		System.out.println("-------------TreeTagger-----------");
		
		final ArrayList<Word> listWord = new ArrayList<Word>();
		
		List<String> list = new ArrayList<String>();
		//HasMap to List
		for(String token : map.keySet())
			list.add(token);
		
		try{
			// Point TT4J to the TreeTagger installation directory. The executable is expected
			// in the "bin" subdirectory - in this example at "/opt/treetagger/bin/tree-tagger"
			
			ClassLoader classLoader = Utils.class.getClassLoader();

			System.setProperty("treetagger.home", classLoader.getResource("").getPath().substring(1)+"TreeTagger");
			
			TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();

			try 
			{
				tt.setModel(classLoader.getResource("").getPath().substring(1)+"TreeTagger/lib/english-utf8.par:iso8859-1");

				tt.setHandler(new TokenHandler<String>() 
						{
					public void token(String token, String pos, String lemma) {
						//System.out.println(token + "\t" + pos + "\t" + lemma);
						listWord.add(new Word(token, map.get(token), pos));
					}
						});
				tt.process(list);
			}
			finally {
				tt.destroy();
			}
		}catch(Exception e){

		}

		return listWord;
	}
}