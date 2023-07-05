package com.example.restapiidemo.emprestados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restapiidemo.emprestados.data.EmprestadosModel
import com.example.restapiidemo.emprestados.data.EmprestadosRepository


class EmprestadosViewModel(application: Application): AndroidViewModel(application){

    private var emprestadosRepository:EmprestadosRepository?=null
    var emprestadosModelListLiveData : LiveData<List<EmprestadosModel>>?=null
    var deleteEmprestadosLiveData:LiveData<EmprestadosModel>?=null

    init {
        emprestadosRepository = EmprestadosRepository()
        emprestadosModelListLiveData = MutableLiveData()
        deleteEmprestadosLiveData = MutableLiveData()
    }

    fun fetchAllPosts(){
        emprestadosModelListLiveData = emprestadosRepository?.fetchAllBorrowed()
    }

    fun deletePost(id:Int){
        //deleteEmprestadosLiveData = emprestadosRepository?.deleteBorrowed(id)
    }

}