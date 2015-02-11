package com.siad.blois.textmining;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Corpus
{
	private static Corpus instance;

	public static Corpus getInstance()
	{
		if (instance == null)
		{
			try
			{
				instance = new Corpus();
			} catch (IOException e)
			{
				System.out.println(e.getMessage());

				instance = null;
			}
		}

		return instance;
	}

	private List<String> positif;

	private List<String> negatif;

	private Corpus() throws IOException
	{
		positif = new ArrayList<String>();

		negatif = new ArrayList<String>();

		try
		{
			this.loadIn(this.positif, "sources/data/pos/");
			this.loadIn(this.negatif, "sources/data/neg/");
		} catch (Exception e)
		{
			System.out.println("Problem in Corpus.create:\n" + e.getMessage());
		}

	}

	private void loadIn(List<String> list, String path) throws IOException
	{
		File folder = new File(this.getRessourcesUrl() + "sources/data/pos/");

		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles)
		{
			list.add(new String(Files.readAllBytes(file.toPath())));
		}
	}

	private String getRessourcesUrl()
	{
		ClassLoader classLoader = getClass().getClassLoader();

		return classLoader.getResource("").getPath();
	}

	public List<String> getPositif()
	{
		return positif;
	}

	public void setPositif(List<String> positif)
	{
		this.positif = positif;
	}

	public List<String> getNegatif()
	{
		return negatif;
	}

	public void setNegatif(List<String> negatif)
	{
		this.negatif = negatif;
	}
}
