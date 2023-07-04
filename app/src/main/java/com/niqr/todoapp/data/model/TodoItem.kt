package com.niqr.todoapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.niqr.todoapp.data.model.serializers.LocalDateSerializer
import com.niqr.todoapp.data.model.serializers.LocalDateTimeSerializer
import com.niqr.todoapp.data.model.serializers.PrioritySerializer
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@Entity(tableName = "task")
data class TodoItem(
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerialName("id")
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "text")
    @SerialName("text")
    val description: String,

    @Serializable(with = LocalDateSerializer::class)
    @ColumnInfo(name = "deadline")
    @SerialName("deadline")
    val deadline: LocalDate? = null,

    @Serializable(with = PrioritySerializer::class)
    @ColumnInfo(name = "importance")
    @SerialName("importance")
    val priority: Priority = Priority.COMMON,

    @ColumnInfo(name = "done")
    @SerialName("done")
    val isDone: Boolean = false,

    @ColumnInfo(name = "color")
    @SerialName("color")
    val color: String? = null,

    @Serializable(with = LocalDateTimeSerializer::class)
    @ColumnInfo(name = "created_at")
    @SerialName("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Serializable(with = LocalDateTimeSerializer::class)
    @ColumnInfo(name = "changed_at")
    @SerialName("changed_at")
    val editedAt: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "last_updated_by")
    @SerialName("last_updated_by")
    val lastUpdatedBy: String = "1110111"
)