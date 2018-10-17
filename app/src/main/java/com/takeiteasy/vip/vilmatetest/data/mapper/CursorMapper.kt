package com.takeiteasy.vip.vilmatetest.data.mapper

import android.database.Cursor

abstract class CursorMapper<T> {
    abstract fun map(cursor: Cursor):T

    fun mapList(cursor: Cursor): List<T> {
        val list = arrayListOf<T>()

        while (cursor.moveToNext()) {
            list.add(map(cursor))
        }

        return list
    }
}