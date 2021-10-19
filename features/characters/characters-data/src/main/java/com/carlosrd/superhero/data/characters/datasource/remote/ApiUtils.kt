package com.carlosrd.superhero.data.characters.datasource.remote

import com.carlosrd.superhero.data.characters.datasource.remote.model.ApiKeysDTO
import com.carlosrd.superhero.data.characters.di.annotation.MarvelPrivateKey
import com.carlosrd.superhero.data.characters.di.annotation.MarvelPublicKey
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiUtils @Inject constructor(
    private val apiKeys : ApiKeysDTO
) {

    fun getCommonParams() : Map<String, String> {

        val ts = getTimestamp()

        val params = HashMap<String, String>()

        params["apikey"] = apiKeys.publicKey
        params["ts"] = ts
        params["hash"] = md5(ts + apiKeys.privateKey + apiKeys.publicKey)!!

        return params

    }

    private fun getTimestamp() : String = Calendar.getInstance().timeInMillis.toString()

    fun md5(input: String): String? {
        return try {
            val md = MessageDigest.getInstance("MD5")
            val md5Data = BigInteger(1, md.digest(input.toByteArray()))
            String.format("%032x", md5Data)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }
    }
}