package orazjemal.meredova.studymap.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class KitapData(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Kitaplar.db"
        private const val DATABASE_VERSION = 1

        // Kitaplar Tablosu
        const val TABLE_NAME_BOOKS = "Kitaplar"
        const val COLUMN_ID = "id"
        const val COLUMN_BOOK_NAME = "kitapAdi"
        const val COLUMN_IMAGE = "kitapGorsel"
        const val COLUMN_DESCRIPTION = "kitapAciklama"
        const val COLUMN_QUANTITY = "kitapAdedi"
        const val COLUMN_PRICE = "kitapFiyati"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BOOKS_TABLE = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME_BOOKS (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_BOOK_NAME TEXT,
            $COLUMN_IMAGE TEXT,
            $COLUMN_DESCRIPTION TEXT,
            $COLUMN_QUANTITY INTEGER,
            $COLUMN_PRICE REAL
        )
        """
        db?.execSQL(CREATE_BOOKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_BOOKS")
        onCreate(db)
    }

    // Kitap Ekle
    fun addBook(kitapAdi: String, kitapGorsel: String, kitapAciklama: String, kitapAdedi: Int, kitapFiyati: Double): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_BOOK_NAME, kitapAdi)
            put(COLUMN_IMAGE, kitapGorsel)
            put(COLUMN_DESCRIPTION, kitapAciklama)
            put(COLUMN_QUANTITY, kitapAdedi)
            put(COLUMN_PRICE, kitapFiyati)
        }
        val result = db.insert(TABLE_NAME_BOOKS, null, contentValues)
        db.close()
        return result != -1L
    }

    // Kitapları Listele
    fun getAllBooks(): List<Book> {
        val books = mutableListOf<Book>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME_BOOKS", null)

        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndexOrThrow(COLUMN_ID))
                    val name = it.getString(it.getColumnIndexOrThrow(COLUMN_BOOK_NAME))
                    val image = it.getString(it.getColumnIndexOrThrow(COLUMN_IMAGE))
                    val description = it.getString(it.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                    val quantity = it.getInt(it.getColumnIndexOrThrow(COLUMN_QUANTITY))
                    val price = it.getDouble(it.getColumnIndexOrThrow(COLUMN_PRICE))

                    books.add(Book(id, name, image, description, quantity, price))
                } while (it.moveToNext())
            }
            it.close()
        }

        db.close()
        return books
    }

    // ID'ye Göre Kitap Getir
    fun getBookById(id: Int): Book? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_BOOKS WHERE $COLUMN_ID = ?", arrayOf(id.toString()))

        var book: Book? = null
        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE))

            book = Book(id, name, image, description, quantity, price)
        }

        cursor.close()
        db.close()
        return book
    }

    // Kitap Güncelle
    fun updateBook(id: Int, kitapAdi: String, kitapGorsel: String, kitapAciklama: String, kitapAdedi: Int, kitapFiyati: Double): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_BOOK_NAME, kitapAdi)
            put(COLUMN_IMAGE, kitapGorsel)
            put(COLUMN_DESCRIPTION, kitapAciklama)
            put(COLUMN_QUANTITY, kitapAdedi)
            put(COLUMN_PRICE, kitapFiyati)
        }
        return db.update(TABLE_NAME_BOOKS, contentValues, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    // Kitap Sil
    fun deleteBookById(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME_BOOKS, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }
}

// Kitap sınıfı (veri modeli)
data class Book(
    val id: Int,
    val name: String,
    val image: String, // Görselin URI veya yolu
    val description: String,
    val quantity: Int,
    val price: Double
)
