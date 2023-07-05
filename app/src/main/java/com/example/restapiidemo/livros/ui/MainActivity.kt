package com.example.restapiidemo.livros.ui

import android.app.Dialog
import android.content.Intent
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_livro_dialog.view.*

class MainActivity : AppCompatActivity(), LivrosAdapter.HomeListener {

    private lateinit var vm: LivrosViewModel
    private lateinit var adapter: LivrosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livros)

        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.livros;

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.livros -> return@OnNavigationItemSelectedListener true
                R.id.estantes -> {
                    startActivity(
                        Intent(
                            applicationContext,
                            com.example.restapiidemo.home.ui.MainActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.desejos -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.emprestados -> {
                    startActivity(
                        Intent(
                            applicationContext,
                            com.example.restapiidemo.emprestados.ui.MainActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.logout -> {
                    startActivity(
                        Intent(
                            applicationContext,
                            com.example.restapiidemo.login.ui.MainActivity::class.java
                        )
                    )
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })


        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("Bookcase")
            supportActionBar?.title = value;
        } else
            supportActionBar?.title = "Books";

        vm = ViewModelProvider(this)[LivrosViewModel::class.java]

        initAdapter()


        if (extras != null) {
            Log.d("ola", extras.getString("Bookcase").toString());
            vm.fetchBooksFilter(extras.getString("Bookcase").toString())
        }
        else
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
        var description = ""
        var gender = ""
        var status = ""
        var start_date = ""
        var end_date = ""
        var pages = ""
        var edition = ""
        var language = ""
        var note = ""
        var borrowed = ""


        view.btn_submit.setOnClickListener {
            title = view.et_title.text.toString().trim()
            description = view.et_description.text.toString().trim()
            gender = view.et_gender.text.toString().trim()
            status = view.et_status.text.toString().trim()
            start_date = view.et_start_date.text.toString().trim()
            end_date = view.et_end_date.text.toString().trim()
            pages = view.et_pages.text.toString().trim()
            edition = view.et_edition.text.toString().trim()
            language = view.et_language.text.toString().trim()
            note = view.et_note.text.toString().trim()
            borrowed = view.et_borrowed.text.toString().trim()


            if (title.isNotEmpty() && description.isNotEmpty()) {
                val livrosModel = LivrosModel()
                livrosModel.title = title
                livrosModel.description = description
                livrosModel.gender = gender
                livrosModel.status = status
                livrosModel.start_date = start_date
                livrosModel.end_date = end_date
                livrosModel.pages = pages.toInt()
                livrosModel.edition = edition
                livrosModel.language = language
                livrosModel.note = note.toInt()
                livrosModel.borrowed = borrowed


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
