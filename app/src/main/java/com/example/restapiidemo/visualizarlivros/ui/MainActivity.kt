package com.example.restapiidemo.visualizarlivros.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.restapiidemo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.visualizar_livro)


        val extras = intent.extras
        if (extras != null) {
            var titulo = findViewById<TextView>(R.id.textView13)
            var descricao = findViewById<TextView>(R.id.textView24)
            var genero = findViewById<TextView>(R.id.textView25)
            var estado = findViewById<TextView>(R.id.textView26)
            var data_inicio = findViewById<TextView>(R.id.textView27)
            var data_fim = findViewById<TextView>(R.id.textView28)
            var paginas = findViewById<TextView>(R.id.textView29)
            var edicao = findViewById<TextView>(R.id.textView30)
            var lingua = findViewById<TextView>(R.id.textView31)
            var nota = findViewById<TextView>(R.id.textView32)
            var emprestar = findViewById<TextView>(R.id.textView33)

            titulo.text=extras.getString("Titulo");
            descricao.text=extras.getString("descricao");
            genero.text=extras.getString("genero");
            estado.text=extras.getString("estado");
            data_inicio.text=extras.getString("data_inicio");
            data_fim.text=extras.getString("data_fim");
            paginas.text=extras.getString("paginas");
            edicao.text=extras.getString("edicao");
            lingua.text=extras.getString("lingua");
            nota.text=extras.getString("nota");
            emprestar.text=extras.getString("emprestar");

            supportActionBar?.title =extras.getString("Titulo");
        }



    }
}