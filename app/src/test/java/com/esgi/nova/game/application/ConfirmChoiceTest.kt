package com.esgi.nova.game.application


import com.nhaarman.mockitokotlin2.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.games.application.ConfirmChoice
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository_Factory
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
@Suppress("UNCHECKED_CAST")
class ConfirmChoiceTest {


    val sut = ConfirmChoice(
        gameDbRepository = mock {  },
        gameResourceDbRepository = mock {  },
        choiceResourceDbRepository = mock {  },
        userRepository = mock {  },
        updateGameToApi = mock {  }
    )

    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2)
    }

}