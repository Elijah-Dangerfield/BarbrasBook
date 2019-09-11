package com.dangerfield.barbrasbook

import com.dangerfield.barbrasbook.api.Repository
import com.dangerfield.barbrasbook.api.RetrofitBuilder
import com.dangerfield.barbrasbook.util.toReadableDate
import org.junit.Test

import org.junit.Assert.*
import java.io.IOException

class BaseUnitTest {
    @Test
    fun test_correct_date_format() {
        val inputDate = "2019-09-08T12:16:55Z"
        val expectedOutPut = "Sun Sep 08"
        assertEquals(inputDate.toReadableDate(),expectedOutPut)
    }

    @Test
    fun test_correct_date_format2() {
        val inputDate = "2019-09-09T12:16:55Z"
        val expectedOutPut = "Mon Sep 09"
        assertEquals(inputDate.toReadableDate(),expectedOutPut)
    }


    @Test
    fun test_can_make_api_call() {

        val call = RetrofitBuilder.apiService.getLatest(
            Repository.celebrity,
            Repository.API_KEY
        )

        try {
            val response = call.execute()
            assertTrue(response.isSuccessful)

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}


