package com.harianugrah.haemtei.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.harianugrah.haemtei.R
import com.harianugrah.haemtei.models.AuthX
import java.util.*


class ListAdapterAccount(
    private val authXlist: List<AuthX>,
    val onItemClickListener: (AuthX) -> Unit,
    val onEditClickListener: (AuthX) -> Unit,
    val onDeleteClickListener: (AuthX) -> Unit,
) :
    RecyclerView.Adapter<ListAdapterAccount.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val licUsername = itemView.findViewById<TextView>(R.id.licEmail)
        val licEmail = itemView.findViewById<TextView>(R.id.licUsername)
        val licCardContainer = itemView.findViewById<CardView>(R.id.licCardContainer)
        val licDelete = itemView.findViewById<ImageButton>(R.id.licDelete)
        val licEdit = itemView.findViewById<ImageButton>(R.id.licEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val viewer =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_account, parent, false)
        return ListViewHolder(viewer)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = authXlist[position]

        holder.licUsername.text = item.username

        holder.licEmail.text = item.email

        holder.licCardContainer.setOnClickListener {
            this.onItemClickListener(item)
        }

        holder.licEdit.setOnClickListener {
            this.onEditClickListener(item)
        }
        
        holder.licDelete.setOnClickListener {
            this.onDeleteClickListener(item)
        }
    }

    override fun getItemCount(): Int {
        return authXlist.size
    }
}