package com.zapeat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.zapeat.dao.ConfiguracaoDAO;
import com.zapeat.model.Configuracao;
import com.zapeat.model.Usuario;
import com.zapeat.util.Constantes;

public class DefaultActivity extends Activity {

	private LocationManager locationManager;
	private Context telaAtual;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void startMonitoring() {

		if (getUsuarioLogado() != null) {

			Configuracao config = new ConfiguracaoDAO().obter(getUsuarioLogado(), this);

			if (config != null) {
				this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, -1, config.getDistancia(), new ZapeatLocationListener());
			}

		}

	}

	public Usuario getUsuarioLogado() {

		SharedPreferences prefs = getSharedPreferences(Constantes.Preferencias.PREFERENCE_DEFAULT, 0);

		Usuario usuario = new Usuario();

		Integer id = prefs.getInt(Constantes.Preferencias.USUARIO_LOGADO, 0);

		if (id == 0) {
			return null;
		}

		usuario.setId(id);

		return usuario;

	}

	public void makeDefaultInfoMessage(Context context) {
		Toast.makeText(context, "Operação realizada com sucesso!", Toast.LENGTH_LONG).show();
	}

	public void makeInfoMessage(Context context, String message) {
		Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);

		t.setGravity(Gravity.CENTER, 0, 0);
		t.show();
	}

	private class ZapeatLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {

			String message = "Fast Shop com 50% de desconto em todos os aparelhos!";
			Toast.makeText(telaAtual, message, Toast.LENGTH_LONG).show();

		}

		public void onStatusChanged(String s, int i, Bundle b) {
		}

		public void onProviderDisabled(String s) {
		}

		public void onProviderEnabled(String s) {
		}

	}

	protected void setTelaAtual(Context context) {
		this.telaAtual = context;
	}

	@Override
	protected void onResume() {
		super.onResume();
		setTelaAtual(this);
		startMonitoring();
	}

	protected void sair() {
		
		SharedPreferences.Editor editor = getSharedPreferences(Constantes.Preferencias.PREFERENCE_DEFAULT, 0).edit();

		editor.remove(Constantes.Preferencias.USUARIO_LOGADO);

		editor.commit();

	}

}
