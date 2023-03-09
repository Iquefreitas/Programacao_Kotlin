package com.app.lojavirtual.activities.FormCadastro

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.lojavirtual.R
import com.app.lojavirtual.databinding.ActivityFormCadastroBinding
import com.app.lojavirtual.model.DB
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class FormCadastro : AppCompatActivity() {

    lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        //Inicioalização da varivável DB
        val db = DB()

        binding.btCadastrar.setOnClickListener {

            val nome = binding.txtNomeCadastro.text.toString()
            val email = binding.txtEmailCadastro.text.toString()
            val senha = binding.txtsenhaCadastro.text.toString()

            //Condicional para verificar se os campos (Nome, Email e Senha) estão devidamente preenchidos
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()){

                val snackbar = Snackbar.make(it,"Preencha todos os campos!",Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{

               //Cadastrando e-mail e senha no FireBase
               FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener {tarefa ->

                   if (tarefa.isSuccessful){

                       //Salvando usuário no Banco de Dados
                       db.salvarDadosUsuario(nome)

               //Mensagem de cadastro realizado com sucesso (snackbar)
                    val snackbar = Snackbar.make(it,"Cadastro realizado com sucesso!",Snackbar.LENGTH_SHORT)

               //Colocar cor no Background e no texto da mensagem (snackbar)
                    snackbar.setBackgroundTint(Color.BLUE)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                   }

                   //Método para informar o usuário qual é a falha na hora de cadastrar um novo usuário
               }.addOnFailureListener { erroCadastro ->

                   val mensagemErro = when(erroCadastro){
                       is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo 6 caracteres"
                       is FirebaseAuthUserCollisionException -> "Esta conta já foi cadastrada"
                       is FirebaseNetworkException -> "Sem conexão com a Internet"
                       else -> "Erro ao cadastrar usuário"
                   }

                   val snackbar = Snackbar.make(it, mensagemErro,Snackbar.LENGTH_SHORT)
                   snackbar.setBackgroundTint(Color.RED)
                   snackbar.setTextColor(Color.WHITE)
                  snackbar.show()

               }
            }
        }
    }
}
