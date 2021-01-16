package com.esgi.nova.infrastructure.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.esgi.nova.events.infrastructure.data.events.EventDAO
import com.esgi.nova.resources.infrastructure.data.ResourceDAO
import com.esgi.nova.events.infrastructure.data.events.EventEntity
import com.esgi.nova.resources.infrastructure.data.ResourceEntity
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyEntity
import com.esgi.nova.difficulties.infrastructure.data.difficulty.DifficultyDAO
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceEntity
import com.esgi.nova.difficulties.infrastructure.data.difficulty_resource.DifficultyResourceDAO
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceEntity
import com.esgi.nova.events.infrastructure.data.choice_resource.ChoiceResourceDAO
import com.esgi.nova.events.infrastructure.data.choices.ChoiceEntity
import com.esgi.nova.events.infrastructure.data.choices.ChoiceDAO
import com.esgi.nova.games.infrastructure.data.game.GameDao
import com.esgi.nova.games.infrastructure.data.game.GameEntity
import com.esgi.nova.games.infrastructure.data.game_event.GameEventDao
import com.esgi.nova.games.infrastructure.data.game_event.GameEventEntity
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceDao
import com.esgi.nova.games.infrastructure.data.game_resource.GameResourceEntity
import com.esgi.nova.languages.infrastructure.data.LanguageEntity
import com.esgi.nova.languages.infrastructure.data.LanguageDAO

@Database(
    entities = [
        EventEntity::class,
        ResourceEntity::class,
        ChoiceEntity::class,
        LanguageEntity::class,
        DifficultyEntity::class,
        DifficultyResourceEntity::class,
        ChoiceResourceEntity::class,
        GameEntity::class,
        GameEventEntity::class,
        GameResourceEntity::class
    ],
    version = 1
)
@TypeConverters(UUIDConverter::class, DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDAO(): EventDAO
    abstract fun resourceDAO(): ResourceDAO
    abstract fun choiceDAO(): ChoiceDAO
    abstract fun difficultyDAO(): DifficultyDAO
    abstract fun languageDAO(): LanguageDAO
    abstract fun difficultyResourceDAO(): DifficultyResourceDAO
    abstract fun choiceResourceDAO(): ChoiceResourceDAO
    abstract fun gameDao(): GameDao
    abstract fun gameEventDao(): GameEventDao
    abstract fun gameResourceDao(): GameResourceDao

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