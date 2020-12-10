package com.meksconway.marvelcase.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.meksconway.marvelcase.data.repository.CharacterDetailRepository
import com.meksconway.marvelcase.data.repository.CharacterDetailUiModel
import com.meksconway.marvelcase.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cache
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel
    @ViewModelInject constructor(
        private val repository: CharacterDetailRepository,
        @Assisted private val handler: SavedStateHandle
    ): ViewModel() {


    private val _characterData = MutableLiveData<Resource<List<CharacterDetailUiModel>>>()
    val characterData: LiveData<Resource<List<CharacterDetailUiModel>>>
        get() = _characterData


    fun getCharacter(characterId: Int) {
        handler.set(CHARACTER_ID, characterId)
        if (handler.get<Boolean>(IS_CALLED) == true) return
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) {
                handler.get<Int>(CHARACTER_ID)?.let { chId ->
                    repository.getCharacterDetail(chId)
                }
            }
            data?.collectLatest { uiModel ->
                handler.set(IS_CALLED, true)
                _characterData.value = uiModel
            }
        }
    }

    companion object {
        const val CHARACTER_ID = "character_id"
        const val IS_CALLED = "is_called"
    }



}