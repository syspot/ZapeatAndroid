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

import android.annotation.SuppressLint;

import com.zapeat.exception.ApplicationException;
import com.zapeat.model.Promocao;
import com.zapeat.model.Usuario;
import com.zapeat.util.Constantes;

public class AuthProvider {

	@SuppressLint({ "NewApi" })
	public Usuario autenticar(Usuario usuario) throws ApplicationException {

		Usuario user = null;

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

			user = readJson(response);

		} catch (Exception ex) {
			throw new ApplicationException(ex);
		}

		return user;

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

				usuario.setPromocoes(new ArrayList<Promocao>());

				JSONArray array = jsonObject.getJSONArray("promocoes");

				JSONObject jsonPromo = null;

				Promocao promocao = null;

				for (int i = 0; i < array.length(); i++) {

					jsonPromo = array.getJSONObject(i);

					promocao = new Promocao();

					promocao.setId(jsonPromo.getLong(Constantes.JsonProperties.ID));

					promocao.setLocalidade(jsonPromo.getString(Constantes.JsonProperties.LOCALIDADE));

					promocao.setLatitude(jsonPromo.getDouble(Constantes.JsonProperties.LATITUDE));

					promocao.setLongitude(jsonPromo.getDouble(Constantes.JsonProperties.LONGITUDE));

					promocao.setDescricao(jsonPromo.getString(Constantes.JsonProperties.DESCRICAO));

					usuario.getPromocoes().add(promocao);

				}

			}

		} catch (Exception ex) {
			throw new ApplicationException(ex);
		}
		return usuario;
	}
}
