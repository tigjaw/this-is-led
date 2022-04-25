package com.thisisled.energysaving.model;

import com.thisisled.energysaving.products.Lighting;

import android.os.Parcel;
import android.os.Parcelable;

public class Fitting implements Parcelable {
	private Lighting	lighting;
	private int			luminaire_quantity;
	private int			lamps_per_luminaire;
	private double		total_annual_energy_cost;
	private double		total_annual_carbon_use;
	private double		total_annual_power_consumption;
	private double		total_annual_cost;
	private double		annual_replacements;
	private double		expected_fitting_life;
	private double		fitting_cost;
	private double		life_cycle_cost;
	private double		life_cycle_carbon_use;
	
	/**
	 * Paramaterised constructor for Fitting.
	 * @param lighting
	 * @param luminaire_quantity
	 * @param lamps_per_luminaire
	 */
	public Fitting(Lighting lighting, final int luminaire_quantity, final int lamps_per_luminaire) {
		this.lighting = lighting;
		this.luminaire_quantity = luminaire_quantity;
		this.lamps_per_luminaire = lamps_per_luminaire;
	}
	
	/**
	 * Fitting Parcelable parameterised constructor.
	 */
	public Fitting(final Parcel in) {
		read_from_parcel(in);
	}

	/**
	 * Calculates total annual power consumption in kilowatt hours.
	 * 
	 * @param model (EnergyModel)
	 * @return total_annual_power_consumption (double)
	 */
	public double calculate_total_annual_power_consumption(EnergyModel model) {
		total_annual_power_consumption = 0;
		try {
			final double op_hours = model.getOperational_hours();
			final double op_days = model.getOperational_days();
			final double total_lamps = calculate_total_lamp_quantity();
			final double watts = lighting.calculate_actual_power_consumption();
			total_annual_power_consumption = (op_hours * op_days * total_lamps * watts) / 1000;
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("ERROR @ calculate_total_annual_power_consumption in FITTING");
		}
		return total_annual_power_consumption;
	}

	/**
	 * Calculates total annual energy cost in British pounds.
	 * 
	 * @param model (EnergyModel)
	 * @return total_annual_energy_cost (double)
	 */
	public double calculate_total_annual_energy_cost(EnergyModel model) {
		total_annual_energy_cost = 0;
		try {
			total_annual_energy_cost = calculate_total_annual_power_consumption(model)
												* model.getUnit_cost();
		} catch (final Exception e) {
			System.out.println("ERROR @ calculate_total_annual_energy_cost in FITTING");
		}
		return total_annual_energy_cost;
	}

	/**
	 * Calculates total annual carbon use in kilograms.
	 * 
	 * @param model (EnergyModel)
	 * @return total_annual_carbon_use (double)
	 */
	public double calculate_total_annual_carbon_use(EnergyModel model) {
		total_annual_carbon_use = 0;
		try {
			total_annual_carbon_use = calculate_total_annual_power_consumption(model) * 0.54522;
		} catch (final Exception e) {
			System.out.println("ERROR @ calculate_total_annual_carbon_use in FITTING");
		}
		return total_annual_carbon_use;
	}

	/**
	 * Calculates total annual cost in British pounds.
	 * 
	 * @param model (EnergyModel)
	 * @return total_annual_cost (double)
	 */
	public double calculate_total_annual_cost(final EnergyModel model) {
		total_annual_cost = 0;
		try {
			total_annual_cost = calculate_total_annual_energy_cost(model)
										+ lighting.annual_replacement_cost(model, this);
		} catch (final Exception e) {
			System.out.println("ERROR @ calculate_total_annual_cost in FITTING");
		}
		return total_annual_cost;
	}
	
	public double calculate_number_of_annual_replacements(EnergyModel model) {
		return lighting.calculate_number_of_annual_replacements(model);
	}

	public int calculate_total_lamp_quantity() {
		int total_lamp_quantity = 0;
		try {
			total_lamp_quantity = lamps_per_luminaire * luminaire_quantity;
		} catch (final Exception e) {
			System.out.println("Error @ calculate_total_lamp_quantity in FITTING");
		}
		return total_lamp_quantity;
	}

	public double calculate_expected_life(EnergyModel model) {
		expected_fitting_life = 0;
		try {
			final double lamp_life = lighting.getLife_to_L70();
			final int op_hours = model.getOperational_hours();
			final int op_days = model.getOperational_days();
			expected_fitting_life = lamp_life / (op_hours * op_days);
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Error @ calculate_expected_life in FITTING");
		}
		return expected_fitting_life;
	}

	/**
	 * Calculates expected fitting cost in British pounds.
	 * 
	 * @param model (EnergyModel)
	 * @return fitting_cost (double)
	 */
	public double calculate_fitting_cost() {
		fitting_cost = 0;
		try {
			fitting_cost = lighting.getPrice() * luminaire_quantity;
		} catch (final Exception e) {
			System.out.println("Error @ calculate_fitting_cost in FITTING");
		}
		return fitting_cost;
	}
	
	@Override
	public String toString() {
		String text = "Fitting:\n";
		try {
			text += lighting.toString();
			text += "Luminaire Quantity: " + luminaire_quantity + "\n";
			text += "Lamps per Luminaire: " + lamps_per_luminaire + "\n";
		} catch (Exception e) {
			System.out.println("Fittings not assinged yet.");
		}
		return text;
	}

	// ***** SETTERS & GETTERS *****//

	public Lighting getLighting() {
		return lighting;
	}

	public void setLighting(Lighting lighting) {
		this.lighting = lighting;
	}

	public int getLuminaire_quantity() {
		return luminaire_quantity;
	}

	public void setLuminaire_quantity(int luminaire_quantity) {
		this.luminaire_quantity = luminaire_quantity;
	}

	public int getLamps_per_luminaire() {
		return lamps_per_luminaire;
	}

	public void setLamps_per_luminaire(int lamps_per_luminaire) {
		this.lamps_per_luminaire = lamps_per_luminaire;
	}

	public double getTotal_annual_energy_cost() {
		return total_annual_energy_cost;
	}

	public void setTotal_annual_energy_cost(double energy_cost) {
		this.total_annual_energy_cost = energy_cost;
	}

	public double getTotal_annual_carbon_use() {
		return total_annual_carbon_use;
	}

	public void setTotal_annual_carbon_use(double carbon) {
		this.total_annual_carbon_use = carbon;
	}

	public double getTotal_annual_power_consumption() {
		return total_annual_power_consumption;
	}

	public void setTotal_annual_power_consumption(
			double total_annual_power_consumption) {
		this.total_annual_power_consumption = total_annual_power_consumption;
	}

	public void setTotal_annual_cost(double total_annual_cost) {
		this.total_annual_cost = total_annual_cost;
	}

	public double getTotal_annual_cost() {
		return total_annual_cost;
	}

	public double getAnnual_replacements() {
		return annual_replacements;
	}

	public void setAnnual_replacements(double annual_replacements) {
		this.annual_replacements = annual_replacements;
	}

	public double getFitting_cost() {
		return fitting_cost;
	}

	public void setFitting_cost(double fitting_cost) {
		this.fitting_cost = fitting_cost;
	}

	public void setExpected_fitting_life(double expected_fitting_life) {
		this.expected_fitting_life = expected_fitting_life;
	}

	public double getExpected_fitting_life() {
		return expected_fitting_life;
	}

	public void setLife_cycle_cost(double life_cycle_cost) {
		this.life_cycle_cost = life_cycle_cost;
	}

	public double getLife_cycle_cost() {
		return life_cycle_cost;
	}

	public void setLife_cycle_carbon_use(double life_cycle_carbon_use) {
		this.life_cycle_carbon_use = life_cycle_carbon_use;
	}

	public double getLife_cycle_carbon_use() {
		return life_cycle_carbon_use;
	}
	
	// ***** PARCELABLE implementation *****//

	private void read_from_parcel(Parcel in) {
		lighting = in.readParcelable(Lighting.class.getClassLoader());
		luminaire_quantity = in.readInt();
		lamps_per_luminaire = in.readInt();
		total_annual_energy_cost = in.readDouble();
		total_annual_carbon_use = in.readDouble();
		total_annual_power_consumption = in.readDouble();
		total_annual_cost = in.readDouble();
		annual_replacements = in.readDouble();
		expected_fitting_life = in.readDouble();
		fitting_cost = in.readDouble();
		life_cycle_cost = in.readDouble();
		life_cycle_carbon_use = in.readDouble();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(lighting, flags);
		dest.writeInt(luminaire_quantity);
		dest.writeInt(lamps_per_luminaire);
		dest.writeDouble(total_annual_energy_cost);
		dest.writeDouble(total_annual_carbon_use);
		dest.writeDouble(total_annual_power_consumption);
		dest.writeDouble(total_annual_cost);
		dest.writeDouble(annual_replacements);
		dest.writeDouble(expected_fitting_life);
		dest.writeDouble(fitting_cost);
		dest.writeDouble(life_cycle_cost);
		dest.writeDouble(life_cycle_carbon_use);		
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator	CREATOR	= new Parcelable.Creator() {
		
		public Fitting createFromParcel(final Parcel in) {
			return new Fitting(in);
		}

		public Fitting[] newArray(final int size) {
			return new Fitting[size];
		}

	};

}