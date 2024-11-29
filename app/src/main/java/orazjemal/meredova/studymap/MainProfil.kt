package orazjemal.meredova.studymap

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orazjemal.meredova.studymap.data.UserData
import orazjemal.meredova.studymap.databinding.ActivityMainProfilBinding
import orazjemal.meredova.studymap.ui.notifications.NotificationsFragment

class MainProfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Button işlevleri için gerekli kod parçası
        val binding = ActivityMainProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Kullanıcı adı almak için UserData sınıfını kullanıyoruz
        val userData = UserData(this)
        val userId = 1 // Örnek kullanıcı ID'si. Gerçek uygulamada, oturum açan kullanıcının ID'sini buraya almanız gerekir.
        val userInfo = userData.getUserInfoById(userId)

        // Kullanıcı adı varsa, TextView'e atama yap
        userInfo?.let {
            val username = it.first // Kullanıcı adı
            binding.textView3.text = username
        }


        //Bilgileri Güncelleme Sayfa Buttonu
        binding.BilgileriGuncelleSayfa.setOnClickListener {
            val intent = Intent(applicationContext, BilgileriGuncelle::class.java)
            startActivity(intent)
        }


        //Kütüphane Sayfa Buttonu
        binding.KutuphaneEkle.setOnClickListener {
            val intent = Intent(applicationContext, KutuphaneEkle::class.java)
            startActivity(intent)
        }

        // Kitap Ekle işlemi
        binding.KitapEkleButton.setOnClickListener {
            val intent = Intent(applicationContext, KitapEkle::class.java)
            startActivity(intent)
            finish() // Geri dönüşü engellemek için ekranı kapatıyoruz
        }

        // Çıkış yapma işlemi
        binding.CikisYap.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish() // Geri dönüşü engellemek için ekranı kapatıyoruz
        }

    }
}