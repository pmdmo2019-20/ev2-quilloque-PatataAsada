package es.iessaladillo.pedrojoya.quilloque.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Contact",
    indices = [Index(value = ["phoneNumber"], unique = true)]
)
class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String
)