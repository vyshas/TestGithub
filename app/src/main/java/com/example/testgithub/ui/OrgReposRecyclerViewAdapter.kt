package com.example.testgithub.ui

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.testgithub.R
import com.example.testgithub.model.OrgRepos

import kotlinx.android.synthetic.main.item_orgrepos.view.*

class OrgReposRecyclerViewAdapter : RecyclerView.Adapter<OrgReposRecyclerViewAdapter.ViewHolder>() {

    private var orgReposList:List<OrgRepos> = emptyList()

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as OrgRepos

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orgrepos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = orgReposList[position]
        holder.nameView.text = item.name
        holder.descriptionView.text = if(TextUtils.isEmpty(item.description)) holder.view.context.getString(R.string.no_desc_available) else item.description
        holder.languageView.text = item.language
        holder.forkView.text = item.forksCount.toString()
        holder.starView.text = item.stargazersCount.toString()



        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = orgReposList.size


     fun submitList(data: List<OrgRepos>) {
         orgReposList = data
        notifyDataSetChanged()

    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.repos_name
        val descriptionView: TextView = view.repos_description
        val languageView : TextView = view.repos_language

        val starView : TextView = view.repos_star
        val forkView : TextView = view.repos_fork


        override fun toString(): String {
            return super.toString() + " '" + descriptionView.text + "'"
        }
    }
}
