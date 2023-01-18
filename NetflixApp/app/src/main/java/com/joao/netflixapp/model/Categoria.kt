package com.joao.netflixapp.model

import com.google.gson.annotations.SerializedName

//Dados extraidos do Jason (Web)

data class Categoria(
    @SerializedName("titulo") val titulo: String? = null,
    @SerializedName("capas") val filmes: MutableList<Filme> = mutableListOf()
)

data class Filme(
   @SerializedName("url_imagem") val capa: String? = null,
   @SerializedName("id") val id: Int = 0,
   val nome: String? = null,
   val descricao: String? = null,
   val elenco: String? = null,
)

data class Categorias(@SerializedName("categoria")
  val categorias: MutableList<Categoria> = mutableListOf()

)