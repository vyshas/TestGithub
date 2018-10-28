package com.example.testgithub.ui

import android.app.Application
import android.arch.lifecycle.*
import android.support.annotation.VisibleForTesting
import android.text.TextUtils
import com.example.testgithub.model.OrgRepos
import com.example.testgithub.model.Resource
import com.example.testgithub.repository.OrgReposRepository
import com.example.testgithub.util.AbsentLiveData
import javax.inject.Inject

class OrgReposViewModel @Inject constructor(private val repository: OrgReposRepository, val context: Application): AndroidViewModel(context) {


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

    @VisibleForTesting
    fun retry() {
        if (this.organisation.value != null) {
            this.organisation.value = this.organisation.value
        }
    }


}