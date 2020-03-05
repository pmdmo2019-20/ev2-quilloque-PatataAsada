package es.iessaladillo.pedrojoya.quilloque.data.model

import androidx.room.ColumnInfo

class CallWithContact(
    @ColumnInfo(name = "callId")
    val callid: Long?,
    @ColumnInfo(name = "contactId")
    val contactId: String?,
    @ColumnInfo(name = "contactName")
    val contactName: String?,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,
    @ColumnInfo(name = "date")
    val date:String?,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "type")
    val type: String?
)