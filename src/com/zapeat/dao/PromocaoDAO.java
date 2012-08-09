package com.zapeat.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import com.zapeat.model.Promocao;

public class PromocaoDAO {

	public List<Promocao> pesquisar(Context context) {

		List<Promocao> promocoes = new ArrayList<Promocao>();

		DBUtil conexao = DBUtil.getInstance(context);
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		qb.setTables(DBUtil.Tabelas.PROMOCOES);
		qb.appendWhere("DATA_ANUNCIO < DATE('now') OR DATA_ANUNCIO IS NULL");
		
		String[] colunas = new String[] { "id", "descricao", "latitude", "longitude", "localidade" };
		Cursor cursor = qb.query(conexao.getReadableDatabase(), colunas, null, null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			promocoes.add(this.createPromocao(cursor));
			cursor.moveToNext();
		}
		
		DBUtil.close(conexao,cursor);
		
		return promocoes;

	}

	private Promocao createPromocao(Cursor cursor) {

		Promocao promocao = new Promocao();
		promocao.setId(cursor.getLong(0));
		promocao.setDescricao(cursor.getString(1));
		promocao.setLatitude(cursor.getString(2));
		promocao.setLongitude(cursor.getString(3));
		promocao.setLocalidade(cursor.getString(4));
		return promocao;

	}

}
