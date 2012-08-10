package com.zapeat.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zapeat.dao.ConfiguracaoDAO;
import com.zapeat.exception.ApplicationException;
import com.zapeat.model.Configuracao;
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

	@SuppressLint({ "NewApi" })
	private void autenticar() {

		try {

			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
			StrictMode.setThreadPolicy(policy);

			Usuario usuario = new Usuario();
			usuario.setLogin(this.login.getText().toString());
			usuario.setSenha(this.senha.getText().toString());

			HttpClient httpclient = new DefaultHttpClient();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("login", usuario.getLogin()));
			nameValuePairs.add(new BasicNameValuePair("senha", usuario.getSenha()));
			HttpPost httppost = new HttpPost(Constantes.Http.URL_AUTH);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);

			if (response.getStatusLine().getStatusCode() != 200) {
				usuario = null;
			}

			usuario = readJson(response);

			if (usuario == null) {

				super.makeInfoMessage(this, "Usuário e/ou senha inválido(s)");

			} else {

				this.redirecionar(usuario);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private Usuario readJson(HttpResponse response) throws ApplicationException {

		Usuario usuario = null;

		try {

			StringBuilder builder = new StringBuilder();
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;

			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			String json = builder.toString();

			if ("".equals(json)) {
				return null;
			}

			JSONObject jsonObject = new JSONObject(builder.toString());

			if (jsonObject.getBoolean("logged")) {

				usuario = new Usuario();

				usuario.setId(jsonObject.getInt("id"));

				usuario.setNome(jsonObject.getString("nome"));
				
			}

		} catch (Exception ex) {
			throw new ApplicationException(ex);
		}
		return usuario;
	}

	private void redirecionar(Usuario usuario) {

		SharedPreferences.Editor editor = getSharedPreferences(Constantes.Preferencias.PREFERENCE_DEFAULT, 0).edit();

		editor.putInt(Constantes.Preferencias.USUARIO_LOGADO, usuario.getId());

		editor.commit();

		Configuracao configuracao = new ConfiguracaoDAO().obter(usuario, this);

		Intent intentMain = null;

		if (configuracao == null) {

			intentMain = new Intent(this, ConfiguracaoActivity.class);

		} else {

			intentMain = new Intent(this, BrowserActivity.class);

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

}
