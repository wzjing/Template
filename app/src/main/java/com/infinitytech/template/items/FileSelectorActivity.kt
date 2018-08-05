package com.infinitytech.template.items

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infinitytech.template.R
import kotlinx.android.synthetic.main.activity_file_selector.*
import kotlinx.android.synthetic.main.item_file_list.view.*
import java.io.File

private const val FILE_REQUEST = 0x2001

class FileSelectorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_selector)
        fab.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_GET_CONTENT)
                    .setType("image/jpeg")
                    .addCategory(Intent.CATEGORY_OPENABLE), FILE_REQUEST)
        }
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = FileAdapter(arrayListOf())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILE_REQUEST) {
            data ?: return
            (rv.adapter as FileAdapter).apply {
                this.data.add(Pair(
                        data.data.path.split(File.separator).lastOrNull() ?: "unknown",
                        data.data.path.split(File.pathSeparator).lastOrNull() ?: "unknown"))
                notifyDataSetChanged()
            }
        }
    }
}

class FileAdapter(var data: ArrayList<Pair<String, String>>) : RecyclerView.Adapter<FileAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MyViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_file_list, parent, false))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        data[position].apply {
            holder.title.text = first
            holder.path.text = second
            if (first.split(".").lastOrNull().equals("jpg")) {
                holder.logo.setImageBitmap(BitmapFactory.decodeFile(second))
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.sourceTitle
        val path: TextView = itemView.path
        val logo: ImageView = itemView.logo
    }

}
