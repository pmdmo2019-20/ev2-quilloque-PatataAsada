package es.iessaladillo.pedrojoya.quilloque.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import es.iessaladillo.pedrojoya.quilloque.data.model.Call
import es.iessaladillo.pedrojoya.quilloque.data.model.CallWithContact
import es.iessaladillo.pedrojoya.quilloque.data.model.Contact

@Dao
interface ConsultasDao {
    @Query("SELECT C.id AS callId, C.phoneNumber AS phoneNumber, C.type AS type, \n" +
            "C.date AS date, C.time AS time, T.id AS contactId, T.name AS contactName \n" +
            "FROM Call AS C LEFT JOIN Contact AS T ON C.phoneNumber = T.phoneNumber \n" +
            "ORDER BY C.id DESC LIMIT :limit")
    fun queryCalls():List<CallWithContact>

    @Query("SELECT name AS contactName, phoneNumber AS phoneNumber " +
            "FROM Contact " +
            "WHERE phoneNumber like :phoneNumber " +
            "UNION " +
            "SELECT DISTINCT phoneNumber AS contactName, phoneNumber AS phoneNumber " +
            "FROM Call " +
            "WHERE phoneNumber like :phoneNumber " +
            "AND phoneNumber NOT IN (SELECT phoneNumber FROM Contact)")
    fun querySuggestions(phoneNumber:String):List<Contact>

    @Insert
    fun insertCall(call: Call):Boolean

    @Insert
    fun insertContact(contact: Contact):Boolean

    @Delete
    fun deleteCall(call: Call):Boolean

    @Delete
    fun deleteContact(contact: Contact):Boolean

    @Query("SELECT * FROM Contact WHERE name LIKE :name")
    fun queryContacts(name:String?): List<Contact>
}