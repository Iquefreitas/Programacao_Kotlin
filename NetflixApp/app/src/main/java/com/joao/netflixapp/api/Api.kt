package com.joao.netflixapp.api

import com.joao.netflixapp.model.Categorias
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("/filmes")
    fun listaCategorias(): Call<Categorias>
}