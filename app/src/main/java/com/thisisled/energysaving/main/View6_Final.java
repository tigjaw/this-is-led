package com.thisisled.energysaving.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class View6_Final extends ActivityListener {

	@Override
	public void onCreate_exclusive(Bundle savedInstanceState) {
		setContentView(R.layout.activity_final_view);
	}

	@Override
	public void onClick_exclusive(View v) {
		switch (v.getId()) {
			case R.id.request_info:
				request_info();
				break;
		}
	}

	private void request_info() {
		Uri uri = Uri.parse("http://www.thisisled.com/index.html");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	@Override
	protected void back_button_pressed() {
		System.out.println("Return to: Summary and Savings activity");
		finish();
	}

	@Override
	protected void next_button_pressed() {
		Intent intent = new Intent(View6_Final.this, View1_MainActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
	}

	@Override
	protected void define_exclusive_widgets() {
		next_button.setText(getResources().getString(R.string.home_button));
		// Set Click Listener for request_info TextView
		TextView request_info = (TextView) findViewById(R.id.request_info);
		request_info.setOnClickListener(this);
		request_info.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_final_view, menu);
		return true;
	}
}