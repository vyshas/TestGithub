package com.example.testgithub.services

import com.example.testgithub.repository.OrgReposRepository
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import timber.log.Timber

//TODO
class ReposService(private val orgReposRepository: OrgReposRepository) : JobService() {

    override fun onStopJob(params: JobParameters?): Boolean {
        return true

    }

    override fun onStartJob(jobParameters: JobParameters?): Boolean {
        Timber.d("ReposService job started")
        //FIXME change hardcoded value
        orgReposRepository.refreshOrgRepos("googlesamples")

        jobParameters?.let { jobFinished(it, false) }

        return true

    }


}
