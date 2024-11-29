package orazjemal.meredova.studymap

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import orazjemal.meredova.studymap.data.KutuphaneData
import orazjemal.meredova.studymap.databinding.ActivityKutuphaneEkleBinding

class KutuphaneEkle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityKutuphaneEkleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // XML'deki öğeleri tanımlıyoruz
        val etKutuphaneAdi = findViewById<EditText>(R.id.kutuphaneAdi)
        val etKapasite = findViewById<EditText>(R.id.KutuphaneKapasitesi)
        val etAdres = findViewById<EditText>(R.id.KutuphaneAcikAdres)
        val etGoogleLink = findViewById<EditText>(R.id.GoogleLink)
        val btnEkle = findViewById<Button>(R.id.KutuphaneKaydet)

        // Veritabanı yardımcı sınıfını oluşturuyoruz
        val kutuphaneDB = KutuphaneData(this)

        // Ekle butonuna tıklama işlemi
        btnEkle.setOnClickListener {
            val kutuphaneAdi = etKutuphaneAdi.text.toString()
            val kapasite = etKapasite.text.toString()
            val adres = etAdres.text.toString()
            val googleLink = etGoogleLink.text.toString()

            // Form doğrulama
            if (kutuphaneAdi.isEmpty() || kapasite.isEmpty() || adres.isEmpty() || googleLink.isEmpty()) {
                Toast.makeText(this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Veritabanına ekleme işlemi
            val isSuccess = kutuphaneDB.addLibrary(kutuphaneAdi, kapasite, adres, googleLink)

            if (isSuccess) {
                Toast.makeText(this, "Kütüphane başarıyla eklendi", Toast.LENGTH_SHORT).show()
                // Formu temizle
                etKutuphaneAdi.text.clear()
                etKapasite.text.clear()
                etAdres.text.clear()
                etGoogleLink.text.clear()
            } else {
                Toast.makeText(this, "Kütüphane eklenirken hata oluştu", Toast.LENGTH_SHORT).show()
            }
        }

        binding.KutuphaneleriListele.setOnClickListener {
            val intent = Intent(applicationContext, KutuphaneListeleAdmin::class.java)
            startActivity(intent)
        }

        //GeriDönme Buttonu
        binding.kEkleGeriDon.setOnClickListener {
            val intent = Intent(applicationContext, MainProfil::class.java)
            startActivity(intent)
        }
    }
}