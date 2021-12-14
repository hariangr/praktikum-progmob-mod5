package com.harianugrah.ngampus.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harianugrah.ngampus.R
import com.harianugrah.ngampus.models.User


class ListAdapter(private val userlist: List<User>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val liImage = itemView.findViewById<ImageView>(R.id.liImage)
        val liTitle = itemView.findViewById<TextView>(R.id.liTitle)
        val liRight = itemView.findViewById<TextView>(R.id.liRight)
        val liDesc = itemView.findViewById<TextView>(R.id.liDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val viewer = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(viewer)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val it = userlist[position]

        holder.liTitle.text = it.nick
        holder.liRight.text = it.nim
        holder.liDesc.text = it.name

        try {
            val decodedString: ByteArray = Base64.decode(it.avatar_b64, Base64.DEFAULT)
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            holder.liImage.setImageBitmap(decodedByte)
        } finally {

        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}