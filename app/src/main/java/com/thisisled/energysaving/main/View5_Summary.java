package com.thisisled.energysaving.main;

import com.thisisled.energysaving.model.Fitting;
import com.thisisled.energysaving.utility.Logic;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class View5_Summary extends ActivityListener {
	private TextView	cost_saving, power_saving, carbon_saving;
	private TextView	expected_life_old, expected_life_new;
	private TextView	annual_replacements_old, annual_replacements_new;
	private TextView	life_cost_saving, life_carbon_saving;

	@Override
	public void onCreate_exclusive(Bundle savedInstanceState) {
		setContentView(R.layout.activity_summary_view);
		
	}

	@Override
	public void onClick_exclusive(View v) {
		// do nothing
	}

	@Override
	protected void back_button_pressed() {
		System.out.println("Return to: New Lighting activity");
		finish();
	}

	@Override
	protected void next_button_pressed() {
		// create intent for next activity
		Intent next_intent = new Intent(this, View6_Final.class);
		// pass Parcelable model to next activity
		next_intent.putExtra("energy_model", energy_model);
		// Open the 'User Parameters Input Activity' window
		System.out.println("Open Final activity");
		startActivityForResult(next_intent, 0);
	}

	@Override
	protected void define_exclusive_widgets() {
		// Annual Savings
		cost_saving = (TextView) findViewById(R.id.cost_saving_text);
		power_saving = (TextView) findViewById(R.id.power_saving_text);
		carbon_saving = (TextView) findViewById(R.id.carbon_saving_text);
		// life expectancy
		expected_life_old = (TextView) findViewById(R.id.old_life_text);
		expected_life_new = (TextView) findViewById(R.id.new_life_text);
		// annual replacements
		annual_replacements_old = (TextView) findViewById(R.id.old_replacements_text);
		annual_replacements_new = (TextView) findViewById(R.id.new_replacements_text);
		// forecast life time saving
		life_cost_saving = (TextView) findViewById(R.id.life_saving_text);
		life_carbon_saving = (TextView) findViewById(R.id.life_carbon_text);
		fill_summary_fields();
	}
	
	private void fill_summary_fields() {
		try {
			// fill annual saving fields
			final double annual_cost_saving = energy_model.calculate_annual_cost_saving();
			cost_saving.setText(Logic.parse_as_currency(annual_cost_saving));
			final double annual_power_saving = energy_model.calculate_annual_power_saving();
			power_saving.setText(Logic.round_to_two_decimals(annual_power_saving));
			final double annual_carbon_saving = energy_model.calculate_annual_carbon_saving();
			carbon_saving.setText(Logic.round_to_two_decimals(annual_carbon_saving));
			// Fittings
			Fitting existing_fitting = energy_model.getExisting_fitting();
			Fitting new_fitting = energy_model.getNew_fitting();
			// fill life expectancy fields
			double expected_life = existing_fitting.calculate_expected_life(energy_model);
			expected_life_old.setText(Logic.round_to_two_decimals(expected_life));
			expected_life = new_fitting.calculate_expected_life(energy_model);
			expected_life_new.setText(Logic.round_to_two_decimals(expected_life));
			// fill annual replacements fields
			double annual_replacements = existing_fitting.calculate_number_of_annual_replacements(energy_model);
			System.out.println("annual_replacements = " + annual_replacements);
			annual_replacements_old.setText(Logic.round_to_two_decimals(annual_replacements));
			annual_replacements = new_fitting.calculate_number_of_annual_replacements(energy_model);
			annual_replacements_new.setText("<0");
			// fill life time saving fields
			double lifetime_cost_saving = energy_model.calculate_life_cycle_cost_saving();
			life_cost_saving.setText(Logic.parse_as_currency(lifetime_cost_saving));
			double lifetime_carbon_saving = energy_model.calculate_life_cycle_carbon_saving();
			life_carbon_saving.setText(Logic.round_to_two_decimals(lifetime_carbon_saving));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error @ fill_summary_fields in View5_Summary");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_summary_view, menu);
		return true;
	}

}