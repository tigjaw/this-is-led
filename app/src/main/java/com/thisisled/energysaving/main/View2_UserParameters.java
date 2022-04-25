package com.thisisled.energysaving.main;

import com.thisisled.energysaving.model.EnergyModel;
import com.thisisled.energysaving.utility.Logic;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class View2_UserParameters extends ActivityListener {

	@Override
	public void onCreate_exclusive(Bundle savedInstanceState) {
		setContentView(R.layout.activity_user_parameters_view);
	}

	@Override
	public void onClick_exclusive(View v) {

	}

	@Override
	protected void back_button_pressed() {
		System.out.println("Return to Main Menu");
		// Close this activity
		finish();
	}

	@Override
	protected void next_button_pressed() {
		System.out.println("Submitting Key Variable Data.");
		StringBuffer parser = new StringBuffer("Problems:");
		System.out.println("parser = " + parser);
		if (parse_inputs(parser)) {
			// create intent for next activity
			Intent next_intent = new Intent(this, View3_ExistingLighting.class);
			// pass Parcelable model to next activity
			next_intent.putExtra("energy_model", energy_model);
			// open View3_ExistingLighting activity
			System.out.println("Open Existing Lighting View");
			startActivityForResult(next_intent, 0);
		} else { // print error message via alert dialog
			System.out.println("Building Alert Dialog");
			new AlertDialog.Builder(this).setTitle("Problems").setMessage(parser).setPositiveButton(
					"Retry", null).show();
		}
	}

	/**
	 * Parses form inputs and returns an EnergyModel.
	 * 
	 * @param parser (String)
	 * @return energy_model (EnergyModel)
	 */
	protected boolean parse_inputs(StringBuffer parser) {
		boolean parses = false;
		// unit price of ELECTRICITY (RATE)
		final EditText electricity_rate_input = (EditText) findViewById(R.id.erate_edit);
		final double e_rate = Logic.parse_double(electricity_rate_input.getText().toString());
		// OPERATION HOURS per day
		final EditText operation_hours_input = (EditText) findViewById(R.id.hours_edit);
		final int op_hours = Logic.parse_int(operation_hours_input.getText().toString());
		// OPERATION DAYS per year
		final EditText operation_days_input = (EditText) findViewById(R.id.days_edit);
		final int op_days = Logic.parse_int(operation_days_input.getText().toString());
		// maintenance COST per hour
		final EditText cost_input = (EditText) findViewById(R.id.mcost_edit);
		final double cost = Logic.parse_double(cost_input.getText().toString());
		if (e_rate == 0) {
			parser.append("\n");
			parser.append("Unit price of Electricity must be above zero.");
		}
		if (op_hours == 0 || op_hours > 24) {
			parser.append("\n");
			parser.append("Operational Hours must be above zero and no more than 24.");
		}
		if (op_days == 0 || op_days > 365) {
			parser.append("\n");
			parser.append("Operational Days must be above zero and no more than 365.");
		}
		if (cost == 0) {
			parser.append("\n");
			parser.append("Maintenance cost per hour must be above zero.");
		}
		if (parser.toString().equals("Problems:")) {
			// create new energy model using form data
			energy_model = new EnergyModel(e_rate, op_hours, op_days, cost);
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
		getMenuInflater().inflate(R.menu.activity_user_parameters_view, menu);
		return true;
	}

}