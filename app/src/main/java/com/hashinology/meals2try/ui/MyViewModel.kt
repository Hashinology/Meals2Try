package com.hashinology.meals2try.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiltretrofitproject.model.Meal
import com.example.hiltretrofitproject.model.Meals
import com.hashinology.meals2try.api.MyRepository
import com.hashinology.meals2try.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val repo: MyRepository): ViewModel() {
    private val _mealsLivedata = MutableLiveData<Resource<Meals>>(Resource.Loading())
    val mealsLivedata: LiveData<Resource<Meals>> = _mealsLivedata

    val mealsDBLiveData: LiveData<List<Meal>>

    init {
        mealsDBLiveData = repo.getDbMeals()
    }

    fun getMeals() = viewModelScope.launch(Dispatchers.IO){
        _mealsLivedata.postValue(Resource.Loading())

        val response = repo.getMeals()

        try {
            if (response.isSuccessful){
                _mealsLivedata.postValue(Resource.Success(response.body()!!))
            }else{
                _mealsLivedata.postValue(Resource.Error(response.message()))
            }
        }catch (t: Throwable){
            when(t){
                is IOException -> _mealsLivedata.postValue(Resource.Error("No Internet"))
                else -> _mealsLivedata.postValue(Resource.Error(t.message.toString()))
            }
        }
    }

    fun addMeals(meal: Meal) = viewModelScope.launch(Dispatchers.IO){
        repo.insertedMeals(meal)
    }

    fun removeMeal(meal:Meal) = viewModelScope.launch(Dispatchers.IO){
        repo.deletedMeals(meal)
    }
}