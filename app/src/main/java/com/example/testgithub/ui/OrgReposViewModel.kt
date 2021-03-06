package com.example.testgithub.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.example.testgithub.model.OrgRepos
import com.example.testgithub.model.Resource
import com.example.testgithub.repository.OrgReposRepository
import com.example.testgithub.util.AbsentLiveData
import javax.inject.Inject

class OrgReposViewModel @Inject constructor(private val repository: OrgReposRepository): ViewModel() {


    private val organisation = MediatorLiveData<String>()

    private var cacheOrganisation: String? = null

    val orgReposList : LiveData<Resource<List<OrgRepos>>> = Transformations.switchMap(organisation){
        orgRepos ->
        if (orgRepos.isNullOrBlank()) {
            AbsentLiveData.create()
        } else {
            repository.loadRepos(orgRepos)

        }

    }



    fun setOrganisation(org: String?) {
        cacheOrganisation = org
        organisation.value = org
    }

    fun refresh(){
        //organisation.value?.let { organisation.value = it }
        if (!TextUtils.isEmpty(cacheOrganisation)) setOrganisation(cacheOrganisation)
    }


}