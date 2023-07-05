package com.example.restapiidemo.desejos.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restapiidemo.R
import com.example.restapiidemo.desejos.data.DesejosModel
import com.example.restapiidemo.visualizarlivros.ui.MainActivity
import kotlinx.android.synthetic.main.home_rv_item_view.view.*

class DesejosAdapter(var listener:HomeListener) : RecyclerView.Adapter<DesejosAdapter.HomeViewHolder>(){

    private var data : ArrayList<DesejosModel>?=null

    interface HomeListener{
        fun onItemDeleted(livrosModel: DesejosModel, position: Int)
    }

    fun setData(list: ArrayList<DesejosModel>){
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
        holder.bindView(item)
        holder.itemView.tv_home_item_title .setOnClickListener {
            item?.let { it1 ->
                Log.d("ola", it1.title.toString());
                val intent = Intent(holder.itemView.context, MainActivity::class.java)
                intent.putExtra("Titulo",it1.title.toString());
                intent.putExtra("descricao",it1.description.toString());
                intent.putExtra("genero",it1.gender.toString());
                intent.putExtra("estado",it1.status.toString());
                intent.putExtra("data_inicio",it1.start_date.toString());
                intent.putExtra("data_fim",it1.end_date.toString());
                intent.putExtra("paginas",it1.pages.toString());
                intent.putExtra("edicao",it1.edition.toString());
                intent.putExtra("lingua",it1.language.toString());
                intent.putExtra("nota",it1.note.toString());
                intent.putExtra("emprestar",it1.borrowed.toString());

                holder.itemView.context.startActivity(intent)
            }
        }
    }

    fun addData(livrosModel: DesejosModel) {
        data?.add(0,livrosModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(item: DesejosModel?) {
            itemView.tv_home_item_title.text = item?.title
            itemView.tv_home_item_body.text = item?.description.toString()
        }

    }

}