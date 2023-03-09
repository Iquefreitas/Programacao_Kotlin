package com.app.lojavirtual.activities.pagamento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.app.lojavirtual.R;
import com.app.lojavirtual.databinding.ActivityPagamentoBinding;
import com.app.lojavirtual.interfaceMercadoPago.ComunicacaoServidorMP;
import com.app.lojavirtual.model.DB;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mercadopago.android.px.configuration.AdvancedConfiguration;
import com.mercadopago.android.px.core.MercadoPagoCheckout;
import com.mercadopago.android.px.model.Payment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Pagamento extends AppCompatActivity{

    ActivityPagamentoBinding binding;
    private String tamanho_calcado;
    private String nome;
    private String preco;

    private final String PUBLIC_KEY = "TEST-b6e5c7ab-3ce9-4585-ad29-8c46df6619aa";
    private final String ACCESS_TOKEN = "TEST-5197951102674042-022406-2eea19496b7a031c26c3c9e001d32783-64969584";

    //Istanciar o banco de dados (DB)
    DB db = new DB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Recuperação do tamanho do calçado
        tamanho_calcado = getIntent().getExtras().getString("tamanho_calcado");
        nome = getIntent().getExtras().getString("nome");
        preco = getIntent().getExtras().getString("preco");

        //Evento de clique para o botão finalizar compra
        binding.btFazerPagamento.setOnClickListener(v ->{

            String bairro = binding.editBairro.getText().toString();
            String rua_numero = binding.editRuaNumero.getText().toString();
            String cidade_estado = binding.editCidadeEstado.getText().toString();
            String celular = binding.editCelular.getText().toString();

            if (bairro.isEmpty() || rua_numero.isEmpty() || cidade_estado.isEmpty() || celular.isEmpty()){
            //Mensagem caso todos os campos não estiverem preenchidos.

                Snackbar snackbar = Snackbar.make(v, "Preencha todos os campos!", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.RED);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }else{
                //Criar método Json (Mercado Pago)
                criarJsonObject();

            }
        });
    }
    private void criarJsonObject(){
        //Criar itens descriminados pelo Json (configuração padrão solictada pelo Mercado Pago)
        JsonObject dados = new JsonObject();

        //Primeiro Item

        JsonArray item_lista = new JsonArray();
        JsonObject item;

        //Segundo item
        JsonObject email = new JsonObject();

        /*Terceiro item - Excluir formas de pagamento - neste caso vamos retirar o boleto
        JsonObject excluir_tipo_pagamento = new JsonObject();
        JsonArray ids = new JsonArray();
        JsonObject removerBoleto = new JsonObject();*/


        //Configuração Jason Mercado Pago (tem que ser igual ao arquivo padrão do Mercado pago)
        item = new JsonObject();
        item.addProperty("title",nome);
        item.addProperty("quantity",1);
        item.addProperty("currency_id","BRL");
        item.addProperty("unit_price",Double.parseDouble(preco));
        item_lista.add(item);

        dados.add("items",item_lista);
        Log.d("j",item_lista.toString());

        //Criação de String para pegar o e-mail do usuário que esta logado no sistema (Loja virtual)
        String emailUsuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        email.addProperty("email",emailUsuario);
        dados.add("payer",email);

        /*Remover objeto (Boleto)
        removerBoleto.addProperty("id","ticket");
        ids.add(removerBoleto);
        excluir_tipo_pagamento.add("excluded_payment_types",ids);
        excluir_tipo_pagamento.addProperty("installments",2);
        dados.add("payment_methods",excluir_tipo_pagamento);*/

       // Log.d("j",dados.toString());
        criarPreferenciaPagamento(dados);
    }
    private void criarPreferenciaPagamento(JsonObject dados){

        String site = "https://api.mercadopago.com";
        String url = "/checkout/preferences?access_token=" + ACCESS_TOKEN;

        //Comunicação com Retrofit2
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(site)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ComunicacaoServidorMP conexao_pagamento = retrofit.create(ComunicacaoServidorMP.class);
        Call<JsonObject> request = conexao_pagamento.enviarPagamento(url,dados);
        request.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                   String preferenceId = response.body().get("id").getAsString();
                   //Criando método de pagamento
                    criarPagamento(preferenceId);
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
    private void criarPagamento(String preferenceId){

        //Criando o método de pagamento
        final AdvancedConfiguration advancedConfiguration =
                new AdvancedConfiguration.Builder().setBankDealsEnabled(false).build();
        new MercadoPagoCheckout
                .Builder("PUBLIC_KEY", preferenceId)
                .setAdvancedConfiguration(advancedConfiguration).build()
                .startPayment(this,123);
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {

                final Payment pagamento = (Payment) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT);
                //Metodo de resposta do MP
                respostaMercadoPago(pagamento);

            }else if (resultCode == RESULT_CANCELED) {

            }else {

            }
        }
    }
    private void respostaMercadoPago(Payment pagamento){

        String status = pagamento.getPaymentStatus();

        String bairro = binding.editBairro.getText().toString();
        String rua_numero = binding.editRuaNumero.getText().toString();
        String cidade_estado = binding.editCidadeEstado.getText().toString();
        String celular = binding.editCelular.getText().toString();

        String endereco = "Bairro: " + bairro + " " + "Rua e Número:" + " " + rua_numero + " Cidade e Estado: " + " " + cidade_estado;
        String status_pagamento = "Status de Pagamento: " + " " + "Pagamento Aprovado";
        String status_entrega = "Status de Entrega: " + " " + "Em andamento";

        String nome_produto = nome;
        String precoProduto = preco;
        String tamanho = tamanho_calcado;


        if(status.equalsIgnoreCase("approved")){
            Snackbar snackbar = Snackbar.make(binding.container,"Sucesso ao fazer o pagamento",Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.BLUE);
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
            db.salvarDadosPedidosUsuario(endereco,celular,nome_produto,precoProduto,tamanho,status_pagamento,status_entrega);

        }else if (status.equalsIgnoreCase("rejected")){
            Snackbar snackbar = Snackbar.make(binding.container,"Erro ao fazer o pagamento",Snackbar.LENGTH_SHORT);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.setTextColor(Color.WHITE);
            snackbar.show();
            Log.e("pagamento","mensagem de erro");
        }
    }
}