package com.example.searchgifs.ui.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.searchgifs.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    val list: MutableState<MainState> = mutableStateOf(MainState())

    fun getTrendList(q:String)=viewModelScope.launch{ //Not complete function to show trend gifs
        list.value = MainState(isLoading = true)
        try{

             val result = mainRepository.getTrendItems(q)
            when(result){
                is Resource.Error->{
                    list.value = MainState(error = "Something wrong")
                }
                is Resource.Success->{
                    result.data?.data.let {
                        list.value = MainState(data = ArrayList(it))
                    }

                }
            }
        }catch (e:Exception){
            list.value = MainState(error = "Something wrong")
        }
    }


    fun getImageList(q:String)=viewModelScope.launch{

        list.value = MainState(isLoading = true)
        try{
            val result = mainRepository.getQueryItems(q)
           // val result = mainRepository.getTrendItems(q)
            when(result){
                is Resource.Error->{
                    list.value = MainState(error = "Something wrong")
                }
                is Resource.Success->{
                    result.data?.data.let {
                        list.value = MainState(data = ArrayList(it))
                    }

                }
            }
        }catch (e:Exception){
            list.value = MainState(error = "Something wrong")
        }



    }


}