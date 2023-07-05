package com.example.restapiidemo.emprestados.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapiidemo.network.ApiClient
import com.example.restapiidemo.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmprestadosRepository {

    private var apiInterface:ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun fetchAllBorrowed():LiveData<List<EmprestadosModel>>{
        val data = MutableLiveData<List<EmprestadosModel>>()

        apiInterface?.fetchAllBorrowed()?.enqueue(object : Callback<List<EmprestadosModel>>{

            override fun onFailure(call: Call<List<EmprestadosModel>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<EmprestadosModel>>,
                response: Response<List<EmprestadosModel>>
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



    fun deleteBorrowed(id:Int):LiveData<Boolean>{
        val data = MutableLiveData<Boolean>()

        apiInterface?.deleteBorrowed(id)?.enqueue(object : Callback<String>{
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