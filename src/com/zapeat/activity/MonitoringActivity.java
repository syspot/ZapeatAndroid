package com.zapeat.activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.zapeat.dao.ConfiguracaoDAO;
import com.zapeat.model.Configuracao;

public class MonitoringActivity extends DefaultActivity {

	private LocationManager locationManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoring);
		this.initComponents();
		
	}

	private void initComponents() {

		Configuracao config = new ConfiguracaoDAO().obter(super.getUsuarioLogado(), this);

		this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, -1, config.getDistancia(), new ZapeatLocationListener());

	}

	private class ZapeatLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {

			String message = "Fast Shop com 50% de desconto em todos os aparelhos!";
			Toast.makeText(MonitoringActivity.this, message, Toast.LENGTH_LONG).show();

		}

		public void onStatusChanged(String s, int i, Bundle b) {
		}

		public void onProviderDisabled(String s) {
		}

		public void onProviderEnabled(String s) {
		}

	}
}
