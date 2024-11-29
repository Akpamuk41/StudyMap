package orazjemal.meredova.studymap
import android.widget.Toast
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orazjemal.meredova.studymap.data.UserData
import orazjemal.meredova.studymap.databinding.ActivityKayitSayfasiBinding
import orazjemal.meredova.studymap.databinding.FragmentNotificationsBinding
import orazjemal.meredova.studymap.ui.notifications.NotificationsFragment

class KayitSayfasi : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Button işlevleri için gerekli kod parçası
        val binding = ActivityKayitSayfasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Veritabanı yardımı
        val dbUser= UserData(this)

        binding.KayitOlBtn.setOnClickListener {
            val username = binding.kayitKullaniciAdi.text.toString()
            val password = binding.kayitSifre.text.toString()
            val defaultRole = "user" // Yeni kayıtlar için varsayılan rol

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val db = dbUser.writableDatabase
                val query = """
                    INSERT INTO ${UserData.TABLE_NAME} 
                    (${UserData.COLUMN_USERNAME}, ${UserData.COLUMN_PASSWORD}, ${UserData.COLUMN_ROLE}) 
                    VALUES (?, ?, ?)
                """
                db.execSQL(query, arrayOf(username, password, defaultRole))
                db.close()

                Toast.makeText(this, "Kayıt Başarılı", Toast.LENGTH_LONG).show()
                binding.kayitKullaniciAdi.text.clear()
                binding.kayitSifre.text.clear()
            } else {
                Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
            }
        }

        //GeriDönme Buttonu
        binding.GeriDonBtn.setOnClickListener {
            val intent = Intent(applicationContext, NotificationsFragment::class.java)
            startActivity(intent)
        }


    }
}