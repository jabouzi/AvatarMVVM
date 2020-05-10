package com.raywenderlich.android.creaturemon.viewmodel

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.raywenderlich.android.creaturemon.model.Creature
import com.raywenderlich.android.creaturemon.model.CreatureRepository
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

class AllCreaturesViewModel(private val repository: CreatureRepository = RoomRepository()): ViewModel() {
    private val allCreatures = MediatorLiveData<List<Creature>>()

    fun getAllCreatures() = allCreatures

    fun getAllCreatureList() {
        allCreatures.addSource(repository.getAllCreatures()) {creatures ->
            allCreatures.postValue(creatures)
        }
    }

    fun clearAllCreatures() {
        repository.clearAllCreatures()
    }
}