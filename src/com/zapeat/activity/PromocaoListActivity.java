package com.zapeat.activity;

import java.util.Iterator;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.zapeat.adapter.ListPromocaoAdapter;
import com.zapeat.dao.PromocaoDAO;
import com.zapeat.http.HttpUtil;
import com.zapeat.model.Promocao;

public class PromocaoListActivity extends DefaultActivity implements OnClickListener {

	private ListView listViewPromocoes;
	private ListPromocaoAdapter adapter;
	private Button btAtualizar;
	private Button btSair;
	private Button btWeb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_promocao_list);
		this.listViewPromocoes = (ListView) findViewById(R.id.list__promocoes);
		this.listViewPromocoes.setCacheColorHint(Color.TRANSPARENT);
		this.btAtualizar = (Button) findViewById(R.id.btAtualizarPromocao);
		this.btAtualizar.setOnClickListener(this);
		this.btSair = (Button) findViewById(R.id.btSairList);
		this.btWeb = (Button) findViewById(R.id.btWeb);
		this.initPromocoes();
		this.initListeners();
	}

	private void initPromocoes() {

		List<Promocao> promocoes = new PromocaoDAO().pesquisarTodas(getApplicationContext());

		this.adapter = new ListPromocaoAdapter(this, promocoes);

		this.listViewPromocoes.setAdapter(this.adapter);

	}

	private void initListeners() {

		OnClickListener sair = new OnClickListener() {

			@Override
			public void onClick(View v) {

				sair();

				Intent intent = new Intent(PromocaoListActivity.this, ZapeatAuthActivity.class);

				startActivity(intent);

				finish();

			}
		};

		this.btSair.setOnClickListener(sair);

		OnClickListener web = new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(PromocaoListActivity.this, BrowserActivity.class);

				startActivity(intent);

				finish();

			}
		};

		this.btWeb.setOnClickListener(web);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_promocao_list, menu);
		return true;
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
		StrictMode.setThreadPolicy(policy);

		List<Promocao> promocoesNovas = null;
		List<Promocao> promocoesAtuais = null;
		Promocao nova = null;
		PromocaoDAO dao = new PromocaoDAO();

		if (getUsuarioLogado() != null) {

			try {

				promocoesNovas = HttpUtil.pesquisarPromocoes(getUsuarioLogado());

				promocoesAtuais = dao.pesquisarTodas(getApplicationContext());

				Iterator<Promocao> iterador = promocoesNovas.iterator();

				while (iterador.hasNext()) {

					nova = iterador.next();

					if (promocoesAtuais.contains(nova)) {
						iterador.remove();
					}
				}

				dao.inserir(promocoesNovas, getApplicationContext());

				this.initPromocoes();

			} catch (Exception ex) {
				super.makeInfoMessage(this, "Ocorreu um erro ao atualizar as promoções, tente novamente mais tarde");
			}
		}

	}

}
