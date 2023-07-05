package com.example.restapiidemo.home.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restapiidemo.R
import com.example.restapiidemo.home.data.PostModel
import com.example.restapiidemo.home.viewmodel.HomeViewModel
import com.example.restapiidemo.login.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_post_dialog.view.*



class MainActivity : AppCompatActivity(), HomeAdapter.HomeListener {

    private lateinit var vm: HomeViewModel
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.estantes;

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.estantes -> return@OnNavigationItemSelectedListener true
                R.id.livros -> {
                    startActivity(Intent(applicationContext, com.example.restapiidemo.livros.ui.MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.desejos -> {
                    startActivity(Intent(applicationContext, com.example.restapiidemo.livros.ui.MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.emprestados -> {
                    startActivity(Intent(applicationContext, com.example.restapiidemo.emprestados.ui.MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.logout -> {
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })


        vm = ViewModelProvider(this)[HomeViewModel::class.java]

        initAdapter()

        vm.fetchAllPosts()

        vm.postModelListLiveData?.observe(this, Observer {
            if (it != null) {
                rv_home.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<PostModel>)
            } else {
                showToast("Something went wrong")
            }
            progress_home.visibility = View.GONE
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_create_post -> showCreatePOstDialog()
        }
        return true
    }

    private fun showCreatePOstDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.create_post_dialog, null)
        dialog.setContentView(view)

        var title = ""
        var count = ""

        view.btn_submit.setOnClickListener {
            title = view.et_title.text.toString().trim()
            count = view.et_body.text.toString().trim()

            Log.d("ola", count.toString());
            if (title.isNotEmpty() && count.isNotEmpty()) {
                val postModel = PostModel()
                postModel.title = title
                postModel.count = count.toInt()

                vm.createPost(postModel)
                Log.d("ola", vm.toString());
                vm.createPostLiveData?.observe(this, Observer {
                    if (it != null) {
                        adapter.addData(postModel)
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
        adapter = HomeAdapter(this)
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = adapter
    }

    override fun onItemDeleted(postModel: PostModel, position: Int) {
        postModel.id?.let { vm.deletePost(it) }
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
