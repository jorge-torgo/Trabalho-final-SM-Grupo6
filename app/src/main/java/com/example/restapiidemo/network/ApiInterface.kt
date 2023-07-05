package com.example.restapiidemo.network

import com.example.restapiidemo.home.data.PostModel
import com.example.restapiidemo.livros.data.LivrosModel
import com.example.restapiidemo.emprestados.data.EmprestadosModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("todos")
    fun fetchAllPosts(): Call<List<PostModel>>

    @POST("todos")
    fun createPost(@Body postModel: PostModel):Call<PostModel>

    @DELETE("todos/{id}")
    fun deletePost(@Path("id") id:Int):Call<String>

    @GET("livros")
    fun fetchAllBooks(): Call<List<LivrosModel>>

    @GET("livrosFiltro/{bookcase}")
    fun fetchAllBooksFiltered(@Path("bookcase") bookcase:String): Call<List<LivrosModel>>

    @POST("livros")
    fun createBook(@Body postModel: LivrosModel):Call<LivrosModel>

    @DELETE("livros/{id}")
    fun deleteBook(@Path("id") id:Int):Call<String>

    @GET("emprestados")
    fun fetchAllBorrowed(): Call<List<EmprestadosModel>>

    @DELETE("emprestados/{id}")
    fun deleteBorrowed(@Path("id") id:Int):Call<String>
}