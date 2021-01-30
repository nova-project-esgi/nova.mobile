package com.esgi.nova

import android.graphics.Bitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.difficulties.ports.IDetailedDifficulty
import com.esgi.nova.files.infrastructure.ports.IFileWrapper
import com.esgi.nova.ui.dashboard.DashBoardActivityModule
import com.esgi.nova.users.ui.LoginActivityModule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith


@Suppress("UNCHECKED_CAST")
@HiltAndroidTest
@UninstallModules(DashBoardActivityModule::class)
@RunWith(AndroidJUnit4::class)
class DashboardActivityTest {

    @Test
    fun resources_should_change_according_to_difficulty() {


//        var resources = mock<IFileWrapper<IDetailedDifficulty.IStartValueResource>> {
//            on {data}.doReturn(mock<IDetailedDifficulty.IStartValueResource> {
//                on { name }.doReturn("ammo")
//            }),
//            on {file}.doReturn(Bitmap.createBitmap)
//        }

    }
}