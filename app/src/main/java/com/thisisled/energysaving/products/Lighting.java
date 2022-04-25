package com.thisisled.energysaving.products;

import com.thisisled.energysaving.model.EnergyModel;
import com.thisisled.energysaving.model.Fitting;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public abstract class Lighting implements Parcelable {
	protected String	name;
	protected double	power_rating;	// in Watts
	protected double	price;			// in £
	protected int		life_to_L70;	// in years

	public Lighting() {
		super();
	}

	public Lighting(final String name, final double power_rating, final double price, final int lamp_life) {
		super();
		this.name = name;
		this.power_rating = power_rating;
		this.price = price;
		this.life_to_L70 = lamp_life;
	}
	
	/**
	 * Lighting Parcelable parameterised constructor.
	 */
	public Lighting(final Parcel in) {
		read_from_parcel(in);
	}

	public abstract double calculate_actual_power_consumption();

	public abstract double annual_replacement_cost(final EnergyModel model, final Fitting fitting);
	
	public double calculate_number_of_annual_replacements(EnergyModel model) {
		double annual_replacements = 0;
		try { // LEDs should not have annual replacements
			Fitting old_fitting = model.getExisting_fitting();
			final int op_hours = model.getOperational_hours();
			final int op_days = model.getOperational_days();
			final double total_op_hours = op_hours * op_days;
			System.out.println("total_op_hours = " + total_op_hours);
			final double lamp_life = getLife_to_L70();
			System.out.println("lamp_life = " + lamp_life);
			final double total_lamp_quantity = old_fitting.calculate_total_lamp_quantity();
			System.out.println("total_lamp_quantity = " + total_lamp_quantity);
			annual_replacements = (total_op_hours / lamp_life) * total_lamp_quantity;
			System.out.println("annual_replacements = " + annual_replacements);
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("ERROR @ calculate_number_of_annual_replacements in FITTING");
		}
		return annual_replacements;
	}
	
	@Override
	public String toString() {
/*		String text = name + ":" + "\n";
		text += power_rating + " watts" + "\n";
		text += "Cost: £" + price + "\n";
		text += "Life to L70: " + life_to_L70 + "\n";*/
		String text = name + ":" + "\n";
		text += power_rating + "W" + "\n";
		text += "£" + price + "\n";
		text += life_to_L70 + "hrs" + "\n";
		return text;
	}

	// ***** SETTERS & GETTERS *****//

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public double getPower_rating() {
		return power_rating;
	}

	public void setPower_rating(final double power_rating) {
		this.power_rating = power_rating;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public int getLife_to_L70() {
		return life_to_L70;
	}

	public void setLife_to_L70(final int life_to_L70) {
		this.life_to_L70 = life_to_L70;
	}

	// ***** PARCELABLE implementation *****//
	
	protected final void read_from_parcel(Parcel in) {
		name = in.readString();
		power_rating = in.readDouble();
		price = in.readDouble();
		life_to_L70 = in.readInt();
		read_from_parcel_exclusive(in);
	}
	
	protected abstract void read_from_parcel_exclusive(Parcel in);

	@Override
	public final void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeDouble(power_rating);
		dest.writeDouble(price);
		dest.writeInt(life_to_L70);
		writeToParcel_exclusive(dest, flags);
	}
	
	protected abstract void writeToParcel_exclusive(Parcel dest, int flags);

	@Override
	public int describeContents() {
		return 0;
	}
}