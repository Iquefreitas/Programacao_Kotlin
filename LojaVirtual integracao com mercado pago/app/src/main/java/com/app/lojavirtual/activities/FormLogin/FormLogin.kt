package com.app.lojavirtual.activities.FormLogin

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.lojavirtual.R
import com.app.lojavirtual.activities.FormCadastro.FormCadastro
import com.app.lojavirtual.activities.FormCadastro.TelaPrincipalProdutos.TelaPrincipalProdutos
import com.app.lojavirtual.activities.dialog.DialogCarregando
import com.app.lojavirtual.databinding.ActivityFormLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class FormLogin : AppCompatActivity() {

    lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        val dialogCarregando = DialogCarregando(this)

        //Evento de click no botão "Entrar" tela de Login
        binding.btEntrar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

         //Validações dos campos para prenchimento de todos os campos
          if (email.isEmpty() || senha.isEmpty()){
             val snackbar = Snackbar.make(it, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
             snackbar.setBackgroundTint(Color.RED)
             snackbar.setTextColor(Color.WHITE)
             snackbar.show()

          }else{

             //Navegar até a tela Tela principal de produtos
             FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener { tarefa ->
                 if (tarefa.isSuccessful){

                     //Métodos para inicialização do carregamento da Dialog
                     dialogCarregando.iniciarCarregamentoAlertaDialog()

                     //Handler: temporizador entre telas
                     Handler(Looper.getMainLooper()).postDelayed({

                        //Puxando o método ir para tela de produtos criado
                         irParaTelaDeProdutos()

                         dialogCarregando.liberarAlertaDialog()
                     },3000) //Tempo de execução da Dialog

                     //Mensagem de sucesso ao efetuar o login (Snackbar opcional)
                     //val snackbar = Snackbar.make(it, "Sucesso ao fazer o login!",Snackbar.LENGTH_SHORT)
                     //snackbar.setBackgroundTint(Color.BLUE)
                     //snackbar.setTextColor(Color.WHITE)
                     //snackbar.show()

                     //Intenção após clicar no botão entrar (Ir para tela principal de produtos)
                     val intent = Intent(this, TelaPrincipalProdutos::class.java)
                     startActivity(intent)
                     finish()
                 }
             }
          }
        }

        //Evento de clique no botão Cadastrar
        binding.txtTelaCadastro.setOnClickListener {

            //Intenção ao clicar no botão "Cadastrar" (Ir para Tela de cadastro)
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }

    }

    //Criando método para ir para tela de produtos quando o usuario já estiver logado
    private fun irParaTelaDeProdutos(){

        //Intenção após clicar no botão entrar (Ir para tela principal de produtos)
        val intent = Intent(this, TelaPrincipalProdutos::class.java)
        startActivity(intent)
        finish()
    }

    //Ciclo de vida de uma atividade quando ela é iniciada
    //neste caso se o usuário logou uma vez, ele permanece logado.
    override fun onStart() {
        super.onStart()
        
        //Usuário atual no sistema (currentUser)
        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        //Condição para quando o usuário já estiver logado
        if (usuarioAtual != null){
            irParaTelaDeProdutos()
        }

    }
}