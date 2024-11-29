package orazjemal.meredova.studymap

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orazjemal.meredova.studymap.databinding.ActivityBilgileriGuncelleBinding
import android.widget.Toast
import orazjemal.meredova.studymap.data.UserData

class BilgileriGuncelle : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Button işlevleri için gerekli kod parçası
        val binding = ActivityBilgileriGuncelleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // UserData sınıfını kullanarak veritabanından kullanıcı bilgilerini alıyoruz
        val userData = UserData(this)
        val userId = 1 // Örnek kullanıcı ID'si. Gerçek uygulamada, oturum açan kullanıcının ID'sini buraya almanız gerekebilir.
        val userInfo = userData.getUserInfoById(userId)

        // Eğer kullanıcı bilgileri mevcutsa, EditText'lere atama yapıyoruz
        userInfo?.let {
            val username = it.first // Kullanıcı adı
            val password = it.second // Şifre

            // Kullanıcı adı ve şifreyi EditText'lere yerleştiriyoruz
            binding.BilgileriGuncelleKullaniciAdi.setText(username)
            binding.BilgileriGuncelleParola.setText(password)
        }

        // Güncelleme Buttonu İşlevi
        binding.BilgileriGuncelleBtn.setOnClickListener {
            val newUsername = binding.BilgileriGuncelleKullaniciAdi.text.toString()
            val newPassword = binding.BilgileriGuncelleParola.text.toString()

            if (newUsername.isNotEmpty() && newPassword.isNotEmpty()) {
                // Kullanıcı bilgilerini güncelliyoruz
                val updateSuccess = userData.updateUser(userId, newUsername, newPassword)

                if (updateSuccess) {
                    // Güncelleme başarılıysa, kullanıcıya bilgi veriyoruz
                    Toast.makeText(this, "Bilgiler başarıyla güncellendi", Toast.LENGTH_SHORT).show()
                } else {
                    // Güncelleme başarısızsa, kullanıcıya hata mesajı veriyoruz
                    Toast.makeText(this, "Bir hata oluştu. Lütfen tekrar deneyin.", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Kullanıcı adı veya şifre boşsa uyarı mesajı
                Toast.makeText(this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show()
            }
        }

        // Geri dönme Buttonu
        binding.GeriDonBtnBilgiler.setOnClickListener {
            val intent = Intent(applicationContext, MainProfil::class.java)
            startActivity(intent)
        }
    }
}
