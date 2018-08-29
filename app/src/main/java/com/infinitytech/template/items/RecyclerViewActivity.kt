package com.infinitytech.template.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.infinitytech.template.BaseActivity
import com.infinitytech.template.R
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.item_test.view.*

class RecyclerViewActivity : BaseActivity() {

    var data = arrayListOf(
            "Zeus" to "Zeus is the God of all in Greece",
            "Thor" to "Thor is the God of Thunder",
            "Iron Man" to "Tony Stark is the only Iron Man",
            "Captain America" to "Captain can fight any enemy"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = HeroAdapter(data)
        rv.adapter = adapter
        var count = 0
        addItemFab.setOnClickListener {
            count++
            val newData = ArrayList(adapter.data)
            newData.add(0, "Odin" to "The all father +$count")
            val result = DiffUtil.calculateDiff(DiffCallback(adapter.data, newData), true)
            adapter.data = newData
            result.dispatchUpdatesTo(adapter)
        }
    }

    class HeroAdapter(var data: List<Pair<String, String>>) : RecyclerView.Adapter<HeroAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_test, parent, false))
        }

        override fun getItemCount() = data.count()

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.title = data[position].first
            holder.description = data[position].second
        }

        class Holder(root: View) : RecyclerView.ViewHolder(root) {
            private val titleTv = root.titleTv
            private val descriptionTv = root.descriptionTv
            var title: String
                set(value) {
                    titleTv.text = value
                }
                get() = titleTv.text.toString()
            var description: String
                set(value) {
                    descriptionTv.text = value
                }
                get() = descriptionTv.text.toString()
        }
    }

    class DiffCallback<T>(val old: List<T>, val new: List<T>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = (old[oldItemPosition] == new[newItemPosition])

        override fun getOldListSize() = old.count()

        override fun getNewListSize() = new.count()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = (old[oldItemPosition] == new[newItemPosition])
    }

    class ItemAnimator: SimpleItemAnimator() {
        override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun runPendingAnimations() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun animateMove(holder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun animateChange(oldHolder: RecyclerView.ViewHolder?, newHolder: RecyclerView.ViewHolder?, fromLeft: Int, fromTop: Int, toLeft: Int, toTop: Int): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun isRunning(): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun endAnimation(item: RecyclerView.ViewHolder) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun endAnimations() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
