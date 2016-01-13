package com.csit.packages.mobilesurvey;

public class Cipher {
	
	private String sequence="";
	public Cipher(String word){
		sequence=word;
		
	}
	
	
	
	public String make (){
		
		sequence=sequence.replace("1", "c");
		sequence=sequence.replace("2", "7");
		sequence=sequence.replace("3", "4");
		sequence=sequence.replace("4", "n");
		sequence=sequence.replace("5", "g");
		sequence=sequence.replace("6", "3");
		sequence=sequence.replace("7", "t");
		sequence=sequence.replace("8", "h");
		sequence=sequence.replace("9", "i");
		sequence=sequence.replace("0", "s");
		
		return sequence;
	}

}
