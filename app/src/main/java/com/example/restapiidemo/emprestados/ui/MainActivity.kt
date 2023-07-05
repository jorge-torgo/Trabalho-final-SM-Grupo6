package com.example.restapiidemo.emprestados.ui

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
import com.example.restapiidemo.desejos.ui.MainActivity
import com.example.restapiidemo.emprestados.ui.EmprestadosAdapter
import com.example.restapiidemo.emprestados.data.EmprestadosModel
import com.example.restapiidemo.emprestados.viewmodel.EmprestadosViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.create_livro_dialog.view.*

class MainActivity : AppCompatActivity(), EmprestadosAdapter.HomeListener {

    private lateinit var vm: EmprestadosViewModel
    private lateinit var adapter: EmprestadosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emprestados)

        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.emprestados;

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.emprestados -> return@OnNavigationItemSelectedListener true
                R.id.estantes -> {
                    startActivity(Intent(applicationContext, com.example.restapiidemo.home.ui.MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.desejos -> {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.livros -> {
                    startActivity(Intent(applicationContext, com.example.restapiidemo.livros.ui.MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.logout -> {
                    startActivity(Intent(applicationContext,
                        com.example.restapiidemo.login.ui.MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

            supportActionBar?.title="Borrowed Books";

        vm = ViewModelProvider(this)[EmprestadosViewModel::class.java]

        initAdapter()

        vm.fetchAllPosts()

        vm.emprestadosModelListLiveData?.observe(this, Observer {
            if (it != null) {
                rv_home.visibility = View.VISIBLE
                adapter.setData(it as ArrayList<EmprestadosModel>)
            } else {
                showToast("Something went wrong")
            }
            progress_home.visibility = View.GONE
        })

    }


    private fun initAdapter() {
        adapter = EmprestadosAdapter(this)
        rv_home.layoutManager = LinearLayoutManager(this)
        rv_home.adapter = adapter
    }

    override fun onItemDeleted(emprestadosModel: EmprestadosModel, position: Int) {
        emprestadosModel.id?.let { vm.deletePost(it) }
        vm.emprestadosModelListLiveData?.observe(this, Observer {
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
