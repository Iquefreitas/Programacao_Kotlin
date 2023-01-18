package com.joao.netflixapp

import android.content.Intent
import android.graphics.Color
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.joao.netflixapp.databinding.ActivityFormCadastroBinding
import com.joao.netflixapp.databinding.ActivityFormLoginBinding

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityFormCadastroBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#000000")
        binding.editEmail.requestFocus()

        Firebase

        binding.btVamosLa.setOnClickListener {

            val email = binding.editEmail.text.toString()

            if(!email.isEmpty()){
               binding.conteinerSenha.visibility = View.VISIBLE
                binding.btVamosLa.visibility = View.GONE
                binding.btContinuar.visibility = View.VISIBLE
                binding.txtTitulo.setText("Um mundo de séries e filmes \n ilimitados esperando por você.")
                binding.txtDescricao.setText("Crie uma conta para saber mais sobre \n o nosso App de Filmes")
                binding.conteinerEmail.boxStrokeColor = Color.parseColor("#FF018786")
                binding.conteinerEmail.helperText = ""
                binding.conteinerHeader.visibility = View.VISIBLE

            }else{
                binding.conteinerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                binding.conteinerEmail.helperText = "O e-mail é obrigatório"
            }
        }
        binding.btContinuar.setOnClickListener { 
            
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            
            if (!email.isEmpty() && !senha.isEmpty()){
                cadastro(email,senha)
            }else if (senha.isEmpty()){
                binding.conteinerSenha.boxStrokeColor = Color.parseColor("#FF0000")
                binding.conteinerSenha.helperText = "A senha é obrigatória"
                binding.conteinerEmail.boxStrokeColor = Color.parseColor("#FF018786")

            }else if (email.isEmpty()){
                binding.conteinerEmail.boxStrokeColor = Color.parseColor("#FF0000")
                binding.conteinerEmail.helperText = "O email é obrigatório"
            }
        }
        binding.txtEntrar.setOnClickListener {
            val intent = Intent(this,FormLogin::class.java)
            startActivity(intent)
        }
    }

    private fun cadastro(email: String, senha: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener { cadastro ->
        if (cadastro.isSuccessful) {
            Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show()
        }
        }.addOnFailureListener{
            Toast.makeText(this, "Erro ao Cadastrar,", Toast.LENGTH_SHORT).show()
        }

    }

}
