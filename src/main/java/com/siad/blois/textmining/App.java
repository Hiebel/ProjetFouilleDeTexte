package com.siad.blois.textmining;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Hello world!
 * 
 */
public class App
{
	public static void main(String[] args)
	{
		//Corpus corpus = Corpus.getInstance();
		/*
		List<String> positif = corpus.getPositif();
		
		for (String string : positif)
		{
			System.out.println(string);
		}
		*/
		String entry = "james l . brooks , one of the developers of the simpsons and director of broadcast news , returns to the big screen with this entertaining , if slightly flawed comedy . nicholson plays melvin udall , probably the most horrible person ever on the screen . he's racist , homophobic , and never has a good word to say to anyone . so , nobody talks to him , except waitress carol conelly ( t . v sitcom star hunt , who was last seen in twister , 1996 ) . naturally , udall , conelly and gay neighbor simon bishop ( kinnear ) who nicholson hates , all hit it off in the end . like good will hunting ( 1997 ) and titanic ( 1997 ) , even though the outcome is completely obvious , as good as it gets is an enjoyable , funny and warm comedy . nicholson is hilarious as melvin , churning out insults with superb relish . only nicholson could get away with the lines that melvin delivers . hunt is also good as waitress carol , and easily rises to the challenge of nicholson . there's also ( thankfully ) a bit of chemistry between them . kinnear , as the gay neighbor , seems to have a slightly underwritten role , he's more of a plot convience than a character . although his performance is good , his character just seems to exist to help melvin and carol come together . in fact , the scene stealer is simon's dog , who is funnier than nicholson . but then again , pets are always cute on screen . providing solid support is cuba gooding , jnr ( jerry maguire , 1996 ) and yeardly smith ( who is the voice of lisa simpsons in the simpsons ) although gooding isn't as good as is character in maguire , he is still fun . he overacts a little , but not so much as to be annoying . smith is also good , although she has a fairly small role . even director lawrence kasdan ( body heat , 1981 ) makes an appearance as a doctor . but this is primarily nicholsons film , and every scene he's in , he's steals it . he's character is so hateful , though , it's amazing that anyone talks to him at all , especially carol . and this is the films main problem . it's totally unbelievable that carol would ever consider liking melvin . she doesn't fall in love with him naturally , the film forces her to fall in love with him . also , melvins character seems to go too nice , too quickly . i would doubt anyone with a character like melvins would be able to turn back to a nice , loving person . it would take a helluva long time , much longer than this film would like to make out . brooks direction is good , though , if a bit average , but he usually manages to get an emotion out of the audience . he handles the comedy scenes better than the sentimental ones ( he tends to pile on to much schmaltz ) but generally he's good . there's also a nice soundtrack by veteran composer hans zimmer . but , generally , as good as it gets achieves what it sets out to do , which is to make the audience feel good by the end of the movie . the movie is a bit overlong , but nicholson is such good fun that the running time passes by pretty quickly . overall , as good as it gets is a fun movie , even though it may be unbelivable , and certainly worth seeing ( if just for jack nicholsons performance . ) not quite as good as it gets ( pardon the bad joke ) , but still good fun . ";
		
		try
		{
			Analyzer analyzer = new StandardAnalyzer();
			
			TokenStream stream = analyzer.tokenStream("", new StringReader(entry));
	
			stream.reset();
			
			List<String> luceneList = new ArrayList<String>();
	
			while (stream.incrementToken())
			{
				luceneList.add(stream.getAttribute(CharTermAttribute.class).toString());
				
				System.out.println(stream.getAttribute(CharTermAttribute.class).toString());
			}
	
			stream.close();
		} catch (Exception e)
		{
			System.out.println("Oops");
		}
	}
}
