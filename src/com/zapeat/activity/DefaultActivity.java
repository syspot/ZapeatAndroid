package com.zapeat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;

import com.zapeat.model.Usuario;
import com.zapeat.util.Constantes;

public class DefaultActivity extends Activity {

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

}
