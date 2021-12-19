package com.harianugrah.haemtei.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.harianugrah.haemtei.Constant
import com.harianugrah.haemtei.R
import com.harianugrah.haemtei.models.Oprec
import com.squareup.picasso.Picasso
import java.util.*


class ListAdapter(
    private val userlist: List<Oprec>,
    val onItemClickListener: (Oprec) -> Unit,
) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val liImage = itemView.findViewById<ImageView>(R.id.liImage)
        val liTitle = itemView.findViewById<TextView>(R.id.licUsername)
        val liRight = itemView.findViewById<TextView>(R.id.liRight)
        val liDesc = itemView.findViewById<TextView>(R.id.liDesc)
        val liDesc2 = itemView.findViewById<TextView>(R.id.licEmail)
        val liCardContainer = itemView.findViewById<CardView>(R.id.licCardContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val viewer = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ListViewHolder(viewer)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = userlist[position]

        holder.liTitle.text = item.title

        holder.liRight.text = item.owner?.username

        val dateStart = item.startDate?.split("T")?.get(0)
        val dateEnd = item.startDate?.split("T")?.get(0)
        holder.liDesc.text = """Dimulai dari $dateStart
            |Ditutup pada $dateEnd
            |${if (item.description != null) item.description else ""}
        """.trimMargin()

        if (item.registered!!) {
            holder.liDesc2.text = "Terdaftar"
            holder.liDesc2.visibility = View.VISIBLE
        } else {
            holder.liDesc2.visibility = View.GONE
        }

        val imgUrl = Constant.BASE_URL + item.thumbnail?.formats?.large?.url
        Log.v("IMG URL", imgUrl)
        Picasso.get().load(imgUrl)
            .into(holder.liImage);
//
//
//        try {
//            val decodedString: ByteArray = Base64.decode(item.avatar_b64, Base64.DEFAULT)
//            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
//            holder.liImage.setImageBitmap(decodedByte)
//
            holder.liCardContainer.setOnClickListener {
                this.onItemClickListener(item)
            }
//        } finally {
//
//        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
}