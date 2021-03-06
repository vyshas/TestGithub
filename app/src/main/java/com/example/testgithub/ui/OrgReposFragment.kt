package com.example.testgithub.ui


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.example.testgithub.model.OrgRepos
import com.example.testgithub.model.Resource
import com.example.testgithub.model.Status
import kotlinx.android.synthetic.main.fragment_orgrepos_list.*
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

    private var layoutView: View? = null


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
        }

        layoutView = view

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        orgReposViewModel = ViewModelProviders.of(this, viewModelFactory).get(OrgReposViewModel::class.java)

        orgReposViewModel.setOrganisation("googlesamples")

        initListData()

    }

    private fun initListData() {
        orgReposViewModel.orgReposList.observe(this, Observer {resource->
            updateStatus(resource)
            orgReposRecyclerViewAdapter?.submitList(resource?.data)

        })


    }

    private fun updateStatus(resource: Resource<List<OrgRepos>>?) {
        when(resource?.status){
            Status.SUCCESS-> {
                swipeview.isRefreshing =false
            }
            Status.LOADING->{
                swipeview.isRefreshing = true
            }
            Status.ERROR->{
                swipeview.isRefreshing = false
                //show error
                showSnackBar(resource.message)
            }
        }

    }


    private fun showSnackBar(message: String?) {
        Snackbar.make(layoutView!!,message!!,Snackbar.LENGTH_LONG).show()
    }


}
