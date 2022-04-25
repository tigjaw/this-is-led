package com.thisisled.energysaving.main;

import com.thisisled.energysaving.model.*;
import com.thisisled.energysaving.products.Bulb;
import com.thisisled.energysaving.utility.Logic;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;

public class View3_ExistingLighting extends ActivityListener {

	@Override
	public void onCreate_exclusive(Bundle savedInstanceState) {
		setContentView(R.layout.activity_existing_lighting_view);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	@Override
	public void onClick_exclusive(View v) {

	}

	@Override
	protected void back_button_pressed() {
		System.out.println("Return to: User Parameters activity");
		// Close this activity
		finish();
	}

	@Override
	protected void next_button_pressed() {
		StringBuffer parser = new StringBuffer("Problems:");
		if (parse_inputs(parser)) {
			// create intent for next activity
			final Intent next_intent = new Intent(this, View4_NewLighting.class);
			// pass Parcelable model to next activity
			next_intent.putExtra("energy_model", energy_model);
			// Open the 'User Parameters Input Activity' window
			System.out.println("Open New Lighting activity");
			startActivityForResult(next_intent, 0);
		} else { // print error message via alert dialog
			System.out.println("Building Alert Dialog");
			new AlertDialog.Builder(this).setTitle("Problems").setMessage(parser).setPositiveButton(
					"Retry", null).show();
		}
	}

	/**
	 * Parses form inputs and returns a fitting.
	 * 
	 * @param parser (StringBuffer)
	 * @return parses (boolean)
	 */
	protected boolean parse_inputs(StringBuffer parser) {
		boolean parses = false;
		// Luminaire Quantity
		final EditText luminaire_quantity_input = (EditText) findViewById(R.id.quantity_edit);
		final int lum_quantity = Logic.parse_int(luminaire_quantity_input.getText().toString());
		// Lamps per Luminaire
		final EditText lamps_per_luminaire_input = (EditText) findViewById(R.id.lamps_edit);
		final int lamps_per_lum = Logic.parse_int(lamps_per_luminaire_input.getText().toString());
		// Lamp Wattage
		final EditText wattage_input = (EditText) findViewById(R.id.watts_edit);
		final int wattage = Logic.parse_int(wattage_input.getText().toString());
		// Ballast / Transformer type
		final Spinner ballast_input = (Spinner) findViewById(R.id.ballast_spinner);
		final String ballast = ballast_input.getItemAtPosition(
				ballast_input.getSelectedItemPosition()).toString();
		// Lamp life, to L70
		final EditText lamp_life_input = (EditText) findViewById(R.id.life_edit);
		final int lamp_life = Logic.parse_int(lamp_life_input.getText().toString());
		// Lamp cost in £
		final EditText lamp_cost_input = (EditText) findViewById(R.id.lamp_cost_edit);
		final double lamp_cost = Logic.parse_double(lamp_cost_input.getText().toString());
		// Lamp replacement time (hours) as a double
		final EditText lamp_replacement_time_input = (EditText) findViewById(R.id.replacement_time_edit);
		final double lamp_replacement_time = Logic.parse_double(lamp_replacement_time_input.getText().toString());
		if (lum_quantity == 0) {
			parser.append("\n");
			parser.append("\n" + "Luminaire Quantity must be above zero.");
		}
		if (lamps_per_lum == 0) {
			parser.append("\n");
			parser.append("'Lamps per Luminaire' must be above zero.");
		}
		if (wattage == 0) {
			parser.append("\n");
			parser.append("Lamp Wattage must be above zero.");
		}
		if (lamp_life == 0) {
			parser.append("\n");
			parser.append("Lamp Life must be above zero.");
		}
		if (lamp_cost == 0) {
			parser.append("\n");
			parser.append("Lamp Cost must be above zero.");
		}
		if (lamp_replacement_time == 0) {
			parser.append("\n");
			parser.append("Lamp Replacement Time must be above zero.");
		}
		if (parser.toString().equals("Problems:")) {
			final Bulb bulb = new Bulb("Bulb", wattage, lamp_cost, lamp_life,
												ballast, lamp_replacement_time);
			final Fitting fitting = new Fitting(bulb, lum_quantity, lamps_per_lum);
			energy_model.setExisting_fitting(fitting); // set existing fitting
			parses = true;
		}
		return parses;
	}

	@Override
	protected void define_exclusive_widgets() {
		// nothing
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_existing_lighting_view, menu);
		return true;
	}

}