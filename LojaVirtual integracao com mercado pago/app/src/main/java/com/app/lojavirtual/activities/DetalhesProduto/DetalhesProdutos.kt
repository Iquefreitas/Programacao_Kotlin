package com.app.lojavirtual.activities.DetalhesProdutos

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.lojavirtual.R
import com.app.lojavirtual.activities.pagamento.Pagamento
import com.app.lojavirtual.databinding.ActivityDetalhesProdutosBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class DetalhesProdutos : AppCompatActivity() {

    lateinit var binding: ActivityDetalhesProdutosBinding
    var tamanho_calcado = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Recuperando os objetos através das chaves
        val foto = intent.extras?.getString("foto")
        val nome = intent.extras?.getString("nome")
        val preco = intent.extras?.getString("preco")

        //Ulitizando o Glide para baixar e renderizar a imagem vindo do BackEnd (WEB)
        Glide.with(this).load(foto).into(binding.dtFotoProduto)
        binding.dtNomeProduto.text = nome
        binding.dtPrecoProduto.text = "R$ ${preco}"


        binding.btFinalizarPedido.setOnClickListener {
            val tamanho_calcado38 = binding.tamanho38
            val tamanho_calcado39 = binding.tamanho39
            val tamanho_calcado40 = binding.tamanho40
            val tamanho_calcado41 = binding.tamanho41
            val tamanho_calcado42 = binding.tamanho42

            //Estrutura When para validar as informações selecionadas (Tamanho do calçado)
            when{

                //Fazendo a checagem dos tamanhos..
                tamanho_calcado38.isChecked -> tamanho_calcado = "38"
                tamanho_calcado39.isChecked -> tamanho_calcado = "39"
                tamanho_calcado40.isChecked -> tamanho_calcado = "40"
                tamanho_calcado41.isChecked -> tamanho_calcado = "41"
                tamanho_calcado42.isChecked -> tamanho_calcado = "42"
            }
            //Mostrar a mensagem para o usuários dizendo que o tamanho não foi selecionado
            if (!tamanho_calcado38.isChecked && !tamanho_calcado39.isChecked && !tamanho_calcado40.isChecked
                && !tamanho_calcado41.isChecked && !tamanho_calcado42.isChecked){
                val snackbar = Snackbar.make(it, "Escolha o tamanho do calçado!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
                //Envio do tamanho selecionado para a tela de pagamento
                // (Aproveitando tbm para enviar o Nome do produto e o preço)
                val intent = Intent(this,Pagamento::class.java)
                intent.putExtra("Tamanho_calcado",tamanho_calcado)
                intent.putExtra("nome",nome)
                intent.putExtra("preco",preco)
                startActivity(intent)
            }
        }
    }
}