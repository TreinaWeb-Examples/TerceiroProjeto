package com.example.terceiroprojeto;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Tela2 extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela2);
		
		ReadDatabase();
	}
	
	private void ReadDatabase(){
		MyDbHelper dbHelper = null;
		SQLiteDatabase db = null;
		TextView txtNome = (TextView) findViewById(R.id.textViewNome);
		TextView txtSexo = (TextView) findViewById(R.id.textViewSexo);
		TextView txtIdade = (TextView) findViewById(R.id.textViewIdade);
		
		String nome = "";
		String sexo = "";
		String idade = "";
		
		try{
			dbHelper = new MyDbHelper(this);
			
			db = dbHelper.getReadableDatabase();
			
			try{
				Cursor cursor = db.query(MyDbHelper.TABLE, null, null, null, null, null, MyDbHelper.C_ID + " DESC");
				
				try{
					if(cursor.moveToNext()){
						nome = cursor.getString(1);
						sexo = cursor.getString(2);
						idade = cursor.getString(3);
					}
				}finally{
					cursor.close();
				}
			}catch(Exception e){
				Log.e("Erro sqlite: ", e.getMessage());
			}
		}catch(Exception e){
			Log.e("Error sqlite: ", e.getMessage());
		}
		finally{
			db.close();
		}
		
		txtNome.setText(nome);
		txtSexo.setText(sexo);
		txtIdade.setText(idade);
	}

}
