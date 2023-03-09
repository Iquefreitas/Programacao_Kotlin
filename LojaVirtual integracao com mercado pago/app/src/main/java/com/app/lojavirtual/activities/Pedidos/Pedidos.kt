package com.app.lojavirtual.activities.Pedidos

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.lojavirtual.R
import com.app.lojavirtual.adapter.Adapter.AdapterPedido
import com.app.lojavirtual.databinding.ActivityPedidosBinding
import com.app.lojavirtual.model.DB
import com.app.lojavirtual.model.Pedido

class Pedidos : AppCompatActivity() {

    lateinit var binding: ActivityPedidosBinding
    lateinit var adapterPedido: AdapterPedido
    var lista_pedidos: MutableList<Pedido> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Tratamento da RecycliView
        val recycler_pedidos = binding.recyclerPedidos

        //Definindo a forma de apresentação do Layout.
        recycler_pedidos.layoutManager = LinearLayoutManager(this)
        recycler_pedidos.setHasFixedSize(true)
        adapterPedido = AdapterPedido(this,lista_pedidos)
        recycler_pedidos.adapter = adapterPedido

        val db = DB()
        db.obterListaDePedidos(lista_pedidos,adapterPedido)

    }
}