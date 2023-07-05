package com.example.restapiidemo.desejos.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapiidemo.desejos.data.DesejosModel
import com.example.restapiidemo.desejos.data.DesejosRepository

class DesejosViewModel(application: Application): AndroidViewModel(application){

    private var desejosRepository:DesejosRepository?=null
    var postModelListLiveData : LiveData<List<DesejosModel>>?=null
    var createPostLiveData:LiveData<DesejosModel>?=null
    var deletePostLiveData:LiveData<DesejosModel>?=null

    init {
        desejosRepository = DesejosRepository()
        postModelListLiveData = MutableLiveData()
        createPostLiveData = MutableLiveData()
        deletePostLiveData = MutableLiveData()
    }

    fun fetchAllPosts(){
        postModelListLiveData = desejosRepository?.fetchAllDesejos()
    }
    fun createPost(postModel: DesejosModel){
        createPostLiveData = desejosRepository?.createDesejos(postModel)
    }

    fun deletePost(id:Int){
      // deletePostLiveData = desejosRepository?.deleteDesejos(id)
    }

}