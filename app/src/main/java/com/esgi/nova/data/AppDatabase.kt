import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esgi.nova.events.infrastructure.data.EventDAO
import com.esgi.nova.data.dao.ResourceDAO
import com.esgi.nova.events.infrastructure.data.Event
import com.esgi.nova.data.entities.Resource
import com.esgi.nova.difficulties.infrastructure.data.Difficulty
import com.esgi.nova.difficulties.infrastructure.data.DifficultyDAO
import com.esgi.nova.events.infrastructure.data.Choice
import com.esgi.nova.events.infrastructure.data.ChoiceDAO

@Database(
    entities = [
        Event::class,
        Resource::class,
        Choice::class,
        Difficulty::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDAO(): EventDAO
    abstract fun resourceDAO(): ResourceDAO
    abstract fun choiceDAO(): ChoiceDAO
    abstract fun difficultyDAO() : DifficultyDAO

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