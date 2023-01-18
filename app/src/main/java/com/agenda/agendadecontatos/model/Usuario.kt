package com.agenda.agendadecontatos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Criando as tabelas
@Entity(tableName = "tabela_usuarios")
data class Usuario(

//Criando as colunas (coluna Nome)
    @ColumnInfo(name = "Nome") val nome: String,
    @ColumnInfo(name = "sobrenome") val sobrenome: String,
    @ColumnInfo(name = "idade") val idade: String,
    @ColumnInfo(name = "celular") val celular: String,
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}