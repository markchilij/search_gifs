package com.example.searchgifs.ui.components

import com.example.searchgifs.network.ApiService
import com.example.searchgifs.network.responses.GifsResponse
import com.example.searchgifs.util.Constant
import com.example.searchgifs.util.Resource
import java.lang.Exception
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService:ApiService) {


    suspend fun getQueryItems(q:String):Resource<GifsResponse>{
        return  try{

            val result = apiService.searchGifs(query = q, apiKey = Constant.KEY)
            Resource.Success(data = result)
        }catch (e:Exception){
            Resource.Error(message = e.message.toString())
        }
    }


    suspend fun getTrendItems(q:String):Resource<GifsResponse>{
        return  try{

            val result = apiService.getQueryImages(apiKey = Constant.KEY, limit = 20)
            Resource.Success(data = result)
        }catch (e:Exception){
            Resource.Error(message = e.message.toString())
        }
    }


}
