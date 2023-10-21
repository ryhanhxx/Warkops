package com.ch.flavourfair.presentation.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch.flavourfair.data.local.datastore.UserPreferenceDataSource
import com.ch.flavourfair.data.repository.ProductRepository
import com.ch.flavourfair.model.Category
import com.ch.flavourfair.model.Product
import com.ch.flavourfair.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: ProductRepository) : ViewModel() {
    /*val homeData: LiveData<List<Product>>
        get() = repo.getProducts().map {
            it.payload ?: emptyList()
        }.asLiveData(Dispatchers.IO)*/

    /*val productData : LiveData<ResultWrapper<List<Product>>>
        get() = repo.getProducts().asLiveData(Dispatchers.IO)*/

    private val _categories = MutableLiveData<ResultWrapper<List<Category>>>()
    val categories : LiveData<ResultWrapper<List<Category>>>
        get() = _categories

    private val _products = MutableLiveData<ResultWrapper<List<Product>>>()
    val products : LiveData<ResultWrapper<List<Product>>>
        get() = _products

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getCategories().collect{
                _categories.postValue(it)
            }
        }
    }

    fun getProducts(category: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getProducts(if(category == "all") null else category).collect{
                _products.postValue(it)
            }
        }
    }
}

