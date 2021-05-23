package com.cartrack.demo.roomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(loginTableModel: LoginTable)

    @Query("SELECT * FROM Login WHERE Username =:username")
    fun getLoginDetails(username: String?): LiveData<LoginTable>

    @Query("delete from Login")
    fun deleteAllData()

}

