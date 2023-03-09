package com.app.lojavirtual.interfaceMercadoPago;

import com.google.gson.JsonObject;

import org.checkerframework.checker.index.qual.Positive;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ComunicacaoServidorMP {
    @Headers({
            "Content-Type: application/json"
    })

    //Enviar dados para Mercado Pago utilizando o POST
    @POST()
    Call<JsonObject> enviarPagamento(
            @Url String url,
            @Body JsonObject dados);

}
