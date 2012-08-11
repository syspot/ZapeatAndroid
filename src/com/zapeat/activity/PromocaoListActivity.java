package com.zapeat.activity;

import java.util.List;

import com.zapeat.adapter.ListPromocaoAdapter;
import com.zapeat.dao.PromocaoDAO;
import com.zapeat.model.Promocao;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.widget.ListView;

public class PromocaoListActivity extends Activity {

	private ListView listViewPromocoes;
	private ListPromocaoAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocao_list);
        this.listViewPromocoes = (ListView) findViewById(R.id.list__promocoes);
		this.listViewPromocoes.setCacheColorHint(Color.TRANSPARENT);
		this.initPromocoes();
    }
    
	private void initPromocoes() {

		List<Promocao> promocoes = new PromocaoDAO().pesquisarTodas(getApplicationContext());

		this.adapter = new ListPromocaoAdapter(this, promocoes);

		this.listViewPromocoes.setAdapter(this.adapter);

	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_promocao_list, menu);
        return true;
    }
}
