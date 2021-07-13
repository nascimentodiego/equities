package br.com.dfn.equities.ui.home

import androidx.lifecycle.MutableLiveData
import br.com.dfn.equities.repositories.equities.EquitiesRepository
import br.com.dfn.equities.ui.base.BaseViewModel
import br.com.dfn.equities.ui.model.Equities
import br.com.dfn.equities.repositories.Result

class EquitiesViewModel(private val repository: EquitiesRepository) : BaseViewModel() {

    private val _equities = MutableLiveData<Result<List<Equities>>>()
    val equities: MutableLiveData<Result<List<Equities>>> get() = _equities

    fun getEquities() = doOnViewModelScope {
        _equities.postValue(Result.Loading)
        when (val response = repository.getEquities()) {
            is Result.Success -> {
                val equities = response.data.map(::Equities)
                _equities.postValue(Result.Success(equities))
            }
            is Result.GenericError -> {
                _equities.postValue(Result.GenericError)
            }
        }
    }
}