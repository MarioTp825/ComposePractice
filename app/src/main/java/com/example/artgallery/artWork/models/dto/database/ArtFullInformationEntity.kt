package com.example.artgallery.artWork.models.dto.database

import com.example.artgallery.artWork.models.dto.ArtHolder
import com.google.gson.Gson
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.converter.PropertyConverter

@Entity
data class ArtFullInformationEntity(
    @Id
    var id: Long = 0,
    @Convert(converter = ArtInformationConverter::class, dbType = String::class)
    var art: ArtHolder.ArtFullInformation
)

class ArtInformationConverter : PropertyConverter<ArtHolder.ArtFullInformation, String> {
    override fun convertToEntityProperty(databaseValue: String?): ArtHolder.ArtFullInformation =
        Gson().fromJson(databaseValue, ArtHolder.ArtFullInformation::class.java)


    override fun convertToDatabaseValue(entityProperty: ArtHolder.ArtFullInformation?): String =
        Gson().toJson(entityProperty)


}
