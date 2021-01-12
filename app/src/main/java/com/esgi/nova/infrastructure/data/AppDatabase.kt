package com.esgi.nova.infrastructure.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esgi.nova.events.infrastructure.data.events.EventDAO
import com.esgi.nova.resources.infrastructure.data.ResourceDAO
import com.esgi.nova.events.infrastructure.data.events.Event
import com.esgi.nova.resources.infrastructure.data.Resource
import com.esgi.nova.difficulties.infrastructure.data.Difficulty
import com.esgi.nova.difficulties.infrastructure.data.DifficultyDAO
import com.esgi.nova.difficulties.infrastructure.data.DifficultyResource
import com.esgi.nova.difficulties.infrastructure.data.DifficultyResourceDAO
import com.esgi.nova.events.infrastructure.data.choices.Choice
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDAO
import com.esgi.nova.languages.infrastructure.data.Language
import com.esgi.nova.languages.infrastructure.data.LanguageDAO

@Database(
    entities = [
        Event::class,
        Resource::class,
        Choice::class,
        Language::class,
        Difficulty::class,
        DifficultyResource::class],
    version = 1
)
@TypeConverters(UUIDConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDAO(): EventDAO
    abstract fun resourceDAO(): ResourceDAO
    abstract fun choiceDAO(): ChoiceDAO
    abstract fun difficultyDAO(): DifficultyDAO
    abstract fun languageDAO(): LanguageDAO
    abstract fun difficultyResourceDAO(): DifficultyResourceDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "nova"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}