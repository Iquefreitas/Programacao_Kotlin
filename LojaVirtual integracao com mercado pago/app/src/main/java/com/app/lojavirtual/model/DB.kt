package com.app.lojavirtual.model

import android.util.Log
import android.widget.TextView
import com.app.lojavirtual.adapter.Adapter.AdapterPedido
import com.app.lojavirtual.adapter.Adapter.AdapterProduto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

//Banco de dados FireBase

class DB {

    fun salvarDadosUsuario(nome: String){

        //Criação de um identificador unico, que nesse caso vai ser o UID do usuário.
        val usuarioID = FirebaseAuth.getInstance().currentUser!!.uid

        //Variável para recuperar a instância do banco de dados
        val db = FirebaseFirestore.getInstance()

        //Mandar objeto (nome) para nosso banco de dados
        val usuarios = hashMapOf(

            //Lista dos dados que queira salva, ex: Nome, CPF e etc..
            "nome" to nome
        )

        //Documento de referênbcia que vai receber o banco de dados (Primeira tabela)
        val documentReference: DocumentReference = db.collection("Usuarios").document(usuarioID)

        //Gerar log e mensagem de conclusão dos dados caso positivo ou negativo.
        documentReference.set(usuarios).addOnSuccessListener {
            Log.d("DB", "Sucesso ao salvar os dados!")
        }.addOnFailureListener{ erro ->
            Log.d("DB_ERROR", "Erro ao salvar os dados! ${erro.printStackTrace()}")
        }
    }

    //Método para recuperar dados do perfil
    fun recuperarDadosDoPerfil(nomeUsuario: TextView, emailUsuario: TextView){
        val usuarioID = FirebaseAuth.getInstance().currentUser!!.uid
        val email = FirebaseAuth.getInstance().currentUser!!.email
        val db = FirebaseFirestore.getInstance()

        val documentReference: DocumentReference = db.collection("Usuarios").document(usuarioID)
        documentReference.addSnapshotListener { documento, error ->
            if (documento != null){
                nomeUsuario.text = documento.getString("nome")
                emailUsuario.text = email
            }
        }
    }
    //Método para obter a lista de produtos do Storage
    fun obterListaDeProdutos(lista_produtos: MutableList<Produto>, adapter_produto: AdapterProduto){

        val db = FirebaseFirestore.getInstance()
        db.collection("Produtos").get()
            .addOnCompleteListener { tarefa ->
                if (tarefa.isSuccessful){
                    for (documento in tarefa.result!!){
                        val produto = documento.toObject(Produto::class.java)
                        lista_produtos.add(produto)
                        adapter_produto.notifyDataSetChanged()
                    }
                }
            }

    }
    //Metodo responsável por salvar os dados do pedido do usuario
    fun salvarDadosPedidosUsuario(

        //Propriedades
        endereco: String,
        celular: String,
        produto: String,
        preco: String,
        tamanho_calcado: String,
        status_pagamento: String,
        status_entrega: String,
    ){
        //Variável
        var db = FirebaseFirestore.getInstance()
        var usuarioID = FirebaseAuth.getInstance().currentUser!!.uid
        var pedidoID = UUID.randomUUID().toString()

        val pedidos = hashMapOf(
            "endereco" to endereco,
            "celular" to celular,
            "produto" to produto,
            "preco" to preco,
            "tamanho_produto" to tamanho_calcado,
            "status_pagamento" to status_pagamento,
            "staus_entrega" to status_entrega,
        )

        //Gerando a criação da coleção "Usuários_Pedidos" no Banco de dados do FireBase (Baseado no usuário logado "usuarioID")
        val documentoReference = db.collection("Usuario_Pedidos").document(usuarioID)
            .collection("Pedidos").document(pedidoID)
        documentoReference.set(pedidos).addOnSuccessListener {
            Log.d("db_pedidos", "Sucesso ao salvar os pedidos")
        }
    }

    fun obterListaDePedidos(lista_pedidos: MutableList<Pedido>, adapter_pedidos: AdapterPedido){

        var db = FirebaseFirestore.getInstance()
        var usuarioID = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Usuario_Pedidos").document(usuarioID).collection("Pedidos")
            .get().addOnCompleteListener { tarefa ->
                if (tarefa.isSuccessful) {
                    for (documento in tarefa.result!!) {
                        val pedidos = documento.toObject(Pedido::class.java)
                        lista_pedidos.add(pedidos)
                        adapter_pedidos.notifyDataSetChanged()
                    }
                }
            }
    }

}