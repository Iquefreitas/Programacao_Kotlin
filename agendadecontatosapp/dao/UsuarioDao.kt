package com.agenda.agendadecontatosapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.agenda.agendadecontatosapp.model.Usuario

@Dao
interface UsuarioDao {

    //Inserir usuários na lista
    @Insert
    fun inserir(listaUsuarios: MutableList<Usuario>)

    //Fazer consulta na tabela de usuarios de forma crescente
    // ORDER = Ordem da listagem
    // ASC = Ordem selecionada (crescente)
    //Obs: Códigos padrão SQL (Internet)

    @Query("SELECT * FROM tabela_usuarios ORDER BY nome ASC ")
    fun get(): MutableList<Usuario>

    //Atualizar dados usuario
    @Query("UPDATE tabela_usuarios SET nome = :novoNome, sobrenome = :novoSobrenome, idade = :novaIdade, celular = :novoCelular "+
            "WHERE uid = :id")

    fun atualizar(id: Int, novoNome: String, novoSobrenome: String, novaIdade: String, novoCelular: String)

    //Deletar usuários da tabela de usuários
    @Query("DELETE FROM tabela_usuarios WHERE uid = :id ")
    fun deletar(id:Int)
}