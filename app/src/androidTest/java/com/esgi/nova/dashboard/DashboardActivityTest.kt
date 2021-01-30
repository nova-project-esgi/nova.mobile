package com.esgi.nova.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.esgi.nova.ui.dashboard.DashBoardActivityModule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
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