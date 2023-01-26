package com.agenda.agendadecontatosapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agenda.agendadecontatosapp.AppDatabase
import com.agenda.agendadecontatosapp.AtualizarUsuario
import com.agenda.agendadecontatosapp.dao.UsuarioDao
import com.agenda.agendadecontatosapp.databinding.ContatoItemBinding
import com.agenda.agendadecontatosapp.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ContatoAdapter(private val context: Context, private val listaUsuarios: MutableList<Usuario>):
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>(){

    //Metodo para CRIAR (sem visualizar) as visualizações (itens da nossa lista)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ContatoViewHolder(itemLista)
      }

    //Método para VISUALIZAR (Na tela) os itens criados.
    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
       holder.txtNome.text = listaUsuarios[position].nome
       holder.txtSobrenome.text = listaUsuarios[position].sobrenome
       holder.txtIdade.text = listaUsuarios[position].idade
       holder.txtCelular.text = listaUsuarios[position].celular

        //Criando evento de click no botão Atualizar.
        holder.btAtualizar.setOnClickListener {
            val intent = Intent(context, AtualizarUsuario::class.java)
            intent.putExtra("nome",listaUsuarios[position].nome)
            intent.putExtra("sobrenome",listaUsuarios[position].sobrenome)
            intent.putExtra("idade",listaUsuarios[position].idade)
            intent.putExtra("Celular",listaUsuarios[position].celular)
            intent.putExtra("uid",listaUsuarios[position].uid)
            context.startActivity(intent)
        }

        //Criando evento de clique no botão deletar
        holder.btDeletar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val usuario = listaUsuarios[position]
                val usuarioDao: UsuarioDao = AppDatabase.getInstance(context).usuarioDao()
                usuarioDao.deletar(usuario.uid)
                listaUsuarios.remove(usuario)
                //Notificar a lista para remover o usuario da lista geral em tempo real
                withContext(Dispatchers.Main){
                    notifyDataSetChanged()
                }

            }
        }

    }

    override fun getItemCount() = listaUsuarios.size


    inner class ContatoViewHolder(binding: ContatoItemBinding) : RecyclerView.ViewHolder(binding.root){

        val txtNome = binding.txtNome
        val txtSobrenome = binding.txtSobrenome
        val txtIdade = binding.txtIdade
        val txtCelular = binding.txtCelular
        val btAtualizar = binding.btAtualizar
        val btDeletar = binding.btDeletar

    }
}
