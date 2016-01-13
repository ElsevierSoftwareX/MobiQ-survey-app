package com.csit.packages.mobilesurvey;

public class Cipher {
	
	private String sequence="";
	public Cipher(String word){
		sequence=word;
		
	}
	
	
	
	public String make (){
		
		sequence=sequence.replace("1", "P");
		sequence=sequence.replace("2", "R");
		sequence=sequence.replace("3", "O");
		sequence=sequence.replace("4", "J");
		sequence=sequence.replace("5", "M");
		sequence=sequence.replace("6", "E");
		sequence=sequence.replace("7", "B");
		sequence=sequence.replace("8", "I");
		sequence=sequence.replace("9", "C");
		sequence=sequence.replace("0", "Q");
		
		return sequence;
	}

}
