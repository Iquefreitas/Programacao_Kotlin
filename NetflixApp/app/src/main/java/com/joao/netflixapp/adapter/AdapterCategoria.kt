package com.joao.netflixapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joao.netflixapp.databinding.CategoriaItemBinding
import com.joao.netflixapp.model.Categoria
import com.joao.netflixapp.model.Filme

class AdapterCategoria(private val context: Context, val listaCategorias: MutableList<Categoria>):
    RecyclerView.Adapter<AdapterCategoria.CategoriaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val itemLista = CategoriaItemBinding.inflate(LayoutInflater.from(context),parent, false)
        return CategoriaViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        holder.titulo.text = listaCategorias[position].titulo

        val categoria = listaCategorias[position]

        holder.recycleViewFilmes.adapter = AdapterFilme(context,categoria.filmes)
        holder.recycleViewFilmes.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
    }

    override fun getItemCount() = listaCategorias.size

    inner class CategoriaViewHolder(binding: CategoriaItemBinding): RecyclerView.ViewHolder(binding.root){
        val titulo = binding.txtTitulo
        val recycleViewFilmes = binding.recycleViewFilmes

    }
}

