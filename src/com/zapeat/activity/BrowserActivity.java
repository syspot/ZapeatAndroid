package com.zapeat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.zapeat.util.Constantes;

public class BrowserActivity extends DefaultActivity {

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
		
		setTelaAtual(this);
		
	}

	private void initComponents() {
		this.btSair = (Button) findViewById(R.id.btSair);
		this.btConfiguracoes = (Button) findViewById(R.id.btConfig);
	}

	private void initListeners() {

		OnClickListener onClickSair = new OnClickListener() {
			@Override
			public void onClick(View v) {

				sair();
				
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
