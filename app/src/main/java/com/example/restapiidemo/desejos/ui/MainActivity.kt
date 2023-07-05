package com.example.restapiidemo.desejos.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restapiidemo.R
import com.example.restapiidemo.desejos.data.DesejosModel
import com.example.restapiidemo.desejos.viewmodel.DesejosViewModel
import com.example.restapiidemo.livros.ui.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_desejos_dialog.view.*

class MainActivity : AppCompatActivity(), DesejosAdapter.HomeListener {

    private lateinit var vm: DesejosViewModel
    private lateinit var adapter: DesejosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desejos)

        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.desejos;

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.desejos -> return@OnNavigationItemSelectedListener true
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

                R.id.livros -> {
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

            supportActionBar?.title = "Wanted Books";

        vm = ViewModelProvider(this)[DesejosViewModel::class.java]

        initAdapter()

            vm.fetchAllPosts()

        vm.postModelListLiveData?.observe(this, Observer {
            if (it != null) {
                rv_home.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<DesejosModel>)
            } else {
                showToast("Something went wrong")
            }
            progress_home.visibility = View.GONE
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.desejos_menu, menu)
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
        val view = LayoutInflater.from(this).inflate(R.layout.create_desejos_dialog, null)
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
                val desejosModel = DesejosModel()
                desejosModel.title = title
                desejosModel.description = description
                desejosModel.gender = gender
                desejosModel.status = status
                desejosModel.start_date = start_date
                desejosModel.end_date = end_date
                desejosModel.pages = pages.toInt()
                desejosModel.edition = edition
                desejosModel.language = language
                desejosModel.note = note.toInt()
                desejosModel.borrowed = borrowed


                vm.createPost(desejosModel)
                Log.d("ola", vm.toString());
                vm.createPostLiveData?.observe(this, Observer {
                    if (it != null) {
                        adapter.addData(desejosModel)
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
        adapter = DesejosAdapter(this)
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = adapter
    }

    override fun onItemDeleted(desejosModel:DesejosModel, position: Int) {
        desejosModel.id?.let { vm.deletePost(it) }
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
