package com.zapeat.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.zapeat.util.Constantes;

public class BrowserActivity extends Activity {

	private Button btSair;
	private Button btConfiguracoes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser);
		WebView ecra = (WebView) findViewById(R.id.wvBrowser);
		ecra.setWebViewClient(new WebViewClient());
		ecra.loadUrl(Constantes.Http.URL_ZAPEAT);
		this.initComponents();
		this.initListeners();
	}

	private void initComponents() {
		this.btSair = (Button) findViewById(R.id.btSair);
		this.btConfiguracoes = (Button) findViewById(R.id.btConfig);
	}

	private void initListeners() {

		OnClickListener onClickSair = new OnClickListener() {
			@Override
			public void onClick(View v) {

				SharedPreferences.Editor editor = getSharedPreferences(Constantes.Preferencias.PREFERENCE_DEFAULT, 0).edit();

				editor.remove(Constantes.Preferencias.USUARIO_LOGADO);

				Intent intent = new Intent(BrowserActivity.this, ZapeatAuthActivity.class);

				startActivity(intent);

				finish();
			}
		};

		OnClickListener onClickConfiguracoes = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BrowserActivity.this, ConfiguracaoActivity.class);

				startActivity(intent);

				finish();

			}
		};

		this.btSair.setOnClickListener(onClickSair);

		this.btConfiguracoes.setOnClickListener(onClickConfiguracoes);
		
	}

}
