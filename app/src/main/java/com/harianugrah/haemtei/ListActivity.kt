package com.harianugrah.haemtei

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.harianugrah.haemtei.adapter.ListAdapter
import com.harianugrah.haemtei.models.*
import com.mancj.materialsearchbar.MaterialSearchBar

class ListActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {
    val TAG = "LOGIN_MAINACT";
    lateinit var queue: RequestQueue;

    var oprecs: ArrayList<Oprec> = ArrayList();

    lateinit var searchBar: MaterialSearchBar;
    lateinit var recyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        queue = Volley.newRequestQueue(this)

        recyclerView = findViewById<RecyclerView>(R.id.recyclerList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            ListAdapter(this, oprecs, fun(it) {
                val intent = Intent(this, DetailActivity::class.java);
                intent.putExtra(DetailActivity.INTENT_OPREC_ID, it.id);
                finish();
                startActivity(intent);

            })

        searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
        searchBar.setOnSearchActionListener(this)

        doSearch(null)
    }

    fun doSearch(q: String?) {
        Log.v("lkjn", Constant.EP_FIND_OPREC)
        val loginReq = object : JsonObjectRequest(

            Request.Method.GET, Constant.EP_FIND_OPREC, null,
            {
                val res = Gson().fromJson(it.toString(), OprecsResult::class.java)

                oprecs.clear()
                oprecs.addAll(res.data)
                recyclerView.adapter?.notifyDataSetChanged();
            },
            {

                Snackbar.make(
                    recyclerView,
                    "Ada yang salah",
                    Snackbar.LENGTH_SHORT
                ).show();

            },
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                val token = AuthSingleton.currentUser!!.jwtToken;
                headers["Authorization"] = "Bearer $token"
                return headers
            }
        }

        loginReq.tag = TAG

        queue.add(loginReq)
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        val s = if (enabled) "enabled" else "disabled"
        Toast.makeText(this, "Search $s", Toast.LENGTH_SHORT).show()
        if (!enabled) {
            doSearch(null)
        }
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        doSearch(text.toString())
    }

    override fun onButtonClicked(buttonCode: Int) {}
}