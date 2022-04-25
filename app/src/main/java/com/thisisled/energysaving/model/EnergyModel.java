package com.thisisled.energysaving.model;

import com.thisisled.energysaving.products.Lighting;

import android.os.Parcel;
import android.os.Parcelable;

public class EnergyModel implements Parcelable {
	private double		unit_cost;						// in £
	private int			operational_hours;			// per day
	private int			operational_days;			// per year
	private double		maintenance_cost;			// hourly rate in £

	private double		annual_cost_saving;			// in £
	private double		annual_carbon_saving;		// in kg
	private double		annual_power_saving;			// kwh
	// LC = Life Cycle
	private double		forecast_LC_cost_saving;	// Cost Saving in £
	private double		forecast_LC_carbon_saving; // Carbon Saving in KG

	private Fitting	existing_fitting;			// BULBs
	private Fitting	new_fitting;					// LEDs
	private Catalogue	catalogue;						// Contains all LED products

	public EnergyModel() {
		this.unit_cost = 0.12;
		this.operational_hours = 9;
		this.operational_days = 240;
		this.maintenance_cost = 35;
		this.catalogue = new Catalogue();
	}

	/**
	 * @param unit_cost (double) e.g. £0.12
	 * @param operational_hours (int) e.g. 9
	 * @param operational_days (int) e.g. 240
	 * @param maintenance_cost (double) e.g. 35
	 */
	public EnergyModel(double unit_cost, int operational_hours,
			int operational_days, double maintenance_cost) {
		this();
		this.unit_cost = unit_cost;
		this.operational_hours = operational_hours;
		this.operational_days = operational_days;
		this.maintenance_cost = maintenance_cost;
	}

	/**
	 * EnergyModel Parcelable parameterised constructor.
	 * @param in (Parcel)
	 */
	public EnergyModel(final Parcel in) {
		read_from_parcel(in);
	}
	
	/**
	 * Step 1: Calculates existing annual cost.<br>
	 * Step 2: Calculates new annual cost.<br>
	 * Step 3: Subtracts step 2 result from step 1 result. <br>
	 * Final Step: Stores and Returns annual_cost_saving.
	 * @return annual_cost_saving (double).
	 */
	public double calculate_annual_cost_saving() {
		annual_cost_saving = 0;
		try {
			final double existing_cost = existing_fitting.calculate_total_annual_cost(this);
			final double new_cost = new_fitting.calculate_total_annual_cost(this);
			annual_cost_saving = existing_cost - new_cost;
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Error @ calculate_total_annual_saving in EnergyModel");
		}
		return annual_cost_saving;
	}
	
	/**
	 * Step 1: Calculates existing annual power use.<br>
	 * Step 2: Calculates new annual power use.<br>
	 * Step 3: Subtracts step 2 result from step 1 result. <br>
	 * Final Step: Stores and Returns annual_power_saving.
	 * @return annual_power_saving (double).
	 */
	public double calculate_annual_power_saving() {
		annual_power_saving = 0;
		try {
			final double existing_cost = existing_fitting.calculate_total_annual_power_consumption(this);
			final double new_cost = new_fitting.calculate_total_annual_power_consumption(this);
			annual_power_saving = existing_cost - new_cost;
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Error @ calculate_annual_power_saving in EnergyModel");
		}
		return annual_power_saving;
	}
	
	/**
	 * Step 1: Calculates existing annual carbon use.<br>
	 * Step 2: Calculates new annual carbon use.<br>
	 * Step 3: Subtracts step 2 result from step 1 result. <br>
	 * Final Step: Stores and Returns annual_carbon_saving.
	 * @return annual_carbon_saving (double).
	 */
	public double calculate_annual_carbon_saving() {
		annual_carbon_saving = 0;
		try {
			final double existing_cost = existing_fitting.calculate_total_annual_carbon_use(this);
			final double new_cost = new_fitting.calculate_total_annual_carbon_use(this);
			annual_carbon_saving = existing_cost - new_cost;
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Error @ calculate_annual_carbon_saving in EnergyModel");
		}
		return annual_carbon_saving;
	}

	/**
	 * LIFE CYCLE COST COMPARISON (calculated over LED lifespan TO L70)
	 * 
	 * @return forecast_LC_cost_saving (double)
	 */
	public double calculate_life_cycle_cost_saving() {
		double existing_LCC = 0, new_LCC = 0;
		forecast_LC_cost_saving = 0;
		try {
			final double LED_life = new_fitting.getExpected_fitting_life();
			final double existing_annual_cost = existing_fitting.getTotal_annual_cost();
			final double new_annual_cost = new_fitting.getTotal_annual_cost();
			// Life-Cycle Cost = LCC
			existing_LCC = existing_annual_cost * LED_life;
			existing_fitting.setLife_cycle_cost(existing_LCC);
			new_LCC = new_annual_cost * LED_life;
			new_fitting.setLife_cycle_cost(new_LCC);
			// FORECAST COST SAVING OVER PERIOD OF LED LIFE EXPECTANCY
			forecast_LC_cost_saving = existing_LCC - new_LCC;
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Error @ calculate_life_cycle_cost_saving in EnergyModel");
		}
		return forecast_LC_cost_saving;
	}

	/**
	 * FORECAST REDUCTION IN CARBON USE OVER PERIOD OF LED LIFE EXPECTANCY (KG)
	 * 
	 * @return forecast_LC_carbon_saving (double)
	 */
	public double calculate_life_cycle_carbon_saving() {
		double existing_LCC = 0, new_LCC = 0;
		forecast_LC_carbon_saving = 0;
		try {
			final double LED_life = new_fitting.getExpected_fitting_life();
			final double existing_annual_carbon = existing_fitting.getTotal_annual_carbon_use();
			final double new_annual_carbon = new_fitting.getTotal_annual_carbon_use();
			// Life-Cycle Cost = LCC
			existing_LCC = existing_annual_carbon * LED_life;
			existing_fitting.setLife_cycle_carbon_use(existing_LCC);
			new_LCC = new_annual_carbon * LED_life;
			new_fitting.setLife_cycle_carbon_use(new_LCC);
			// FORECAST COST SAVING OVER PERIOD OF LED LIFE EXPECTANCY
			forecast_LC_carbon_saving = existing_LCC - new_LCC;
		} catch (final Exception e) {
			e.printStackTrace();
			System.out.println("Error @ calculate_life_cycle_carbon_saving in EnergyModel");
		}
		return forecast_LC_carbon_saving;
	}
	
	/**
	 * Get's Lighting product from Catalogue using name as parameter.
	 * 
	 * @param name (String)
	 */
	public Lighting get_lighting_by_name(String name) {
		return catalogue.get_lighting_by_name(name);
	}
	
	// ***** OTHER *****//
	@Override
	public String toString() {
		String text = "<----------EnergyModel.toString()---------->\n"
							+ "Energy Model, Key Variable Data:\n";
		text += "----------------------------------------\n";
		text += "Unit Cost of Electricity: £" + unit_cost + "\n";
		text += "Operational Hours: " + operational_hours + "\n";
		text += "Operational Hours: " + operational_days + "\n";
		text += "Maintenance Cost: £" + maintenance_cost + "\n";
		text += "----------------------------------------\n";
		text += "Old " + existing_fitting.toString();
		text += "----------------------------------------\n";
		text += "New " + new_fitting.toString();
		text += "</----------END EnergyModel.toString()---------->\n";
		return text;
	}

	// ***** PARCELABLE implementation *****//
	
	@Override
	public int describeContents() {
		return 0;
	}

	private void read_from_parcel(final Parcel in) {
		// key variable data
		unit_cost = in.readDouble();
		operational_hours = in.readInt();
		operational_days = in.readInt();
		maintenance_cost = in.readDouble();
		// savings
		annual_cost_saving = in.readDouble();
		annual_carbon_saving = in.readDouble();
		forecast_LC_cost_saving = in.readDouble();
		forecast_LC_carbon_saving = in.readDouble();
		// fittings
		existing_fitting = in.readParcelable(Fitting.class.getClassLoader());
		new_fitting = in.readParcelable(Fitting.class.getClassLoader());
		catalogue = in.readParcelable(Catalogue.class.getClassLoader());
	}

	@Override
	public void writeToParcel(final Parcel dest, final int flags) {
		/* We just need to write each field into the parcel. When we read from
		 * parcel, they will come back in the same order */
		// key variable data
		dest.writeDouble(unit_cost);
		dest.writeInt(operational_hours);
		dest.writeInt(operational_days);
		dest.writeDouble(maintenance_cost);
		// savings
		dest.writeDouble(annual_cost_saving);
		dest.writeDouble(annual_carbon_saving);
		dest.writeDouble(forecast_LC_cost_saving);
		dest.writeDouble(forecast_LC_carbon_saving);
		// fittings
		dest.writeParcelable(existing_fitting, flags);
		dest.writeParcelable(new_fitting, flags);
		dest.writeParcelable(catalogue, flags);
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator	CREATOR	= new Parcelable.Creator() {
		
		public EnergyModel createFromParcel(final Parcel in) {
			return new EnergyModel(in);
		}

		public EnergyModel[] newArray(final int size) {
			return new EnergyModel[size];
		}

	};

	// ***** SETTERS & GETTERS *****//
	
	// ***** SETTERS & GETTERS *****//

	public final double getUnit_cost() {
		return unit_cost;
	}

	public final void setUnit_cost(double unit_cost) {
		this.unit_cost = unit_cost;
	}

	public final int getOperational_hours() {
		return operational_hours;
	}

	public final void setOperational_hours(int operational_hours) {
		this.operational_hours = operational_hours;
	}

	public final int getOperational_days() {
		return operational_days;
	}

	public final void setOperational_days(int operational_days) {
		this.operational_days = operational_days;
	}

	public final double getMaintenance_cost() {
		return maintenance_cost;
	}

	public final void setMaintenance_cost(double maintenance_cost) {
		this.maintenance_cost = maintenance_cost;
	}

	public final double getAnnual_cost_saving() {
		return annual_cost_saving;
	}

	public final void setAnnual_cost_saving(double annual_cost_saving) {
		this.annual_cost_saving = annual_cost_saving;
	}

	public final double getAnnual_carbon_saving() {
		return annual_carbon_saving;
	}

	public final void setAnnual_carbon_saving(double annual_carbon_saving) {
		this.annual_carbon_saving = annual_carbon_saving;
	}

	public final double getAnnual_power_saving() {
		return annual_power_saving;
	}

	public final void setAnnual_power_saving(double annual_power_saving) {
		this.annual_power_saving = annual_power_saving;
	}

	public final double getForecast_LC_cost_saving() {
		return forecast_LC_cost_saving;
	}

	public final void setForecast_LC_cost_saving(double forecast_LC_cost_saving) {
		this.forecast_LC_cost_saving = forecast_LC_cost_saving;
	}

	public final double getForecast_LC_carbon_saving() {
		return forecast_LC_carbon_saving;
	}

	public final void setForecast_LC_carbon_saving(double forecast_LC_carbon_saving) {
		this.forecast_LC_carbon_saving = forecast_LC_carbon_saving;
	}

	public final Fitting getExisting_fitting() {
		return existing_fitting;
	}

	public final void setExisting_fitting(Fitting existing_fitting) {
		this.existing_fitting = existing_fitting;
	}

	public final Fitting getNew_fitting() {
		return new_fitting;
	}

	public final void setNew_fitting(Fitting new_fitting) {
		this.new_fitting = new_fitting;
	}

	public void setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}

	public Catalogue getCatalogue() {
		return catalogue;
	}

}