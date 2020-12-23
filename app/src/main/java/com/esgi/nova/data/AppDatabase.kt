import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esgi.nova.data.dao.EventDAO
import com.esgi.nova.data.dao.ResourceDAO
import com.esgi.nova.data.entities.Event
import com.esgi.nova.data.entities.Resource

@Database(entities = [Event::class, Resource::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDAO(): EventDAO
    abstract fun resourceDAO(): ResourceDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "nova")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}