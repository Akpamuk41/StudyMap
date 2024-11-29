package orazjemal.meredova.studymap

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import orazjemal.meredova.studymap.data.KutuphaneData

class KutuphaneListeleAdmin : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var libraryAdapter: ArrayAdapter<String> // String için ArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kutuphane_listele)

        listView = findViewById(R.id.list_view_libraries)

        // Kütüphaneleri veritabanından al
        val kutuphaneData = KutuphaneData(this)
        val libraries = kutuphaneData.getAllLibraries()

        // Sadece kütüphane isimlerini al
        val libraryNames = libraries.map { it.name } // List<String> oluşturulur

        // Adapter ile isimleri ListView'e bağla
        libraryAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, // Basit bir satır tasarımı kullanıyoruz
            libraryNames
        )
        listView.adapter = libraryAdapter

        // ListView item tıklama olayı (isteğe bağlı)
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedLibrary = libraries[position] // Tüm listeyi kullanarak nesneyi al
            val intent = Intent(this, kuutuphaneDuzenle::class.java).apply {
                putExtra("libraryId", selectedLibrary.id) // Detay ekranına ID gönder
            }
            startActivity(intent)
        }
    }
}


