package es.iessaladillo.pedrojoya.quilloque.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.iessaladillo.pedrojoya.quilloque.data.dao.ConsultasDao
import es.iessaladillo.pedrojoya.quilloque.data.model.Call
import es.iessaladillo.pedrojoya.quilloque.data.model.Contact

/* Consulta para llamadas recientes
   "SELECT C.id AS callId, C.phoneNumber AS phoneNumber, C.type AS type, \n" +
   "C.date AS date, C.time AS time, T.id AS contactId, T.name AS contactName \n" +
   "FROM Call AS C LEFT JOIN Contact AS T ON C.phoneNumber = T.phoneNumber \n" +
   "ORDER BY C.id DESC LIMIT :limit"
*/

/* Consulta para sugerencias
   "SELECT name AS contactName, phoneNumber AS phoneNumber " +
   "FROM Contact " +
   "WHERE phoneNumber like :phoneNumber " +
   "UNION " +
   "SELECT DISTINCT phoneNumber AS contactName, phoneNumber AS phoneNumber " +
   "FROM Call " +
   "WHERE phoneNumber like :phoneNumber " +
   "AND phoneNumber NOT IN (SELECT phoneNumber FROM Contact)")
*/

@Database(
    entities = [Call::class, Contact::class],
    version = 1
)
abstract class Consultas : RoomDatabase() {
    abstract val consultasDao: ConsultasDao

    companion object {
        @Volatile
        private var INSTANCE:Consultas? = null

        fun getInstance(context: Context):Consultas{
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            Consultas::class.java,
                            "app_database"
                        ).allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}