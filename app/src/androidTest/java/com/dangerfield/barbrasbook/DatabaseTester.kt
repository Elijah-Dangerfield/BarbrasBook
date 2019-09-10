package com.dangerfield.barbrasbook

import android.content.Context
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.dangerfield.barbrasbook.db.ArticlesDao
import com.dangerfield.barbrasbook.db.ArticlesDatabase
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.model.Source
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTester {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: ArticlesDatabase
    private lateinit var dao: ArticlesDao

    @Before
    fun setup() {
        val context: Context = InstrumentationRegistry.getTargetContext()
        try {
            database = Room.inMemoryDatabaseBuilder(context, ArticlesDatabase::class.java)
                .allowMainThreadQueries().build()
        } catch (e: Exception) {
            Log.i("test", e.message)
        }
        dao = database.articleDao()
    }

    @Test
    fun testAddingAndRetrievingData() {
        // 1
        val dataBefore = dao.getAll()

        // 2
        val list = Article(null,
            "",
            "",
            "",
            ""
            , Source(""),
            "",
            "","",null)

        dao.insertAll(list)

        //3
        val postInsertRetrievedCategories = dao.getAll()
        val sizeDifference = postInsertRetrievedCategories.size - dataBefore.size
        Assert.assertEquals(1, sizeDifference)
    }

    @After
    fun tearDown() {
        database.close()
    }
}