 StudyMap, kütüphanelerin dijitalleşmesiyle bilgiye erişimi kolaylaştırmayı ve
 kütüphane yönetim süreçlerini daha verimli hale getirmeyi amaçlayan bir
 mobil uygulamadır. Geleneksel kütüphaneler, fiziksel sınırlamaları nedeniyle
 kullanıcıların bilgiye anında ulaşmasını zorlaştırmaktadır. Bu proje,
 kullanıcıların mobil cihazları üzerinden kütüphane bilgilerine erişmesini, kitap
 satın almasını ve sipariş verebilmesini sağlamaktadır.
 Uygulama, kullanıcı ve admin olmak üzere iki farklı rol içerir. Kullanıcılar
 kitapları sepete ekleyip satın alabilir, sipariş geçmişlerini görüntüleyebilir ve
 bilgilerini güncelleyebilir. Admin kullanıcılar ise kütüphane ve kitap yönetimini
 gerçekleştirebilir. Google Maps API entegrasyonu ile kütüphanelerin konumları
 harita üzerinde kolayca bulunabilir hale getirilmiştir.
 StudyMap, Kotlin dili kullanılarak Android Studio üzerinde geliştirilmiş ve
 SQLite veritabanıyla desteklenmiştir. Kullanıcı dostu arayüzü ve güçlü altyapısı
 sayesinde hem kullanıcıların hem de yöneticilerin işlerini kolaylaştırmaktadır.
 Gelecekte çevrimdışı erişim ve çok dilli destek gibi özellikler eklenerek daha
 geniş bir kullanıcı kitlesine hitap edilmesi hedeflenmektedir.

  Kullanılan Teknolojiler:
 Geliştirme Dili: Kotlin
 Geliştirme Ortamı: Android Studio
 Veritabanı: SQLite
 API Entegrasyonu: Google Maps API

  Başlangıç
  // Kullanıcı Girişi
  Eğer kullanıcı "admin" ise
    Yönlendir(AdminPanel)  // Admin paneline yönlendir
  Aksi takdirde
    Yönlendir(KütüphaneListele)  // Kullanıcıyı kütüphaneler listesine yönlendir
  // Kütüphane Listeleme
  KütüphaneListele()
    Kütüphaneleri al(Veritabanından)  // Veritabanından kütüphaneleri al
    Her kütüphane için:
      - Kütüphane adı, açıklaması, kapasitesi, saatleri ve harita konumunu göster
    Eğer kullanıcı bir kütüphaneye tıklarsa
      KütüphaneDetayGoster(Kütüphane)  // Seçilen kütüphanenin detaylarını göster
  // Kütüphane Detayları Göster
  KütüphaneDetayGoster(Kütüphane)
    Kütüphane ismi, açıklaması, kapasite, saatler ve harita konumunu göster
    Kitapları Listele(Kütüphane)  // Seçilen kütüphanedeki kitapları listele
  // Kitap Listeleme
  Kitapları Listele(Kütüphane)
    Kitapları al(Kütüphane veritabanından)  // Veritabanından kütüphanedeki kitapları
 al
    Her kitap için:
      - Kitap adı, yazar, fiyatı ve diğer bilgileri göster
    Eğer kullanıcı bir kitabı seçerse
      SepeteEkle(Kitap)  // Kitap sepete eklenir
      // Sepet İşlemler
 SepeteEkle(Ktap)
 Sepet güncelle(Ktap ekle) // Sepete ktap ekler
 Sepet görüntüle() // Kullanıcıya sepet göster
 Eğer kullanıcı "Satın Al" butonuna tıklarsa
 SatınAl(Ktap, Kullanıcı) // Sepettek ktabı satın al
 // Satın Alma İşlem
 SatınAl(Ktap, Kullanıcı)
 Kullanıcı blgler le Sparş oluştur
 Sparş vertabanına kaydet // Sparş vertabanına kaydeder
 Sparş onayla(Admn) // Admn, sparş onaylar
 // Kullanıcı Prof l Güncelleme
 KullanıcıProf lGüncelle(Kullanıcı)
 Kullanıcı blglern al() // Kullanıcı blglern al
 Kullanıcı blglern düzenle() // Kullanıcı blglern güncelle
 Kullanıcı blglern kaydet() // Güncellenen blgler kaydet
 // Admn Panel
 AdmnPanel()
 Admn, Kütüphane ve Ktap Yönetm şlemler :
 - Kütüphane ekle, düzenle, sl
 - Ktap ekle, düzenle, sl
 - Kullanıcı blglern düzenle
 Sparşler onayla(Admn) // Admn, sparşler onaylar
 Btş
