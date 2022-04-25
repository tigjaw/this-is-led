package com.thisisled.energysaving.products;

import com.thisisled.energysaving.model.EnergyModel;
import com.thisisled.energysaving.model.Fitting;

import android.os.Parcel;
import android.os.Parcelable;

public class LED extends Lighting {

	/**
	 * LED default constructor.
	 */
	public LED() {
		super();
	}

	/**
	 * LED parameterised constructor.
	 */
	public LED(final String name, final double power_rating, final double price,
			final int lamp_life) {
		super(name, power_rating, price, lamp_life);
	}

	/**
	 * LED Parcelable parameterised constructor.
	 */
	public LED(Parcel in) {
		read_from_parcel(in);
	}

	@Override
	public double calculate_actual_power_consumption() {
		return power_rating;
	}
	
	@Override
	public double annual_replacement_cost(final EnergyModel model, final Fitting fitting) {
		return 0;
	}
	
	@Override
	public double calculate_number_of_annual_replacements(EnergyModel model) {
		return 0;
	}
	
	// ***** SETTERS & GETTERS *****//

	// ***** PARCELABLE implementation *****//

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator	CREATOR	= new Parcelable.Creator() {
		
		public LED createFromParcel(final Parcel in) {
			return new LED(in);
		}

		public LED[] newArray(final int size) {
			return new LED[size];
		}

	};

	@Override
	protected void read_from_parcel_exclusive(Parcel in) {
		// nothing
	}

	@Override
	protected void writeToParcel_exclusive(Parcel dest, int flags) {
		// nothing
	}

}