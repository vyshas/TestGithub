package com.example.testgithub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testgithub.AppExecutors
import com.example.testgithub.R
import com.example.testgithub.dependencyinjection.Injectable
import com.example.testgithub.model.OrgRepos
import com.example.testgithub.model.Resource
import com.example.testgithub.model.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_orgrepos_list.view.*
import kotlinx.android.synthetic.main.include_appbar.view.*
import javax.inject.Inject


class OrgReposFragment : Fragment(), Injectable {

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
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
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
                layoutView?.swipeview?.isRefreshing =false
            }
            Status.LOADING->{
                layoutView?.swipeview?.isRefreshing = true
            }
            Status.ERROR->{
                layoutView?.swipeview?.isRefreshing = false
                //show error
                showSnackBar(resource.message)
            }
        }

    }


    private fun showSnackBar(message: String?) {
        Snackbar.make(layoutView!!,message!!,Snackbar.LENGTH_LONG).show()
    }


}
