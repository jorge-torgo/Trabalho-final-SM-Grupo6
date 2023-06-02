package com.example.restapiidemo.home.ui


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.PostModel
import kotlinx.android.synthetic.main.home_rv_item_view.view.*
import kotlinx.android.synthetic.main.home_rv_item_view.view.img_delete
import kotlinx.android.synthetic.main.home_rv_item_view.view.tv_home_item_body
import kotlinx.android.synthetic.main.home_rv_item_view.view.tv_home_item_title
import kotlinx.android.synthetic.main.livros_rv_item_view.view.*

class HomeAdapter(var listener:HomeListener) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    private var data : ArrayList<PostModel>?=null

    interface HomeListener{
        fun onItemDeleted(postModel: PostModel, position: Int)
    }

    fun setData(list: ArrayList<PostModel>){
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
                val intent = Intent(holder.itemView.context, com.example.restapiidemo.livros.ui.MainActivity::class.java)
                intent.putExtra("Bookcase",it1.title.toString());
                holder.itemView.context.startActivity(intent)
            }
        }


    }

    fun addData(postModel: PostModel) {
        data?.add(0,postModel)
        notifyItemInserted(0)
    }

    fun removeData(position: Int) {
        data?.removeAt(position)
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindView(item: PostModel?) {
            itemView.tv_home_item_title.text = item?.title
            itemView.tv_home_item_body.text = item?.count.toString()
        }

    }

}