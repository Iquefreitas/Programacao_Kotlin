package com.app.lojavirtual.activities.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import com.app.lojavirtual.R

class DialogCarregando(private val activity: Activity) {

    //Variável AlertaDialog
    lateinit var dialog: AlertDialog

    //Método de carregamento da Dialog
    fun iniciarCarregamentoAlertaDialog(){

        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity.layoutInflater
        builder.setView(layoutInflater.inflate(R.layout.dialog_carregando,null))
        builder.setCancelable(false) //Se colocar com true o usuário consegue cancelar a interação da dialog (não recomendado0
        dialog = builder.create()
        dialog.show()

    }

    fun liberarAlertaDialog(){
        dialog.dismiss()
    }

}