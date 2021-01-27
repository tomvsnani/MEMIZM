package com.analogit.memeizm.Databases

import androidx.lifecycle.LiveData
import androidx.room.*
import com.analogit.memeizm.Models.MainContentModel

@Dao
interface MainEntityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(mainContentModel: MainContentModel):Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(mainContentModel: MainContentModel):Int

    @Delete
    fun delete(mainContentModel: MainContentModel)

    @Query("SELECT * FROM MainContentModel WHERE isInProgressTemplate=:isInTemplates")
    fun getMyTemplates(isInTemplates: Boolean = true):LiveData<List<MainContentModel>>


    @Query("SELECT * FROM MainContentModel WHERE isInMyDownloads=:isInDownloads")
    fun getMyDownloads(isInDownloads: Boolean = true):LiveData<List<MainContentModel>>

    @Query("SELECT * FROM MainContentModel WHERE isSaved=:isInSaved")
    fun getMySavedItems(isInSaved: Boolean = true):LiveData<List<MainContentModel>>

    @Query("SELECT * FROM MainContentModel WHERE isFav=:isFav")
    fun getMyFavItems(isFav: Boolean = true):LiveData<List<MainContentModel>>

    @Query("SELECT * FROM MainContentModel WHERE id=:id")
    fun getItemById(id:String):LiveData<MainContentModel>

    @Query("SELECT COUNT(*) FROM MainContentModel")
    fun getItemCount():Long

    @Query("SELECT * FROM MainContentModel")
    fun getTotal():LiveData<List<MainContentModel>>


}