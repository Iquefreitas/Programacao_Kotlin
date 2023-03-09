package com.app.lojavirtual.adapter.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.lojavirtual.databinding.PedidoItemBinding
import com.app.lojavirtual.model.Pedido


class AdapterPedido(val context: Context, val lista_pedidos: MutableList<Pedido>):
    RecyclerView.Adapter<AdapterPedido.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val item_lista = PedidoItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return PedidoViewHolder(item_lista)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        holder.txtEndereco.text = lista_pedidos.get(position).endereco
        holder.txtCelular.text = lista_pedidos.get(position).celular
        holder.txtNome.text = lista_pedidos.get(position).nome
        holder.txtPreco.text = lista_pedidos.get(position).preco
        holder.txtTamanhoCalcado.text = lista_pedidos.get(position).tamanho_calcado
        holder.txtStatusPagamento.text = lista_pedidos.get(position).status_pagamento
        holder.txtStatusEntrega.text = lista_pedidos.get(position).status_entrega
    }

    override fun getItemCount() = lista_pedidos.size

    inner class PedidoViewHolder(binding: PedidoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val txtEndereco = binding.txtEndereco
        val txtCelular = binding.txtCelular
        val txtNome = binding.txtNomeProduto
        val txtPreco = binding.txtPrecoProduto
        val txtTamanhoCalcado = binding.txtTamanhoCalcado
        val txtStatusPagamento = binding.txtStatusPagamento
        val txtStatusEntrega = binding.txtStatusEntrega

    }
}