package com.example.restapiidemo.desejos.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapiidemo.network.ApiClient
import com.example.restapiidemo.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DesejosRepository {

    private var apiInterface:ApiInterface?=null

    init {
        apiInterface = ApiClient.getApiClient().create(ApiInterface::class.java)
    }

    fun fetchAllDesejos():LiveData<List<DesejosModel>>{
        val data = MutableLiveData<List<DesejosModel>>()

        apiInterface?.fetchAllDesejos()?.enqueue(object : Callback<List<DesejosModel>>{

            override fun onFailure(call: Call<List<DesejosModel>>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(
                call: Call<List<DesejosModel>>,
                response: Response<List<DesejosModel>>
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

    fun createDesejos(desejosModel: DesejosModel):LiveData<DesejosModel>{
        val data = MutableLiveData<DesejosModel>()

        apiInterface?.createDesejos(desejosModel)?.enqueue(object : Callback<DesejosModel>{
            override fun onFailure(call: Call<DesejosModel>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<DesejosModel>, response: Response<DesejosModel>) {
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

    fun deleteDesejos(id:Int):LiveData<Boolean>{
        val data = MutableLiveData<Boolean>()

        apiInterface?.deleteDesejos(id)?.enqueue(object : Callback<String>{
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