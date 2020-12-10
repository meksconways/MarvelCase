package com.meksconway.marvelcase.util

import com.meksconway.marvelcase.BuildConfig
import java.security.MessageDigest

object QueryParameter {

    const val LIMIT = 30

    fun getQueryMap(): MutableMap<String, Any> {
        val queryMap = hashMapOf<String, Any>()
        val ts = (System.currentTimeMillis() / 1000).toString()
        val hash = (ts + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY).md5()
        queryMap["ts"] = ts
        queryMap["apikey"] = BuildConfig.PUBLIC_KEY
        queryMap["hash"] = hash
        return queryMap
    }

    private fun String.md5(): String {
        return MessageDigest.getInstance("MD5").digest(this.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
    }

}
