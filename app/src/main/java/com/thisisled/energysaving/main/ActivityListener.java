package com.thisisled.energysaving.main;

import com.thisisled.energysaving.model.EnergyModel;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Abstract, interface class developed as container for methods which are
 * required by more than one activity. Reduces code repetition.
 * 
 * @author Josh Woodyatt
 */
public abstract class ActivityListener extends Activity implements
		OnClickListener {
	protected EnergyModel	energy_model;
	protected Button			next_button;
	protected Button			back_button;

	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {// Get Energy Model from Existing Lighting extras
			final Bundle bundle = getIntent().getExtras();
			energy_model = bundle.getParcelable("energy_model");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (getResources().getBoolean(R.bool.portrait_only)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		onCreate_exclusive(savedInstanceState);
		define_shared_widgets(); // must be after onCreateExclusive
	}

	/**
	 * Override this, for us as onCreate method, for activity exclusive code.
	 * 
	 * @param savedInstanceState (Bundle)
	 */
	public abstract void onCreate_exclusive(Bundle savedInstanceState);

	/**
	 * Contains on click listeners that recur in every activity. Add unique on
	 * click listeners and actions to onClickExclusive method.
	 * 
	 * @param v (View)
	 */
	@Override
	public final void onClick(View v) {
		switch (v.getId()) {
			case R.id.back_button:
				back_button_pressed();
				break;
			case R.id.next_button:
				next_button_pressed();
				break;
		}
		onClick_exclusive(v);
	}

	/**
	 * Override me. Override this method in activity to apply on click listeners
	 * and actions that are not shared by other activities. For shared
	 * onClick-listeners and actions, see onClick method.
	 * 
	 * @param v (View)
	 */
	public abstract void onClick_exclusive(View v);

	/**
	 * Method for defining widgets which recur across every activity. Will return
	 * error if called before defineSharedWidgets().
	 */
	protected final void define_shared_widgets() {
		// Set Click Listener for back button
		back_button = (Button) findViewById(R.id.back_button);
		back_button.setOnClickListener(this);
		// Set Click Listener for next button
		next_button = (Button) findViewById(R.id.next_button);
		next_button.setOnClickListener(this);
		define_exclusive_widgets();
	}

	/**
	 * Override me. Method for defining widgets that are individual to an
	 * activity.
	 */
	protected abstract void define_exclusive_widgets();

	/**
	 * Override me. Define behaviour of the back button's onClick listener.
	 */
	protected abstract void back_button_pressed();

	/**
	 * Override me. Define behaviour of the next button's onClick listener.
	 */
	protected abstract void next_button_pressed();

	/**
	 * Override this method to parse inputs before allowing activities to open.
	 * 
	 * @param parser (StringBuffer)
	 * @return (boolean)
	 */
	protected boolean parse_inputs(StringBuffer parser) {
		// override me
		return true;
	}

	public final EnergyModel getEnergy_model() {
		return energy_model;
	}

	public final void setEnergy_model(EnergyModel energy_model) {
		this.energy_model = energy_model;
	}
}