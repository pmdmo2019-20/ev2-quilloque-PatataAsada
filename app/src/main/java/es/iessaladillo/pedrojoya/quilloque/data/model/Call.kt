package es.iessaladillo.pedrojoya.quilloque.data.model

import androidx.room.*

@Entity(
    tableName = "Call",
    foreignKeys = [
        androidx.room.ForeignKey(

            entity = Contact::class,

            parentColumns = ["phoneNumber"],

            childColumns = ["phoneNumber"],

            onUpdate = androidx.room.ForeignKey.NO_ACTION,

            onDelete = androidx.room.ForeignKey.NO_ACTION

        )
    ]
)
class Call(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "time")
    val time: String
)

class CallWithContact(
    @Embedded
    val call: Call,
    @Relation(
        parentColumn = "phoneNumber",
        entityColumn = "phoneNumber"
    )
    val contact: Contact
)