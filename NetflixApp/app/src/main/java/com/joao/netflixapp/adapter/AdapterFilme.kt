package com.joao.netflixapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joao.netflixapp.DetalhesFilme
import com.joao.netflixapp.databinding.CategoriaItemBinding
import com.joao.netflixapp.databinding.FilmeItemBinding
import com.joao.netflixapp.model.Filme

class AdapterFilme (private val context: Context, private val listaFilme: MutableList<Filme>):
    RecyclerView.Adapter<AdapterFilme.FilmeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val itemLista = FilmeItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return FilmeViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
       Glide.with(context).load(listaFilme[position].capa).centerCrop().into(holder.capa)

        holder.capa.setOnClickListener {
            val intent = Intent(context,DetalhesFilme::class.java)
            intent.putExtra("capa",listaFilme[position].capa)
            intent.putExtra("nome",listaFilme[position].nome)
            intent.putExtra("descricao",listaFilme[position].descricao)
            intent.putExtra("elenco",listaFilme[position].elenco)
            context.startActivity(intent)
        }

        //centerCrop é um comando para ajustar a imagem ao quadro principal
    }

    override fun getItemCount() = listaFilme.size

    inner class FilmeViewHolder(binding: FilmeItemBinding): RecyclerView.ViewHolder(binding.root){
    val capa = binding.capaFilme
    }

}