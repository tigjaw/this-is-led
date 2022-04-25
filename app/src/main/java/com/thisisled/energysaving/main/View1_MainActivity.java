package com.thisisled.energysaving.main;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class View1_MainActivity extends ActivityListener {

	@Override
	public void onCreate_exclusive(Bundle savedInstanceState) {
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onClick_exclusive(View v) {

	}

	@Override
	protected void back_button_pressed() {
		System.out.println("Closing Application");
		// Close this activity
		finish();
	}

	@Override
	protected void next_button_pressed() {
		System.out.println("Application Begun");
		// Open the 'User Parameters Input ' activity
		Intent next_intent = new Intent(this, View2_UserParameters.class);
		startActivityForResult(next_intent, 0);
	}

	@Override
	protected void define_exclusive_widgets() {
		EditText intro = (EditText) findViewById(R.id.intro_text);
		intro.setText(Html.fromHtml(getResources().getString(R.string.intro)));
		back_button.setText(getResources().getString(R.string.exit_button));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}