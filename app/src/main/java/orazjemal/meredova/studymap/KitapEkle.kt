package orazjemal.meredova.studymap

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orazjemal.meredova.studymap.data.KitapData

class KitapEkle : AppCompatActivity() {

    private lateinit var kitapGorselUri: Uri
    private val GALLERY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kitap_ekle)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // UI bileşenlerini tanımlama
        val kitapGorsel = findViewById<ImageView>(R.id.kitapGorsel)
        val btnSelectImage = findViewById<Button>(R.id.selectImageButton)
        val etKitapAdi = findViewById<EditText>(R.id.kitapAdi)
        val etKitapAciklama = findViewById<EditText>(R.id.kitapAciklama)
        val etKitapAdedi = findViewById<EditText>(R.id.kitapAdedi)
        val etKitapFiyati = findViewById<EditText>(R.id.kitapFiyati)
        val btnKitapEkle = findViewById<Button>(R.id.kitapEkleBtn)

        // Veritabanı işlemleri için sınıfı başlatma
        val kitapData = KitapData(this)

        // Görsel seçme işlemi
        btnSelectImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        }

        // Kitap ekleme butonuna tıklama işlemi
        btnKitapEkle.setOnClickListener {
            val kitapAdi = etKitapAdi.text.toString().trim()
            val kitapAciklama = etKitapAciklama.text.toString().trim()
            val kitapAdedi = etKitapAdedi.text.toString().toIntOrNull()
            val kitapFiyati = etKitapFiyati.text.toString().toDoubleOrNull()

            if (kitapAdi.isEmpty() || !::kitapGorselUri.isInitialized || kitapAciklama.isEmpty() ||
                kitapAdedi == null || kitapFiyati == null) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Veritabanına kitap ekleme
            val isSuccess = kitapData.addBook(kitapAdi, kitapGorselUri.toString(), kitapAciklama, kitapAdedi, kitapFiyati)
            if (isSuccess) {
                Toast.makeText(this, "Kitap başarıyla eklendi!", Toast.LENGTH_SHORT).show()

                // Alanları temizle
                etKitapAdi.text.clear()
                etKitapAciklama.text.clear()
                etKitapAdedi.text.clear()
                etKitapFiyati.text.clear()
                kitapGorsel.setImageResource(R.drawable.baseline_library_books_24) // Varsayılan görsele döner
            } else {
                Toast.makeText(this, "Kitap eklenirken bir hata oluştu.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Görsel seçme sonucu işleme
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            kitapGorselUri = data.data ?: return
            findViewById<ImageView>(R.id.kitapGorsel).setImageURI(kitapGorselUri)
        }
    }
}
