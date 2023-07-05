package com.example.restapiidemo.emprestados.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restapiidemo.R
import com.example.restapiidemo.emprestados.data.EmprestadosModel
import kotlinx.android.synthetic.main.home_rv_item_view.view.*

class EmprestadosAdapter(var listener:HomeListener) : RecyclerView.Adapter<EmprestadosAdapter.HomeViewHolder>(){

    private var data : ArrayList<EmprestadosModel>?=null

    interface HomeListener{
        fun onItemDeleted(emprestadosModel: EmprestadosModel, position: Int)
    }

    fun setData(list: ArrayList<EmprestadosModel>){
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.emprestados_rv_item_view, parent, false))
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


    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(item: EmprestadosModel?) {
            itemView.tv_home_item_title.text = item?.title
            itemView.tv_home_item_body.text = item?.borrowed
        }

    }

}