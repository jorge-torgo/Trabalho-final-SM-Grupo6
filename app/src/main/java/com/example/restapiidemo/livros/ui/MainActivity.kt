package com.example.restapiidemo.livros.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restapiidemo.R
import com.example.restapiidemo.livros.data.LivrosModel
import com.example.restapiidemo.livros.viewmodel.LivrosViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_post_dialog.view.*

class MainActivity : AppCompatActivity(), LivrosAdapter.HomeListener {

    private lateinit var vm: LivrosViewModel
    private lateinit var adapter: LivrosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("Bookcase")
            getSupportActionBar()?.title=value;
        }
        else
            getSupportActionBar()?.title="Livros";

        vm = ViewModelProvider(this)[LivrosViewModel::class.java]

        initAdapter()

        vm.fetchAllPosts()

        vm.postModelListLiveData?.observe(this, Observer {
            if (it != null) {
                rv_home.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<LivrosModel>)
            } else {
                showToast("Something went wrong")
            }
            progress_home.visibility = View.GONE
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.livro_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_create_book -> showCreatePOstDialog()
        }
        return true
    }

    private fun showCreatePOstDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.create_livro_dialog, null)
        dialog.setContentView(view)

        var title = ""
        var count = ""

        view.btn_submit.setOnClickListener {
            title = view.et_title.text.toString().trim()
            count = view.et_body.text.toString().trim()

            Log.d("ola", count.toString());
            if (title.isNotEmpty() && count.isNotEmpty()) {
                val livrosModel = LivrosModel()
                livrosModel.title = title
                livrosModel.count = count.toInt()

                vm.createPost(livrosModel)
                Log.d("ola", vm.toString());
                vm.createPostLiveData?.observe(this, Observer {
                    if (it != null) {
                        adapter.addData(livrosModel)
                        rv_home.smoothScrollToPosition(0)
                    } else {
                        showToast("Cannot create post at the moment")
                    }
                    dialog.cancel()
                })

            } else {
                showToast("Please fill data carefully!")
            }

        }

        dialog.show()

        val window = dialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

    }

    private fun initAdapter() {
        adapter = LivrosAdapter(this)
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = adapter
    }

    override fun onItemDeleted(livrosModel: LivrosModel, position: Int) {
        livrosModel.id?.let { vm.deletePost(it) }
        vm.deletePostLiveData?.observe(this, Observer {
            if (it != null) {
                adapter.removeData(position)
            } else {
                showToast("Cannot delete post at the moment!")
            }
        })

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}
