package com.joao.netflixapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.joao.netflixapp.adapter.AdapterCategoria
import com.joao.netflixapp.api.Api
import com.joao.netflixapp.databinding.ActivityTelaPrincipalBinding
import com.joao.netflixapp.model.Categoria
import com.joao.netflixapp.model.Categorias
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TelaPrincipal : AppCompatActivity(){

    private lateinit var binding: ActivityTelaPrincipalBinding
    private lateinit var adapterCategoria: AdapterCategoria
    private val listaCategorias: MutableList<Categoria> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#000000")

        val recyclerViewFilmes = binding.recyclerViewFilmes
        recyclerViewFilmes.layoutManager = LinearLayoutManager(this)
        recyclerViewFilmes.setHasFixedSize(true)
        adapterCategoria = AdapterCategoria(this,listaCategorias)
        recyclerViewFilmes.adapter = adapterCategoria

        binding.txtSair.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, FormLogin::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Saiu dp App", Toast.LENGTH_SHORT).show()
        }
        //Configurar retrofit

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://stackmobile.com.br/")
            .build()
            .create(Api::class.java)
        retrofit.listaCategorias().enqueue(object : Callback<Categorias>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<Categorias>, response: Response<Categorias>){
                if(response.code() ==200){
                    response.body()?.let{
                        adapterCategoria.listaCategorias.addAll(it.categorias)
                        adapterCategoria.notifyDataSetChanged()

                        //Barra de carregamento das capas dos filmes
                        binding.containerProgressBar.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        binding.txtCarregando.visibility = View.GONE
                    }
                }
            }
            override fun onFailure(call: Call<Categorias>, t: Throwable){
                Toast.makeText(applicationContext,"Erro ao buscar todos os Filmes!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
