package com.example.litroz.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var title: String?,
    var description: String?,
    var isCompleted: Boolean = false,
    var syncStatus: Boolean = false,
    val isDeleted: Boolean = false,
    var isNew: Boolean = true
) : Parcelable  {

    // Construtor para ler do Parcel
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        title = parcel.readString(),
        description = parcel.readString(),
        isCompleted = parcel.readByte() != 0.toByte(),
        syncStatus = parcel.readByte() != 0.toByte(),
        isDeleted = parcel.readByte() != 0.toByte(),
        isNew = parcel.readByte() != 0.toByte(),
    )

    // Método para escrever os dados no Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeByte(if (isCompleted) 1 else 0)
        parcel.writeInt(if (syncStatus) 1 else 0)
        parcel.writeByte(if (isDeleted) 1 else 0)
        parcel.writeByte(if (isNew) 1 else 0)
    }

    // Método necessário para implementar Parcelable (geralmente retorna 0)
    override fun describeContents(): Int = 0

    // Criador para recriar a Task a partir de um Parcel
    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Task> {
            override fun createFromParcel(parcel: Parcel): Task {
                return Task(parcel)
            }

            override fun newArray(size: Int): Array<Task?> {
                return arrayOfNulls(size)
            }
        }
    }
}