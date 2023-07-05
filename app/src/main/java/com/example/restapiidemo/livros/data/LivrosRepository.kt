package com.example.restapiidemo.livros.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapiidemo.network.ApiClient
import com.example.restapiidemo.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LivrosRepository {

    private var apiInterface:ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun fetchAllBooks():LiveData<List<LivrosModel>>{
        val data = MutableLiveData<List<LivrosModel>>()

        apiInterface?.fetchAllBooks()?.enqueue(object : Callback<List<LivrosModel>>{

            override fun onFailure(call: Call<List<LivrosModel>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<LivrosModel>>,
                response: Response<List<LivrosModel>>
            ) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }

            }
        })

        return data

    }

    fun fetchAllBooksFiltered(bookcase:String):LiveData<List<LivrosModel>>{
        val data = MutableLiveData<List<LivrosModel>>()

        apiInterface?.fetchAllBooksFiltered(bookcase)?.enqueue(object : Callback<List<LivrosModel>>{

            override fun onFailure(call: Call<List<LivrosModel>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<LivrosModel>>,
                response: Response<List<LivrosModel>>
            ) {

                val res = response.body()
                if (response.code() == 200 &&  res!=null){
                    data.value = res
                }else{
                    data.value = null
                }

            }
        })

        return data

    }

    fun createBook(livrosModel: LivrosModel):LiveData<LivrosModel>{
        val data = MutableLiveData<LivrosModel>()

        apiInterface?.createBook(livrosModel)?.enqueue(object : Callback<LivrosModel>{
            override fun onFailure(call: Call<LivrosModel>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<LivrosModel>, response: Response<LivrosModel>) {
                val res = response.body()
                if (response.code() == 201 && res!=null){
                    data.value = res
                }else{
                    data.value = null
                }
            }
        })

        return data

    }

    fun deleteBook(id:Int):LiveData<Boolean>{
        val data = MutableLiveData<Boolean>()

        apiInterface?.deleteBook(id)?.enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                data.value = false
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                data.value = response.code() == 200
            }
        })

        return data

    }

}