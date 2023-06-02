package com.example.restapiidemo.livros.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapiidemo.livros.data.LivrosModel
import com.example.restapiidemo.livros.data.LivrosRepository

class LivrosViewModel(application: Application): AndroidViewModel(application){

    private var livrosRepository:LivrosRepository?=null
    var postModelListLiveData : LiveData<List<LivrosModel>>?=null
    var createPostLiveData:LiveData<LivrosModel>?=null
    var deletePostLiveData:LiveData<LivrosModel>?=null

    init {
        livrosRepository = LivrosRepository()
        postModelListLiveData = MutableLiveData()
        createPostLiveData = MutableLiveData()
        deletePostLiveData = MutableLiveData()
    }

    fun fetchAllPosts(){
        postModelListLiveData = livrosRepository?.fetchAllBooks()
    }

    fun createPost(postModel: LivrosModel){
        createPostLiveData = livrosRepository?.createBook(postModel)
    }

    fun deletePost(id:Int){
        //deletePostLiveData = livrosRepository?.deleteBook(id)
    }

}