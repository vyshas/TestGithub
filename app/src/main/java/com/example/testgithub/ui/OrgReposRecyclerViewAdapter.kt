package com.example.testgithub.ui


import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testgithub.AppExecutors
import com.example.testgithub.R
import com.example.testgithub.model.OrgRepos
import kotlinx.android.synthetic.main.item_orgrepos.view.*

class OrgReposRecyclerViewAdapter(appExecutors: AppExecutors) : ListAdapter<OrgRepos, OrgReposRecyclerViewAdapter.OrgReposViewHolder>(DiffCallback()){

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as OrgRepos

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgReposViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orgrepos, parent, false)
        return OrgReposViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrgReposViewHolder, position: Int) {
        val item = getItem(position)
        holder.nameView.text = item.name
        holder.descriptionView.text = if (TextUtils.isEmpty(item.description)) holder.view.context.getString(R.string.no_desc_available) else item.description
        holder.languageView.text = item.language
        holder.forkView.text = item.forksCount.toString()
        holder.starView.text = item.stargazersCount.toString()


        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }



    inner class OrgReposViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.repos_name
        val descriptionView: TextView = view.repos_description
        val languageView: TextView = view.repos_language

        val starView: TextView = view.repos_star
        val forkView: TextView = view.repos_fork


        override fun toString(): String {
            return super.toString() + " '" + descriptionView.text + "'"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<OrgRepos>() {
        override fun areItemsTheSame(orgRepos1: OrgRepos, orgRepos2: OrgRepos): Boolean {
            return orgRepos1.id == orgRepos2.id
        }

        override fun areContentsTheSame(orgRepos1: OrgRepos, orgRepos2: OrgRepos): Boolean {
            return orgRepos1 == orgRepos2
        }

    }


}
