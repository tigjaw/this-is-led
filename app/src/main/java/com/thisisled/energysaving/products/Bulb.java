package com.thisisled.energysaving.products;

import com.thisisled.energysaving.model.EnergyModel;
import com.thisisled.energysaving.model.Fitting;

import android.os.Parcel;
import android.os.Parcelable;

public class Bulb extends Lighting {
	private Ballast	driver_type	= Ballast.Non_Used;
	private double		lamp_replacement_time;				// in hours

	public Bulb() {
		this.name = "T8 1200 x 600";
		this.power_rating = 36;
		this.price = 5;
		this.lamp_replacement_time = 0.5;
	}

	/**
	 * Parameterised constructor for Bulb, takes driver_type as Ballast enum. 
	 * @param name (String) e.g. T8 1200 x 600
	 * @param power_rating (double) e.g. 36
	 * @param price (double) e.g. £5.00
	 * @param lamp_life (int) e.g. 20000
	 * @param driver_type (Ballast) e.g. Switch
	 * @param lamp_replacement_time (double) e.g. 0.5
	 */
	public Bulb(final String name, final double power_rating, final double price, final int lamp_life,
			final Ballast driver_type, final double lamp_replacement_time) {
		super(name, power_rating, price, lamp_life);
		this.driver_type = driver_type;
		this.lamp_replacement_time = lamp_replacement_time;
	}
	
	/**
	 * Parameterised constructor for Bulb, takes driver_type as String. 
	 * @param name (String) e.g. T8 1200 x 600
	 * @param power_rating (double) e.g. 36
	 * @param price (double) e.g. £5.00
	 * @param lamp_life (int) e.g. 20000
	 * @param driver_type (String) e.g. Switch
	 * @param lamp_replacement_time (double) e.g. 0.5
	 */
	public Bulb(final String name, final double power_rating, final double price, final int lamp_life,
			final String driver_type, final double lamp_replacement_time) {
		super(name, power_rating, price, lamp_life);
		setDriver_type(driver_type);
		this.lamp_replacement_time = lamp_replacement_time;
	}

	/**
	 * Bulb Parcelable parameterised constructor.
	 * 
	 * @param in (Parcel)
	 */
	public Bulb(Parcel in) {
		read_from_parcel(in);
	}
	
	@Override
	public double calculate_actual_power_consumption() {
		double apc = 0;
		try {
			apc = power_rating * driver_type.getCode() + power_rating;
		} catch (final Exception e) {
			System.out.println("Error @ calculate_actual_power_consumption in BULB");
		}
		return apc;
	}
	
	public double annual_replacement_cost(final EnergyModel model, final Fitting fitting) {
		double replacement_cost = 0;
		try { // BASE_COST + (LAMP_REPLACEMENT_TIME X MAINTENANCE_COST)
			final double annual_replacements = calculate_number_of_annual_replacements(model);
			final double base_cost = annual_replacements * price;
			final double maintenance_cost = model.getMaintenance_cost();
			final double install_cost = lamp_replacement_time * annual_replacements * maintenance_cost;
			replacement_cost = base_cost + install_cost;
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Error @ annual_replacement_cost in BULB");
		}
		return replacement_cost;
	}
	
	@Override
	public String toString() {
		String text = name + ":" + "\n";
		text += power_rating + "W" + "\n";
		text += "£" + price + "\n";
		text += life_to_L70 + "hrs" + "\n";
		text += "Driver Type: " + driver_type.toString() + "\n";
		text += "Lamp Replacement time: " + lamp_replacement_time + " hrs\n";
		return text;
	}

	// ***** SETTERS & GETTERS *****//

	public Ballast getDriver_type() {
		return driver_type;
	}

	public void setDriver_type(Ballast driver_type) {
		this.driver_type = driver_type;
	}
	
	public void setDriver_type(String driver_type) {
		try {
			this.driver_type = Ballast.valueOf(driver_type);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error @ setDriver_type(String) in BULB");
		}
	}

	public void setLamp_replacement_time(double lamp_replacement_time) {
		this.lamp_replacement_time = lamp_replacement_time;
	}

	public double getLamp_replacement_time() {
		return lamp_replacement_time;
	}
	
	// ***** PARCELABLE implementation *****//
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator	CREATOR	= new Parcelable.Creator() {
		
		public Bulb createFromParcel(final Parcel in) {
			return new Bulb(in);
		}

		public Bulb[] newArray(final int size) {
			return new Bulb[size];
		}

	};
	

	@Override
	protected void read_from_parcel_exclusive(Parcel in) {
		driver_type = in.readParcelable(Ballast.class.getClassLoader());
		lamp_replacement_time = in.readDouble();
	}
	

	@Override
	protected void writeToParcel_exclusive(Parcel dest, int flags) {
		dest.writeParcelable(driver_type, flags);
		dest.writeDouble(lamp_replacement_time);
	}
}

enum Ballast implements Parcelable {
	Switch_Start(0.20), High_Frequency(0.11), Non_Used(0.0);

	private double	frequency;

	private Ballast(double f) {
		frequency = f;
	}

	public double getCode() {
		return frequency;
	}
	
	// ***** PARCELABLE implementation *****//

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(ordinal());
	}
	
   public static final Creator<Ballast> CREATOR = new Creator<Ballast>() {
      @Override
      public Ballast createFromParcel(final Parcel source) {
          return Ballast.values()[source.readInt()];
      }

      @Override
      public Ballast[] newArray(final int size) {
          return new Ballast[size];
      }
  };
}