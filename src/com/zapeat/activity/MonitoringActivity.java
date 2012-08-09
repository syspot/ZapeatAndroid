package com.zapeat.activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MonitoringActivity extends DefaultActivity {

	private final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0;

	private final long MINIMUM_TIME_BETWEEN_UPDATES = 1000;

	private Button localizar;

	private LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoring);
		this.initComponents();
		this.initListeners();

	}

	private void initComponents() {
		this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new ZapeatLocationListener());
		localizar = (Button) findViewById(R.id.localizador);
	}

	private void initListeners() {

		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCurrentLocation();

			}
		};

		localizar.setOnClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_monitoring, menu);
		return true;
	}

	private void showCurrentLocation() {

		Location location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (location != null) {
			String message = String.format("Localização atual \n Longitude: %1$s \n Latitude: %2$s", location.getLongitude(), location.getLatitude());
			Toast.makeText(MonitoringActivity.this, message, Toast.LENGTH_LONG).show();
		}

	}

	private class ZapeatLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			
		}

		public void onStatusChanged(String s, int i, Bundle b) {
		}

		public void onProviderDisabled(String s) {
		}

		public void onProviderEnabled(String s) {
		}

	}
}
