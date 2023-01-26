package com.agenda.agendadecontatosapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.agenda.agendadecontatosapp.adapter.ContatoAdapter
import com.agenda.agendadecontatosapp.dao.UsuarioDao
import com.agenda.agendadecontatosapp.databinding.ActivityMainBinding
import com.agenda.agendadecontatosapp.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioDao: UsuarioDao
    private lateinit var contatoAdapter: ContatoAdapter
    private val _listaUsuarios = MutableLiveData<MutableList<Usuario>>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            getContatos()

            withContext(Dispatchers.Main){

                   _listaUsuarios.observe(this@MainActivity){ listaUsuarios ->
                       val recyclerViewContatos = binding.recycleViewContatos
                       recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                       recyclerViewContatos.setHasFixedSize(true)
                       contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuarios)
                       recyclerViewContatos.adapter = contatoAdapter
                        contatoAdapter.notifyDataSetChanged()
                   }
            }

        }

        binding.btCadastrar.setOnClickListener {
            val navegarTelaCadastro = Intent(this, CadastrarUsuario::class.java)
            startActivity(navegarTelaCadastro)

        }


    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume(){
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            getContatos()

            withContext(Dispatchers.Main){

                _listaUsuarios.observe(this@MainActivity){ listaUsuarios ->
                    val recyclerViewContatos = binding.recycleViewContatos
                    recyclerViewContatos.layoutManager = LinearLayoutManager(this@MainActivity)
                    recyclerViewContatos.setHasFixedSize(true)
                    contatoAdapter = ContatoAdapter(this@MainActivity, listaUsuarios)
                    recyclerViewContatos.adapter = contatoAdapter
                    contatoAdapter.notifyDataSetChanged()
                }
            }

        }
    }

    private fun getContatos(){
        usuarioDao = AppDatabase.getInstance(this).usuarioDao()
        val listaUsuarios: MutableList<Usuario> = usuarioDao.get()
        _listaUsuarios.postValue(listaUsuarios)

    }
}