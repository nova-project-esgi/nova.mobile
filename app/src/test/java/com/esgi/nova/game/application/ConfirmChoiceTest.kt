package com.esgi.nova.game.application


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDbRepository
import com.nhaarman.mockitokotlin2.*
import com.esgi.nova.games.application.ConfirmChoice
import com.esgi.nova.games.infrastructure.data.game.GameDbRepository
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDbRepository
import com.esgi.nova.users.infrastructure.data.UserStorageRepository
import com.esgi.nova.users.ui.LoginActivityModule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
class ConfirmChoiceTest {




    @Test
    fun addition_isCorrect() {
//        val gameDbRepository = mock<GameDbRepository>{}
//        val gameResourceDbRepository = mock<GameResourceDbRepository>{}
//        val choiceResourceDbRepository = mock<ChoiceResourceDbRepository>{}
//        val gameDbRepository = mock<UserStorageRepository>{}
//        val gameDbRepository = mock<GameDbRepository>{}
//        val sut = ConfirmChoice(
//            gameDbRepository = mock {  },
//            gameResourceDbRepository = mock {  },
//            choiceResourceDbRepository = mock {  },
//            userRepository = mock {  },
//            updateGameToApi = mock {  }
//        )
        Assert.assertEquals(5, 2 + 2)
    }

}