package com.example.restapiidemo.livros.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.PostModel
import com.example.restapiidemo.livros.data.LivrosModel
import kotlinx.android.synthetic.main.home_rv_item_view.view.*

class LivrosAdapter(var listener:HomeListener) : RecyclerView.Adapter<LivrosAdapter.HomeViewHolder>(){

    private var data : ArrayList<LivrosModel>?=null

    interface HomeListener{
        fun onItemDeleted(livrosModel: LivrosModel, position: Int)
    }

    fun setData(list: ArrayList<LivrosModel>){
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_rv_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = data?.get(position)
        holder.bindView(item)
        holder.itemView.img_delete.setOnClickListener {
            item?.let { it1 ->
                listener.onItemDeleted(it1, position)
            }
        }
    }

    fun addData(livrosModel: LivrosModel) {
        data?.add(0,livrosModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(item: LivrosModel?) {
            itemView.tv_home_item_title.text = item?.title
            itemView.tv_home_item_body.text = item?.count.toString()
        }

    }

}