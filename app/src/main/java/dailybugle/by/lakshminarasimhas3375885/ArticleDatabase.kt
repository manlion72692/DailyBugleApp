package dailybugle.by.lakshminarasimhas3375885

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Database helper for storing and retrieving bookmarked articles.
class ArticleDatabase(context: Context) {
    private val dbHelper = DBHelper(context)

    fun addBookmark(article: Article) {
        val db = dbHelper.writableDatabase
        val contentValues = ContentValues().apply {
            put("title", article.title)
            put("description", article.description)
            put("url", article.url)
            put("image", article.urlToImage)
            put("source", article.source.name)
        }
        db.insert("bookmarked_articles", null, contentValues)
    }

    fun getAllBookmarks(): List<Article> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            "bookmarked_articles", null, null, null, null, null, null
        )
        val bookmarkedArticles = mutableListOf<Article>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val description = cursor.getString(cursor.getColumnIndex("description"))
            val url = cursor.getString(cursor.getColumnIndex("url"))
            val image = cursor.getString(cursor.getColumnIndex("image"))
            val source = cursor.getString(cursor.getColumnIndex("source"))
            bookmarkedArticles.add(
                Article(
                    Source(source, source),
                    "",
                    title,
                    description,
                    url,
                    image,
                    "",
                    ""
                )
            )
        }
        cursor.close()
        return bookmarkedArticles
    }

    fun deleteBookmark(url: String) {
        val db = dbHelper.writableDatabase
        db.delete("bookmarked_articles", "url = ?", arrayOf(url))
    }

    private class DBHelper(context: Context) : SQLiteOpenHelper(
        context,
        "article_database",
        null,
        1
    ) {
        override fun onCreate(db: SQLiteDatabase) {
            val createTable = "CREATE TABLE bookmarked_articles (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "description TEXT," +
                    "url TEXT," +
                    "image TEXT," +
                    "source TEXT)"
            db.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS bookmarked_articles")
            onCreate(db)
        }
    }
}
