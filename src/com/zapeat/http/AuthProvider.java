package com.zapeat.http;

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
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.zapeat.exception.ApplicationException;
import com.zapeat.model.Usuario;
import com.zapeat.util.Constantes;

public class AuthProvider extends AsyncTask<Usuario, Void, Usuario> {

	@Override
	protected Usuario doInBackground(Usuario... params) {

		if (params == null || params.length == 0) {
			return null;
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

			if (response.getStatusLine().getStatusCode() != 200) {
				usuario = null;
			}

			usuario = readJson(response);

			return usuario;

		} catch (Exception ex) {
			return null;
		}
	}

	private Usuario readJson(HttpResponse response) throws ApplicationException {

		try {

			StringBuilder builder = new StringBuilder();
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			String line;

			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			JSONArray jsonArray = new JSONArray(builder.toString());

			JSONObject jsonObject;

			if (jsonArray != null && jsonArray.length() >= 0) {

				jsonObject = jsonArray.getJSONObject(0);

			}

		} catch (Exception ex) {
			throw new ApplicationException(ex);
		}
		return null;
	}
}
