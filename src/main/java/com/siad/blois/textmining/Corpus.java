package com.siad.blois.textmining;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

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
		
		//TODO récupérer tous les fichiers
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("sources" + File.separator + "data" + File.separator + "pos" + File.separator + "p_cv100_11528.txt");
		
		try
		{
			positif.add(new String(IOUtils.toString(inputStream).getBytes()));
		} finally
		{
			if(inputStream != null)
			{
				inputStream.close();
			}
		}
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
