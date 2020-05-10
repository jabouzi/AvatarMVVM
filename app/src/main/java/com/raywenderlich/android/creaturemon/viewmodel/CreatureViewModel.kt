package com.raywenderlich.android.creaturemon.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.drawable.AdaptiveIconDrawable
import com.raywenderlich.android.creaturemon.model.*
import com.raywenderlich.android.creaturemon.model.room.RoomRepository

class CreatureViewModel(
        private val generator: CreatureGenerator = CreatureGenerator(),
        private val repository: CreatureRepository = RoomRepository()
): ViewModel() {
    private val creatureLiveData = MutableLiveData<Creature>()

    var name = ""
    var intelligence = 0
    var strength = 0
    var endurance = 0
    var drawable = 0

    lateinit var creature: Creature

    fun getCreatureLiveData(): LiveData<Creature> = creatureLiveData

    fun updateCreature() {
        val attributes = CreatureAttributes(intelligence, strength, endurance)
        creature = generator.generateCreature(attributes, name, drawable)
        creatureLiveData.postValue(creature)
    }

    fun attributeSelected(attributeType: AttributeType, position: Int) {
        when(attributeType) {
            AttributeType.INTELLIGENCE ->
                intelligence = AttributeStore.INTELLIGENCE[position].value
            AttributeType.STRENGTH ->
                strength = AttributeStore.STRENGTH[position].value
            AttributeType.ENDURANCE ->
                endurance = AttributeStore.ENDURANCE[position].value
        }
        updateCreature()
    }

    fun drawableSelected(drawable: Int) {
        this.drawable = drawable
        updateCreature()
    }

    fun saveCreature(): Boolean {
        return if (canSaveCreature()) {
            repository.saveCreature(creature)
            true
        } else {
            false
        }
    }

    fun canSaveCreature(): Boolean {
        return intelligence != 0 && strength != 0 &&
                endurance != 0 && name.isNotEmpty() &&
                drawable != 0
    }
}