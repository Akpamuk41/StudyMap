package orazjemal.meredova.studymap.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class KutuphaneData(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Kutuphanem.db"
        private const val DATABASE_VERSION = 3 // Sürüm güncellendi

        // Kütüphaneler Tablosu
        const val TABLE_NAME_LIBRARIES = "Kutuphaneler"
        const val COLUMN_ID = "id"
        const val COLUMN_LIBRARY_NAME = "kutuphaneAdi"
        const val COLUMN_CAPACITY = "kapasite"
        const val COLUMN_ADDRESS = "adres"
        const val COLUMN_GOOGLE_LINK = "googleLink"
    }





    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_LIBRARIES_TABLE = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME_LIBRARIES (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_LIBRARY_NAME TEXT,
            $COLUMN_CAPACITY INTEGER,  
            $COLUMN_ADDRESS TEXT,
            $COLUMN_GOOGLE_LINK TEXT
        )
    """
        db?.execSQL(CREATE_LIBRARIES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_LIBRARIES")
        onCreate(db)
    }

    fun addLibrary(kutuphaneAdi: String, kapasite: String, adres: String, googleLink: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_LIBRARY_NAME, kutuphaneAdi)
            put(COLUMN_CAPACITY, kapasite)  // Int tipi
            put(COLUMN_ADDRESS, adres)
            put(COLUMN_GOOGLE_LINK, googleLink)
        }
        val result = db.insert(TABLE_NAME_LIBRARIES, null, contentValues)
        db.close()
        return result != -1L
    }

    // Kütüphaneleri Getir
    fun getAllLibraries(): List<Library> {
        val libraries = mutableListOf<Library>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME_LIBRARIES", null)

        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    // Get column indexes
                    val idIndex = it.getColumnIndex(COLUMN_ID)
                    val nameIndex = it.getColumnIndex(COLUMN_LIBRARY_NAME)
                    val capacityIndex = it.getColumnIndex(COLUMN_CAPACITY)
                    val addressIndex = it.getColumnIndex(COLUMN_ADDRESS)
                    val googleLinkIndex = it.getColumnIndex(COLUMN_GOOGLE_LINK)

                    // If column index is -1, it means the column does not exist
                    if (idIndex == -1 || nameIndex == -1 || capacityIndex == -1 || addressIndex == -1 || googleLinkIndex == -1) {
                        println("DEBUG: One of the columns is missing.")
                        continue
                    }

                    // Create a Library object and add it to the list
                    val library = Library(
                        id = it.getInt(idIndex),
                        name = it.getString(nameIndex),
                        capacity = it.getInt(capacityIndex),
                        address = it.getString(addressIndex),
                        googleLink = it.getString(googleLinkIndex)
                    )
                    libraries.add(library)

                } while (it.moveToNext())
            }
            it.close() // Close the cursor
        }

        db.close() // Close the database
        return libraries
    }


    fun getLibraryById(id: Int): Library? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_LIBRARIES WHERE $COLUMN_ID = ?", arrayOf(id.toString()))

        var library: Library? = null
        if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(COLUMN_ID)
            val nameIndex = cursor.getColumnIndex(COLUMN_LIBRARY_NAME)
            val capacityIndex = cursor.getColumnIndex(COLUMN_CAPACITY)
            val addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS)
            val googleLinkIndex = cursor.getColumnIndex(COLUMN_GOOGLE_LINK)

            library = Library(
                id = cursor.getInt(idIndex),
                name = cursor.getString(nameIndex),
                capacity = cursor.getInt(capacityIndex),
                address = cursor.getString(addressIndex),
                googleLink = cursor.getString(googleLinkIndex)
            )
        }

        cursor.close()
        db.close()

        return library
    }

    fun updateLibrary(id: Int, name: String, capacity: String, address: String, googleLink: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_LIBRARY_NAME, name)
            put(COLUMN_CAPACITY, capacity)
            put(COLUMN_ADDRESS, address)
            put(COLUMN_GOOGLE_LINK, googleLink)
        }
        return db.update(TABLE_NAME_LIBRARIES, contentValues, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun deleteLibraryById(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME_LIBRARIES, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0 // Eğer 0'dan büyükse, silme işlemi başarılıdır
    }






}