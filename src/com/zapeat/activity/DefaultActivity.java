package com.zapeat.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.zapeat.model.Usuario;
import com.zapeat.util.Constantes;

public class DefaultActivity extends Activity {

	protected String ultimaAtualizacao;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

	protected void startMonitoring() {
		stopMonitoring();
		startService(new Intent(this, PollService.class));

	}

	protected void stopMonitoring() {
		stopService(new Intent(Constantes.Services.MONITORING));
	}

	protected void sair() {

		SharedPreferences.Editor editor = getSharedPreferences(Constantes.Preferencias.PREFERENCE_DEFAULT, 0).edit();

		editor.remove(Constantes.Preferencias.USUARIO_LOGADO);

		editor.commit();

		stopMonitoring();

	}

	protected void alterarUltimaAtualizacaoPromocoes() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		SharedPreferences.Editor editor = getSharedPreferences(Constantes.Preferencias.PREFERENCE_DEFAULT, 0).edit();

		editor.remove(Constantes.Preferencias.ULTIMA_ATUALIZACAO);

		editor.putString(Constantes.Preferencias.ULTIMA_ATUALIZACAO, dateFormat.format(new Date()));

		this.ultimaAtualizacao = dateFormat.format(new Date());

		editor.commit();
	}

}
