package com.thisisled.energysaving.utility;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Logic {

	public static double parse_double(final String input) {
		double d = 0.00;
		try {
			d = Double.parseDouble(input);
		} catch (final NumberFormatException e) { // Report Problems
			System.out.println("Problem @ parse_int in LOGIC");
		}
		return d;
	}

	public static int parse_int(String input) {
		int i = 0;
		try {
			i = Integer.parseInt(input);
		} catch (NumberFormatException e) { // Report Problems
			System.out.println("Problem @ parse_int in LOGIC");
		}
		return i;
	}

	/**
	 * Parses a double as currency.
	 * 
	 * @param money (double)
	 * @return moneyString (String)
	 */
	public static String parse_as_currency(double money) {
		String moneyString = "";
		try {
			NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
			formatter.setRoundingMode(RoundingMode.HALF_EVEN);
			moneyString = formatter.format(money);
			System.out.println(moneyString);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem @ parse_as_currency in LOGIC");
		}
		return moneyString;
	}

	/**
	 * Parses an int as currency.
	 * 
	 * @param money (int)
	 * @return moneyString (String)
	 */
	public static String parse_as_currency(int money) {
		String moneyString = "";
		try {
			NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK);
			moneyString = formatter.format(money);
			System.out.println(moneyString);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem @ parse_as_currency in LOGIC");
		}
		return moneyString;
	}

	public static String round_to_two_decimals(double number) {
		String numberString = "";
		try {
			DecimalFormat df = new DecimalFormat("#");
			numberString = df.format(number);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Problem @ round_two_decimals in LOGIC");
		}
		return numberString;
	}

}