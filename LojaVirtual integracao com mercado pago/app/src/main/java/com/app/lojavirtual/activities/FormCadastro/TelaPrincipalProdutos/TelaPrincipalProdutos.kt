package com.app.lojavirtual.activities.FormCadastro.TelaPrincipalProdutos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.app.lojavirtual.R
import com.app.lojavirtual.activities.FormLogin.FormLogin
import com.app.lojavirtual.activities.Pedidos.Pedidos
import com.app.lojavirtual.activities.dialog.DialogPerfilUsuario
import com.app.lojavirtual.adapter.Adapter.AdapterProduto
import com.app.lojavirtual.databinding.ActivityTelaPrincipalProdutosBinding
import com.app.lojavirtual.model.DB
import com.app.lojavirtual.model.Produto
import com.google.firebase.auth.FirebaseAuth

class TelaPrincipalProdutos : AppCompatActivity() {

    lateinit var bindiding: ActivityTelaPrincipalProdutosBinding

    //Criando uma instância para o Adapter
    lateinit var adapterProduto: AdapterProduto

    var lista_produto: MutableList<Produto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindiding = ActivityTelaPrincipalProdutosBinding.inflate(layoutInflater)
        setContentView(bindiding.root)

        val recycler_produtos = bindiding.recyclerProdutos

        //LayoutManager responsável por gerenciar o formato da lista
        // (formato de grade ((Grid)) de duas colunas).
        recycler_produtos.layoutManager = GridLayoutManager(this, 2)

        //Para dar mais desempenho na Recycler utilizamos setFixedSize
        recycler_produtos.setHasFixedSize(true)

        adapterProduto = AdapterProduto(this,lista_produto)
        recycler_produtos.adapter = adapterProduto

        val db = DB()
        db.obterListaDeProdutos(lista_produto,adapterProduto)

    }

    //Método para criar menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal,menu)
        return true
    }
    //Sobrescrita para criar cliques nos itens do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            //Ação para quando clicar nos itens de menu (Perfil, Pedidos e Deslogar).
            R.id.perfil -> iniciarDialogPerfilUsuario()
            R.id.pedidos -> iniciarTelaDePedidos()
            R.id.deslogar -> deslogarUsuario()
        }
        return  super.onOptionsItemSelected(item)
    }

    //Iniciar Dialog do perfil de usuário (botão do menu "Perfil")
    private fun iniciarDialogPerfilUsuario(){
        val dialogPerfilUsuario = DialogPerfilUsuario(this)
        dialogPerfilUsuario.iniciarPerfilUsuario()
        dialogPerfilUsuario.recuperarDadosUsuariosBanco()
    }
    //Navegar para tela de Pedidos
    private fun iniciarTelaDePedidos(){
        val intent = Intent(this,Pedidos::class.java)
        startActivity(intent)
    }

    //Metodo privado para deslogar usuário
    private fun deslogarUsuario(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}