package orazjemal.meredova.studymap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orazjemal.meredova.studymap.data.KutuphaneData
import orazjemal.meredova.studymap.databinding.ActivityBilgileriGuncelleBinding
import orazjemal.meredova.studymap.databinding.ActivityKuutuphaneDuzenleBinding
import orazjemal.meredova.studymap.ui.notifications.NotificationsFragment

class kuutuphaneDuzenle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kuutuphane_duzenle)

        //Button işlevleri için gerekli kod parçası
        val binding = ActivityKuutuphaneDuzenleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // EditText ve Button bileşenlerini tanımlama
        val kutuphaneIsimD: EditText = findViewById(R.id.DkutuphaneAdi)
        val kutuphaneKapasiteD: EditText = findViewById(R.id.DKutuphaneKapasitesi)
        val kutuphaneAdresD: EditText = findViewById(R.id.DKutuphaneAcikAdres)
        val kutuphaneGoogleLinkD: EditText = findViewById(R.id.DGoogleLink)
        val guncelleButton: Button = findViewById(R.id.KutuphaneGuncelle)
        val silButton: Button = findViewById(R.id.DkutuphaneSil)

        // Intent ile gelen kütüphane ID'sini al
        val libraryId = intent.getIntExtra("libraryId", -1)

        // Veritabanından kütüphane bilgilerini çek
        val kutuphaneDB = KutuphaneData(this)
        val library = kutuphaneDB.getLibraryById(libraryId)

        // Eğer kütüphane bulunursa bilgileri doldur
        library?.let {
            kutuphaneIsimD.setText(it.name ?: "")
            kutuphaneKapasiteD.setText(it.capacity?.toString() ?: "")
            kutuphaneAdresD.setText(it.address ?: "")
            kutuphaneGoogleLinkD.setText(it.googleLink ?: "")
        } ?: Log.e("KutuphaneDuzenle", "Library not found for ID: $libraryId")

        // Güncelleme butonuna tıklama işlemi
        binding.KutuphaneGuncelle.setOnClickListener {
            val updatedName = kutuphaneIsimD.text.toString()
            val updatedCapacity = kutuphaneKapasiteD.text.toString()
            val updatedAddress = kutuphaneAdresD.text.toString()
            val updatedGoogleLink = kutuphaneGoogleLinkD.text.toString()

            // Güncelleme işlemini çağır
            kutuphaneDB.updateLibrary(libraryId, updatedName, updatedCapacity, updatedAddress, updatedGoogleLink)

            // Geri dön veya mesaj göster
            finish()
        }
        // Kütüphane Sil Buttonu
        binding.DkutuphaneSil.setOnClickListener {
            val libraryId = intent.getIntExtra("libraryId", -1)

            if (libraryId != -1) {
                val kutuphaneDB = KutuphaneData(this)
                val success = kutuphaneDB.deleteLibraryById(libraryId)

                if (success) {
                    Toast.makeText(this, "Kütüphane başarıyla silindi.", Toast.LENGTH_SHORT).show()
                    finish() // Mevcut activity'yi kapatır ve bir önceki ekrana döner
                } else {
                    Toast.makeText(this, "Kütüphane silinemedi.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Kütüphane ID bulunamadı.", Toast.LENGTH_SHORT).show()
            }
        }

        //GeriDönme Buttonu
        binding.kDuzenleGeriDon.setOnClickListener {
            val intent = Intent(applicationContext, MainProfil::class.java)
            startActivity(intent)
        }

    }
}