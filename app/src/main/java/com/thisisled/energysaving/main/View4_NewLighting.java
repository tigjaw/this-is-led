package com.thisisled.energysaving.main;

import java.util.ArrayList;

import com.thisisled.energysaving.model.*;
import com.thisisled.energysaving.products.Lighting;
import com.thisisled.energysaving.utility.Logic;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class View4_NewLighting extends ActivityListener {
	private CheckBox					quantity_check;
	private EditText					quantity_edit;
	private EditText					lamps_edit;
	private Spinner					products_spinner;
	private ArrayList<Lighting>	products;
	private Lighting					selected_item;

	@Override
	public void onCreate_exclusive(Bundle savedInstanceState) {
		setContentView(R.layout.activity_new_lighting_view);
		products = populate_list();
	}

	private ArrayList<Lighting> populate_list() {
		Catalogue catalogue = energy_model.getCatalogue();
		products = catalogue.getProducts();
		return products;
	}

	@Override
	public void onClick_exclusive(View v) {
		if (v.getId() == R.id.quantity_check) {
			focus_inputs(quantity_check.isChecked());
		}
	}

	/**
	 * Focuses quantityEdit and lampsEdit depending on input and modifies their
	 * text data.
	 * 
	 * @param boxChecked (boolean)
	 */
	private void focus_inputs(boolean boxChecked) {
		if (!boxChecked) {
			quantity_edit.setFocusableInTouchMode(true);
			lamps_edit.setFocusableInTouchMode(true);
		} else {
			quantity_edit.setFocusable(false);
			lamps_edit.setFocusable(false);
			final Fitting fitting = energy_model.getExisting_fitting();
			final String quantity = "" + fitting.getLuminaire_quantity();
			quantity_edit.setText(quantity);
			final String lamps = "" + fitting.getLamps_per_luminaire();
			lamps_edit.setText(lamps);
		}
	}

	@Override
	protected void back_button_pressed() {
		System.out.println("Return to: Existing Lighting activity");
		// Close this activity
		finish();
	}

	@Override
	protected void next_button_pressed() {
		StringBuffer parser = new StringBuffer("Problems:");
		if (parse_inputs(parser)) {
			// create intent for next activity
			final Intent next_intent = new Intent(this, View5_Summary.class);
			// pass Parcelable model to next activity
			next_intent.putExtra("energy_model", energy_model);
			// Open the 'Summary' activity
			System.out.println("Calculating Savings");
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
	 * @return (boolean)
	 */
	protected boolean parse_inputs(StringBuffer parser) {
		boolean parses = false;
		// Luminaire Quantity input
		final int lum_quantity = Logic.parse_int(quantity_edit.getText().toString());
		// Lamps per Luminaire input
		final int lamps_per_lum = Logic.parse_int(lamps_edit.getText().toString());
		if (lum_quantity == 0) {
			parser.append("\n");
			parser.append("Luminaire Quantity must be above zero.");
		}
		if (lamps_per_lum == 0) {
			parser.append("\n");
			parser.append("'Lamps per Luminaire' must be above zero.");
		}
		if (parser.toString().equals("Problems:")) {
			create_new_fitting(lum_quantity, lamps_per_lum);
			parses = true; // if quantities parse correctly
		}
		return parses;
	}

	private void create_new_fitting(final int lum_quantity,
			final int lamps_per_lum) {

		Lighting led = (Lighting) products_spinner.getSelectedItem();

		Fitting ledFitting = new Fitting(led, lum_quantity, lamps_per_lum);
		energy_model.setNew_fitting(ledFitting);

		System.out.println(energy_model.toString());
	}

	@Override
	protected void define_exclusive_widgets() {
		// Set Click Listener for quantityCheck CheckBox
		quantity_check = (CheckBox) findViewById(R.id.quantity_check);
		quantity_check.setOnClickListener(<Lighting> this);
		// define edit texts for quantities and set unfocused
		quantity_edit = (EditText) findViewById(R.id.quantity_edit);
		lamps_edit = (EditText) findViewById(R.id.lamps_edit);
		// define product spinner
		products_spinner = (Spinner) findViewById(R.id.led_spinner);
		ArrayAdapter<Lighting> myAdapter = new ArrayAdapter<>(
																							this,
																							android.R.layout.simple_spinner_item,
																							products);
		products_spinner.setAdapter(myAdapter);
		setSelected_item((Lighting) products_spinner.getSelectedItem());

		products_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				selected_item = (Lighting) products_spinner.getItemAtPosition(arg2);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		focus_inputs(true);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.activity_new_lighting_view, menu);
		return true;
	}

	public void setSelected_item(Lighting selected_item) {
		this.selected_item = selected_item;
	}

	public Lighting getSelected_item() {
		return selected_item;
	}

}