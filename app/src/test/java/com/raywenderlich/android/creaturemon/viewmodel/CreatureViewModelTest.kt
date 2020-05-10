package com.raywenderlich.android.creaturemon.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.android.creaturemon.model.Creature
import com.raywenderlich.android.creaturemon.model.CreatureAttributes
import com.raywenderlich.android.creaturemon.model.CreatureGenerator
import com.raywenderlich.android.creaturemon.model.CreatureRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


internal class CreatureViewModelTest {
    private lateinit var creatureViewModel: CreatureViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockGenerator: CreatureGenerator

    @Mock
    lateinit var repository: CreatureRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        creatureViewModel = CreatureViewModel(mockGenerator, repository)
    }

    @Test
    fun testSetupCreature() {
        val attributes = CreatureAttributes(10, 3, 7)
        val stubCreature = Creature(attributes, 87, "Test Creature")
        Mockito.`when`(mockGenerator.generateCreature(attributes)).thenReturn(stubCreature)
        creatureViewModel.intelligence = 10
        creatureViewModel.strength = 3
        creatureViewModel.endurance = 7

        creatureViewModel.updateCreature()

        assertEquals(stubCreature, creatureViewModel.creature)
    }

    @Test
    fun testCantSaveCreatureWithBlankName () {
        creatureViewModel.intelligence = 10
        creatureViewModel.strength = 3
        creatureViewModel.endurance = 7
        creatureViewModel.drawable = 1
        creatureViewModel.name = ""
        val canSaveCreature = creatureViewModel.canSaveCreature()
        assertEquals(false, canSaveCreature)
    }

    @Test
    fun testCanSaveCreatureWithoutStrenght() {
        creatureViewModel.intelligence = 10
        creatureViewModel.strength = 0
        creatureViewModel.endurance = 7
        creatureViewModel.drawable = 1
        creatureViewModel.name = "Dummy name"
        val canSaveCreature = creatureViewModel.canSaveCreature()
        assertEquals(false, canSaveCreature)
    }

    @Test
    fun testCanSaveCreatureWithoutEndurance() {
        creatureViewModel.intelligence = 10
        creatureViewModel.strength = 3
        creatureViewModel.endurance = 0
        creatureViewModel.drawable = 1
        creatureViewModel.name = "Dummy name"
        val canSaveCreature = creatureViewModel.canSaveCreature()
        assertEquals(false, canSaveCreature)
    }

    @Test
    fun testCanSaveCreatureWithoutIntelligence() {
        creatureViewModel.intelligence = 0
        creatureViewModel.strength = 3
        creatureViewModel.endurance = 7
        creatureViewModel.drawable = 1
        creatureViewModel.name = "Dummy name"
        val canSaveCreature = creatureViewModel.canSaveCreature()
        assertEquals(false, canSaveCreature)
    }

    @Test
    fun testCanSaveCreatureWithoutDrawable() {
        creatureViewModel.intelligence = 10
        creatureViewModel.strength = 3
        creatureViewModel.endurance = 7
        creatureViewModel.drawable = 0
        creatureViewModel.name = "Dummy name"
        val canSaveCreature = creatureViewModel.canSaveCreature()
        assertEquals(false, canSaveCreature)
    }

}