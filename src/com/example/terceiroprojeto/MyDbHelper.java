package com.example.terceiroprojeto;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDbHelper extends SQLiteOpenHelper {
	
	static final String DB_NAME = "treinaweb.db";
	static final int DB_VERSION = 2;
	static final String TABLE = "cadastro";
	static final String C_ID = "id";
	static final String C_NOME = "nome";
	static final String C_SEXO = "sexo";
	static final String C_IDADE = "idade";
	static final String C_TELEFONE = "telefone";
	
	public MyDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try{
			String sql = "create table " + TABLE + " (" + C_ID + " integer primary key autoincrement, "
					+ C_NOME + " text, " + C_SEXO + " text, " + C_IDADE + " text, " + C_TELEFONE + " text)";
			db.execSQL(sql);
		}catch(Exception e){
			Log.e("Error DbHelper", e.getMessage());
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		try{
			backup(db);
			
			db.execSQL("drop table if exists " + TABLE);
			onCreate(db);
			
			restore(db);
		}catch(Exception e){
			Log.e("Error DbHelper", e.getMessage());
		}

	}
	
	private List<ContentValues> dados;
	
	private void backup(SQLiteDatabase db){
		dados = new ArrayList<ContentValues>();
		try{
			Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
			
			try{
				if(cursor.moveToNext()){
					String nome = cursor.getString(1);
					String sexo = cursor.getString(2);
					String idade = cursor.getString(3);
					
					ContentValues values = new ContentValues();
					values.put(MyDbHelper.C_NOME, nome);
					values.put(MyDbHelper.C_IDADE, idade);
					values.put(MyDbHelper.C_SEXO, sexo);
					
					dados.add(values);		
					
				}
			}finally{
					cursor.close();
				}
				Log.i("DbHelper", "Backup do banco de dados realizado!");
			}catch(Exception e){
				Log.e("Error DbHelper", e.getMessage());
			}
	}
	
	private void restore(SQLiteDatabase db){
		try{
			for(ContentValues values : dados){
				db.insertOrThrow(MyDbHelper.TABLE, null, values);
			}
			
			Log.i("DbHelper", "Restore do banco de dados realizado!");
		}catch(Exception e){
			Log.e("Error DbHelper", e.getMessage());
		}
	}

}
