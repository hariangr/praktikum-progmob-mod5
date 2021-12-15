package com.harianugrah.haemtei

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harianugrah.haemtei.models.AppDatabase
import com.mancj.materialsearchbar.MaterialSearchBar

class ListActivity : AppCompatActivity() {
//class ListActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {
//    var roomUsers: ArrayList<RoomUser> = ArrayList();
//
//    lateinit var searchBar: MaterialSearchBar;
//    lateinit var recyclerView: RecyclerView;
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_list)
//
////        for (i in 0..20) {
////            users.add(User("Nama", "Nick", "NIM", false, "", 19))
////        }
//        roomUsers.addAll(AppDatabase.getInstance(this).userDao().getAll());
//
//        recyclerView = findViewById<RecyclerView>(R.id.recyclerList)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter =
//            ListAdapter(roomUsers, fun(it) {
//                val intent = Intent(this, DetailActivity::class.java);
//                intent.putExtra(DetailActivity.INTENT_USER_ID, it.uid);
//                finish();
//                startActivity(intent);
//
//            })
//
//        searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
//        searchBar.setOnSearchActionListener(this)
//    }
//
//    override fun onSearchStateChanged(enabled: Boolean) {
//        val s = if (enabled) "enabled" else "disabled"
//        Toast.makeText(this, "Search $s", Toast.LENGTH_SHORT).show()
//        if (!enabled) {
//            roomUsers.clear();
//            roomUsers.addAll(AppDatabase.getInstance(this).userDao().getAll());
//            recyclerView.adapter?.notifyDataSetChanged();
//        }
//    }
//
//    override fun onSearchConfirmed(text: CharSequence?) {
//        roomUsers.clear();
//        roomUsers.addAll(AppDatabase.getInstance(this).userDao().findByName(text.toString()));
//
//        Log.v("ppp", roomUsers.toString())
//        recyclerView.adapter?.notifyDataSetChanged();
//    }
//
//    override fun onButtonClicked(buttonCode: Int) {}
}