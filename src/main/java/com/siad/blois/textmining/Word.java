package com.siad.blois.textmining;

public class Word {
	
	public String token;
	public int frequency;
	//classification grammaticale
	public String classification;
	//pos ou neg
	public char category;
	public Word(String token, int frequency, String classification) {
		super();
		this.token = token;
		this.frequency = frequency;
		this.classification = classification;
	}
	
	

}
