package com.dbs.bgcp.util;

public class NumericUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	String number = "000123.00";
	
	String retvalu=removePreceedingZero(number);
	System.out.println(retvalu);
	}

	public static String removePreceedingZero(String number)
	{
		String numberWithoutZeros = number.replaceFirst("^0+", "");
		return numberWithoutZeros;
	}

	
}
