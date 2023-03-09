package com.app.lojavirtual.activities.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import com.app.lojavirtual.activities.FormLogin.FormLogin
import com.app.lojavirtual.databinding.DialogPerfilUsuarioBinding
import com.app.lojavirtual.model.DB
import com.google.firebase.auth.FirebaseAuth


class DialogPerfilUsuario(private val activity: Activity) {

    lateinit var dialog: AlertDialog

    //Criar ViewBiding dentro dessa classe para recuperar a referência
    // dos id dos botõesde layout (dialog_perfil_usuario)
    lateinit var binding: DialogPerfilUsuarioBinding

    fun iniciarPerfilUsuario(){

        val builder = AlertDialog.Builder(activity)

        // Iniciar perfil
        binding = DialogPerfilUsuarioBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        builder.setCancelable(true)
        dialog = builder.create()
        builder.show()
    }

    // Método para recuperar dados do usuários do BD
    fun recuperarDadosUsuariosBanco(){
        val nomeUsuario = binding.txtNomeUsuario
        val emailUsuario = binding.txtEmailUsuario
        val db = DB()
        db.recuperarDadosDoPerfil(nomeUsuario, emailUsuario)

        //Deslogar usuário quando clicar no botão sair
        binding.btDeslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity,FormLogin::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

}