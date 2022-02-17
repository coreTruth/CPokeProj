package com.example.myapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.data.PokemonItemDetail
import com.example.myapplication.data.PokemonItemUI
import com.example.myapplication.data.PokemonPageResponse
import com.example.myapplication.repository.PokemonRepository
import com.example.myapplication.repository.network.ApiInterface
import com.example.myapplication.repository.network.NetworkResource
import com.example.myapplication.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    val fetchPageDataState = SingleLiveEvent<FetchPageDataState>()

    private val _itemUIs: MutableLiveData<List<PokemonItemUI>> = MutableLiveData(emptyList())
    val itemUIs: LiveData<List<PokemonItemUI>> get() = _itemUIs

    private var nextUrl: String? = null

    init {
        fetchPageData(ApiInterface.BASE_URL)
    }

    fun fetchNextPage() = fetchPageData(nextUrl)

    private fun fetchPageData(url: String?) = viewModelScope.launch {
        _loading.value = true
        when (val pageResponse = PokemonRepository.getPage(url)) {
            is NetworkResource.Success -> handleSuccess(pageResponse.value, this)
            is NetworkResource.Failure -> handleError()
        }

        _loading.value = false
    }

    private suspend fun handleSuccess(
        pageResponse: PokemonPageResponse,
        coroutineScope: CoroutineScope
    ) {
        nextUrl = pageResponse.next
        val newItemUis = pageResponse.results.map { item -> item.toPokemonItemUI() }
        val deferredList: MutableList<Deferred<NetworkResource<PokemonItemDetail>>> =
            mutableListOf()

        for (item in newItemUis) {
            deferredList.add(coroutineScope.async { PokemonRepository.getItemDetail(item.url) })
        }

        for (deferredResponse in deferredList) {
            when (val detailItemResponse = deferredResponse.await()) {
                is NetworkResource.Success -> with(detailItemResponse.value) {

                    newItemUis.firstOrNull { item -> item.name == name }?.let {
                        it.ability = abilities
                        it.stats = stats
                        it.icon = getIconUrl()
                        it.typeString = getTypesString()
                    }
                }
                is NetworkResource.Failure -> Unit
            }
        }

        _itemUIs.value = _itemUIs.value?.filter { item -> !item.isLoadMoreButton }
            ?.toMutableList()
            ?.apply {
                addAll(newItemUis)
                if (nextUrl != null) {
                    add(PokemonItemUI(isLoadMoreButton = true))
                }
            }

        fetchPageDataState.value = FetchPageDataState.Success
    }

    private fun handleError() {
        fetchPageDataState.value = FetchPageDataState.Error(R.string.fail_fetch)
    }

    sealed class FetchPageDataState {
        object Success : FetchPageDataState()
        data class Error(val errorMessage: Int? = null) : FetchPageDataState()
    }
}