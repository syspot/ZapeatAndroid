package com.zapeat.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zapeat.dao.ConfiguracaoDAO;
import com.zapeat.model.Configuracao;
import com.zapeat.model.Usuario;
import com.zapeat.util.Constantes;

public class ZapeatAuthActivity extends DefaultActivity {

	private Button autenticar;
	private EditText login;
	private EditText senha;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zapeat);
		this.initFields();
		this.initListeners();

	}

	private void initFields() {
		this.autenticar = (Button) super.findViewById(R.id.btAutenticar);
		this.login = (EditText) super.findViewById(R.id.login);
		this.senha = (EditText) super.findViewById(R.id.senha);
	}

	private void initListeners() {
		OnClickListener onclick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (validateFields())
					autenticar();

			}
		};

		this.autenticar.setOnClickListener(onclick);
	}

	private void autenticar() {

		Usuario usuario = new Usuario();
		usuario.setLogin(this.login.getText().toString());
		usuario.setSenha(this.senha.getText().toString());
		usuario.setId(1);

		SharedPreferences.Editor editor = getSharedPreferences(Constantes.Preferencias.PREFERENCE_DEFAULT, 0).edit();

		editor.putInt(Constantes.Preferencias.USUARIO_LOGADO, usuario.getId());
		
		editor.commit();

		Configuracao configuracao = new ConfiguracaoDAO().obter(usuario, this);

		Intent intentMain = null;

		if (configuracao == null) {

			intentMain = new Intent(this, ConfiguracaoActivity.class);

		} else {

			intentMain = new Intent(this, MonitoringActivity.class);

		}

		this.startActivity(intentMain);
		this.finish();

	}

	private boolean validateFields() {

		if (this.login.getText() == null || this.login.getText().length() == 0) {
			Toast.makeText(ZapeatAuthActivity.this, "É obrigatório o preenchimento do login !", Toast.LENGTH_LONG).show();
			return false;
		}

		if (this.senha.getText() == null || this.senha.getText().length() == 0) {
			Toast.makeText(ZapeatAuthActivity.this, "É obrigatório o preenchimento da senha !", Toast.LENGTH_LONG).show();
			return false;
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.zapeat, menu);
		return true;
	}
}
