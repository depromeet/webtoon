package com.depromeet.webtoon.batch.support

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersIncrementer

internal class ParamCleanRunIdIncrementer : JobParametersIncrementer {

    private var key = RUN_ID_KEY
    fun setKey(key: String) {
        this.key = key
    }

    override fun getNext(parameters: JobParameters?): JobParameters {
        val params = parameters ?: JobParameters()
        val id = (params.getLong(key) ?: 0L) + 1L
        return JobParametersBuilder().addLong(key, id).toJobParameters()
    }

    companion object {
        private const val RUN_ID_KEY = "run.id"
    }
}
