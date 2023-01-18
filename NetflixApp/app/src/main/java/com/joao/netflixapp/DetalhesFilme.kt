package com.joao.netflixapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.joao.netflixapp.databinding.ActivityDetalhesFilmeBinding

class DetalhesFilme : AppCompatActivity() {

    private lateinit var binding: ActivityDetalhesFilmeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesFilmeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#000000")

        val capa = intent.extras?.getString("capa")
        val nome = intent.extras?.getString("nome")
        val descricao = intent.extras?.getString("descricao")
        val elenco = intent.extras?.getString("elenco")

        //Populando imagem da Web (via URL) com Glide
        Glide.with(this).load(capa).centerCrop().into(binding.capaFilme)

        // Trazendo informações do Layout detalhes_filme (nome, descrição e elenco)
        binding.txtNome.setText(nome)
        binding.txtDescricao.text = "Descrição: $descricao"
        binding.txtElenco.text = "Elenco: $elenco"

    }
}