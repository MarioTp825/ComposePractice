package com.example.artgallery.artWork.models.db

import android.content.Context
import com.example.artgallery.artWork.models.dto.database.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore

object ArtObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox
            .builder()
            .androidContext(context)
            .name("ArtWorkDB")
            .build()
    }

    fun <T>boxFor(data: Class<T>): Box<T> = store.boxFor(data)
}