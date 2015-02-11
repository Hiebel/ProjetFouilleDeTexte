package com.siad.blois.textmining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

	public static Map<String, Integer> getTokens(List<String> list, String fileName)
	{
		Map<String, Integer> tokenMap = new HashMap<String, Integer>();

		File file = new File(getRessourcesUrl() + fileName);

		if (file.exists())
		{
			BufferedReader input = null;

			try
			{
				input = new BufferedReader(new FileReader(file));

				String token = input.readLine();

				while (token != null)
				{
					String[] tokenParts = token.split(" ");

					tokenMap.put(tokenParts[0], Integer.valueOf(tokenParts[1]));

					token = input.readLine();
				}

				input.close();
			} catch (Exception e)
			{
				System.out.println("Problème lors de la récupération du fichier : " + fileName);
			}
		} else
		{
			List<String> tokens = new ArrayList<String>();

			for (String entry : list)
			{
				tokens.addAll(Utils.tokenize(entry));
			}

			Set<String> uniqueToken = new HashSet<String>(tokens);

			tokenMap = new HashMap<String, Integer>();

			BufferedWriter output = null;

			try
			{
				output = new BufferedWriter(new FileWriter(file));

				for (String token : uniqueToken)
				{
					int freq = Collections.frequency(tokens, token);

					tokenMap.put(token, freq);

					output.write(token + " " + freq + "\n");
				}

				output.close();
			} catch (Exception e)
			{
				System.out.println("Problème lors de la création du fichier : " + fileName);
			}
		}
		return tokenMap;
	}

	public static String getRessourcesUrl()
	{
		ClassLoader classLoader = Utils.class.getClassLoader();

		return classLoader.getResource("").getPath();
	}

	@SuppressWarnings("unchecked")
	public static List<Word> treeTagger(final Map<String, Integer> map)
	{
		System.out.println("-------------TreeTagger-----------");

		final List<Word> listWord = new ArrayList<Word>();

		List<String> list = new ArrayList<String>();
		// HasMap to List
		for (String token : map.keySet())
			list.add(token);

		try
		{
			// Point TT4J to the TreeTagger installation directory. The
			// executable is expected
			// in the "bin" subdirectory - in this example at
			// "/opt/treetagger/bin/tree-tagger"

			System.setProperty("treetagger.home", getRessourcesUrl() + "TreeTagger");

			TreeTaggerWrapper<String> tt = new TreeTaggerWrapper<String>();

			try
			{
				tt.setModel(getRessourcesUrl() + "TreeTagger/lib/english-utf8.par:iso8859-1");

				tt.setHandler(new TokenHandler<String>()
				{
					public void token(String token, String pos, String lemma)
					{
						// System.out.println(token + "\t" + pos + "\t" +
						// lemma);
						listWord.add(new Word(token, map.get(token), pos));
					}
				});
				tt.process(list);
			} finally
			{
				tt.destroy();
			}
		} catch (Exception e)
		{

		}
		for (Word string : listWord)
		{

			System.out.println(string.token + " : " + string.classification + " : " + string.frequency);
		}
		return listWord;
	}

	public static List<Word> setCategory(List<Word> words, char c)
	{
		for (Word word : words)
		{
			word.category = c;
		}

		return words;
	}

	public static List<Word> merge(List<Word> positifWords, List<Word> negatifWords)
	{
		List<Word> list = new ArrayList<Word>();
		
		list.addAll(positifWords);
		list.addAll(negatifWords);
		
		return list;
	}
}