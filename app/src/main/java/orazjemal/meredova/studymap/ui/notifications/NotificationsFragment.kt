package orazjemal.meredova.studymap.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import orazjemal.meredova.studymap.KayitSayfasi
import orazjemal.meredova.studymap.MainProfil
import orazjemal.meredova.studymap.data.UserData
import orazjemal.meredova.studymap.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        binding.girisBtn.setOnClickListener {
            val username = binding.editTextText.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val userData = UserData(requireContext()) // Yeni sınıfı kullanıyoruz
                val db = userData.readableDatabase
                val query = "SELECT * FROM ${UserData.TABLE_NAME} WHERE ${UserData.COLUMN_USERNAME} = ? AND ${UserData.COLUMN_PASSWORD} = ?"
                val cursor = db.rawQuery(query, arrayOf(username, password))

                if (cursor.count > 0) {
                    cursor.moveToFirst() // Kullanıcıyı bulduk
                    Toast.makeText(requireContext(), "Giriş Başarılı", Toast.LENGTH_SHORT).show()

                    // Ana sayfaya yönlendir
                    val intent = Intent(activity, MainProfil::class.java)
                    startActivity(intent)
                    activity?.finish() // Bu fragment'ı sonlandır, geri dönüşte tekrar görülmesin
                } else {
                    Toast.makeText(requireContext(), "Kullanıcı adı veya parola yanlış", Toast.LENGTH_SHORT).show()
                }

                cursor.close()
                db.close()
            } else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show()
            }
        }
        //Kayıt Ol Sayfasına Yönlendir
        binding.kayitBtn.setOnClickListener() {
            val intent = Intent(activity, KayitSayfasi::class.java)
            startActivity(intent)
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}