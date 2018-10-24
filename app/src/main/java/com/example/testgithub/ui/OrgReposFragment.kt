package com.example.testgithub.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.testgithub.AppExecutors
import com.example.testgithub.R
import com.example.testgithub.dependencyinjection.Injectable
import kotlinx.android.synthetic.main.fragment_orgrepos_list.view.*
import kotlinx.android.synthetic.main.include_appbar.view.*
import javax.inject.Inject


class OrgReposFragment : Fragment(), Injectable {

    // variable that we shall initialize at a later point in code
    lateinit var orgReposViewModel:OrgReposViewModel

    private var orgReposRecyclerViewAdapter: OrgReposRecyclerViewAdapter? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_orgrepos_list, container, false)

        val recyclerView = view.recycler_view

        view.title.text = getString(R.string.orgrepos_title)

        // Set the orgReposRecyclerViewAdapter
        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,false)
                orgReposRecyclerViewAdapter = OrgReposRecyclerViewAdapter(appExecutors)
                adapter = orgReposRecyclerViewAdapter

            }
        }

        view.swipeview.setOnRefreshListener {
            orgReposViewModel.refresh()
            view.swipeview.isRefreshing = false
        }

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        orgReposViewModel = ViewModelProviders.of(this, viewModelFactory).get(OrgReposViewModel::class.java)

        orgReposViewModel.setOrganisation("googlesamples")

        initListData()

    }

    private fun initListData() {
        orgReposViewModel.orgReposList.observe(this, Observer {it->
            orgReposRecyclerViewAdapter?.submitList(it?.data)

        })


    }



}
