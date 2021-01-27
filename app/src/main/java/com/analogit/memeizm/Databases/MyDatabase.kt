package com.analogit.memeizm.Databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.analogit.memeizm.Models.MainContentModel

@Database(entities = [MainContentModel::class], version = 1, exportSchema = false)
abstract class MyDatabase() : RoomDatabase() {
    abstract val dao: MainEntityDao

    companion object {
        @Volatile
        var db: MyDatabase? = null


        fun getInstance(context: Context): MyDatabase {
            synchronized(this) {
                if (db == null) {
                    db = Room.databaseBuilder(context, MyDatabase::class.java, "mydb")
                        .fallbackToDestructiveMigration().build()
                }
                return db!!


            }
        }
    }


}