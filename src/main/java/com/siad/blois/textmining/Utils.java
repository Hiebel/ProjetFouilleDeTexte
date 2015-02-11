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
}