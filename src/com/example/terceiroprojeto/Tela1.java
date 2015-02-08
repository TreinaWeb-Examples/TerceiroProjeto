package com.example.terceiroprojeto;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Tela1 extends Activity {
	MyDbHelper dbHelper;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela1);
		
		Button btn = (Button) findViewById(R.id.buttonSalvar);
		btn.setOnClickListener(mClickListener);
	}
	
	private View.OnClickListener mClickListener  = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TextView lblStatus = (TextView) findViewById(R.id.textViewStatus);
			EditText txtNome = (EditText) findViewById(R.id.editTextNome);
			RadioButton rbMasculino = (RadioButton) findViewById(R.id.radioMasculino);
			RadioButton rbFeminino = (RadioButton) findViewById(R.id.radioFeminino);
			EditText txtIdade = (EditText) findViewById(R.id.editTextIdade);
			
			String nome = txtNome.getText().toString();
			String idade = txtIdade.getText().toString();
			String sexo = "";
			
			if(rbMasculino.isChecked())
				sexo = "Masculino";
			else if(rbFeminino.isChecked())
				sexo = "Feminino";
			
			SaveDatabase(nome, sexo, idade);
			
			lblStatus.setText("Status: Dados salvos com sucesso");
			
		}
	};
	
	public void SaveDatabase(String nome, String sexo, String idade){
		try{
			dbHelper = new MyDbHelper(this);
			
			db = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(MyDbHelper.C_NOME, nome);
			values.put(MyDbHelper.C_IDADE, idade);
			values.put(MyDbHelper.C_SEXO, sexo);
			
		try{
			db.insertOrThrow(MyDbHelper.TABLE, null, values);
		}finally{
			db.close();
		}
		}catch(Exception e){
			Log.e("Error sqlite", e.getMessage());
		}
	}
}
