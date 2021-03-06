package com.zapeat.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zapeat.dao.PromocaoDAO;
import com.zapeat.http.HttpUtil;
import com.zapeat.model.Usuario;
import com.zapeat.util.Constantes;

public class ZapeatAuthActivity extends DefaultActivity implements OnClickListener {

	private Button autenticar;
	private EditText login;
	private EditText senha;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zapeat);
		this.initFields();
	}

	private void initFields() {
		this.autenticar = (Button) super.findViewById(R.id.btAutenticar);
		this.login = (EditText) super.findViewById(R.id.login);
		this.senha = (EditText) super.findViewById(R.id.senha);
		this.autenticar.setOnClickListener(this);
	}

	public void onClick(View v) {

		if (v.getId() == R.id.btAutenticar && validateFields()) {
			autenticar();
		}

	}


	private void autenticar() {

		try {

			Usuario usuario = new Usuario();
			usuario.setLogin(this.login.getText().toString());
			usuario.setSenha(this.senha.getText().toString());

			usuario = HttpUtil.autenticar(usuario);

			if (usuario == null) {

				super.makeInfoMessage(this, "Usuário e/ou senha inválido(s)");

			} else {

				this.redirecionar(usuario);

				startMonitoring();

			}

		} catch (Exception ex) {
			makeInfoMessage(this, "Ocorreu uma falha na autenticação, tente novamente mais tarde");
		}

	}

	private void redirecionar(Usuario usuario) {

		SharedPreferences.Editor editor = getSharedPreferences(Constantes.Preferencias.PREFERENCE_DEFAULT, 0).edit();

		editor.putInt(Constantes.Preferencias.USUARIO_LOGADO, usuario.getId());

		editor.commit();

		PromocaoDAO promocaoDAO = new PromocaoDAO();

		promocaoDAO.clean(getApplicationContext());

		promocaoDAO.inserir(usuario.getPromocoes(), getApplicationContext());

		this.startActivity(new Intent(this, PromocaoListActivity.class));

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

}
