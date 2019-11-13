package net.hulyka.spacexviewer.api.service

import net.hulyka.spacexviewer.api.model.LaunchData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("v3/launches")
    suspend fun getAllLaunches(
        @Query("limit") limit: Int, @Query("offset") offset: Int,
        @Query("order") order: String = "desc"
    ): List<LaunchData>

    @GET("v3/launches/{flightNumber}")
    suspend fun getLaunchById(@Path("flightNumber") flightNumber: Int): LaunchData

    @GET("v3/launches/past")
    suspend fun getPastLaunches(@Query("start") start: String, @Query("end") end: String): List<LaunchData>

}
