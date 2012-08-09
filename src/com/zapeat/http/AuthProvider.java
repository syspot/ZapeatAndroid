package com.zapeat.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.zapeat.model.Usuario;
import com.zapeat.util.Constantes;

public class AuthProvider extends AsyncTask<Usuario, Void, String> {

	@Override
	protected String doInBackground(Usuario... params) {

		if (params == null || params.length == 0) {
			return Constantes.Http.Mensagens.VALIDACAO_USUARIO;
		}

		Usuario usuario = params[0];

		try {

			HttpClient httpclient = new DefaultHttpClient();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("login", usuario.getLogin()));
			nameValuePairs.add(new BasicNameValuePair("senha", usuario.getSenha()));
			HttpPost httppost = new HttpPost(Constantes.Http.URL_AUTH);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			return (String) response.getParams().getParameter(Constantes.Http.PARAMETRO_RETORNO);

		} catch (Exception ex) {
			return Constantes.Http.Mensagens.FALHA_AUTENTICACAO;
		}
	}
}
