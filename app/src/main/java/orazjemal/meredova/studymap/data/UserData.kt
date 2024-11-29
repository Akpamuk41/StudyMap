package orazjemal.meredova.studymap.data
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

// Kullanıcı Kaydı ve Yönetimi
class UserData(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Kutuphanem.db" // Veritabanı adı
        private const val DATABASE_VERSION = 3           // Veritabanı sürümü
        const val TABLE_NAME = "Kullanicilar"             // Tablo adı
        const val COLUMN_ID = "id"                        // ID sütunu
        const val COLUMN_USERNAME = "kullaniciAdi"        // Kullanıcı adı sütunu
        const val COLUMN_PASSWORD = "sifre"              // Şifre sütunu
        const val COLUMN_ROLE = "role"                   // Rol sütunu
    }

    // Veritabanı oluşturma
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_ROLE TEXT DEFAULT NULL -- Rol sütunu, başlangıçta boş bırakılabilir
            )
        """
        db?.execSQL(CREATE_TABLE)
    }

    // Veritabanı yükseltme
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_ROLE TEXT DEFAULT NULL")
        }
    }

    // Kullanıcı bilgisi çekme
    fun getUserInfoById(userId: Int): Pair<String, String>? {
        val db = readableDatabase
        val query = "SELECT $COLUMN_USERNAME, $COLUMN_PASSWORD FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        var userInfo: Pair<String, String>? = null

        db.rawQuery(query, arrayOf(userId.toString())).use { cursor ->
            if (cursor.moveToFirst()) {
                val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                userInfo = Pair(username, password)
            } else {
                Log.e("DatabaseError", "Kullanıcı bulunamadı.")
            }
        }
        return userInfo
    }

    // Kullanıcı güncelleme (şifre ve kullanıcı adı)
    fun updateUser(userId: Int, newUsername: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USERNAME, newUsername)
            put(COLUMN_PASSWORD, newPassword)
        }

        val rowsAffected = db.update(
            TABLE_NAME,
            contentValues,
            "$COLUMN_ID = ?",
            arrayOf(userId.toString())
        )

        return rowsAffected > 0
    }
}