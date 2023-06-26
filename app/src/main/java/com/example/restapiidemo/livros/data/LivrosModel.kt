package com.example.restapiidemo.livros.data

data class LivrosModel(
    var id:Int?=0,
    var title:String?="",
    var description:String?="",
    var gender:String?="",
    var status:String?="",
    var start_date:String?="",
    var end_date:String?="",
    var pages:Int?=0,
    var edition:String?="",
    var language:String?="",
    var note:Int?=0,
    var borrowed:String?=""

    )