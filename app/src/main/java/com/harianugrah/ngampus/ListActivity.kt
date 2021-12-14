package com.harianugrah.ngampus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harianugrah.ngampus.adapter.ListAdapter
import com.harianugrah.ngampus.models.AppDatabase
import com.harianugrah.ngampus.models.User

class ListActivity : AppCompatActivity() {
    lateinit var users: List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

//        for (i in 0..20) {
//            users.add(User("Nama", "Nick", "NIM", false, "", 19))
//        }
        users = AppDatabase.getInstance(this).userDao().getAll()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            ListAdapter(users, fun(it) {
                Log.v("ZZZZ", it.toString())
            })
    }
}