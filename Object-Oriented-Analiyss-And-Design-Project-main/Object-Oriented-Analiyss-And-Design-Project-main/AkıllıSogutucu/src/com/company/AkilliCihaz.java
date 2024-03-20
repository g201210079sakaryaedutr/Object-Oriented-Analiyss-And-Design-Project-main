package com.company;

import java.time.format.DateTimeFormatter;
public class AkilliCihaz {
    private String cihazIsmi;
    private String cihazSahibininAdı;
    private String cihazZamanDilimi;
    private int cihazOtoUykuModuSaat;
    private IEkran ekran;
    private ITusTakimi tusTakimi;
    private IEyleyici eyleyici;
    private ISicaklikAlgilayici sicaklikAlgilayici;
    private IAgArayuzu agArayuzu;
    private VeritabaniIslem veritabaniIslem;
    private Publisher publisher;
    private static final int SICAKLIK_GORUNTULE = 1;
    private static final int SOGUTUCUYU_AC = 2;
    private static final int SOGUTUCUYU_KAPAT = 3;
    private static final int CIKIS = 4;
    private AkilliCihaz(AkilliCihazBuilder builder) {
        ekran = new Ekran();
        tusTakimi = new TusTakimi();
        eyleyici = new Eyleyici();
        sicaklikAlgilayici = new SicaklikAlgilayici();
        agArayuzu = new AgArayuzu();
        veritabaniIslem = new VeritabaniIslem(new VeritabaniBaglanti());
        publisher = new Publisher();
        this.cihazIsmi = builder.cihazIsmi;
        this.cihazSahibininAdı =
                builder.cihazSahibininAdı;
        this.cihazZamanDilimi = builder.cihazZamanDilimi;
        this.cihazOtoUykuModuSaat =
                builder.cihazOtoUykuModuSaat;
    }
    public void baslat() {
        // Bildirim gidecek modüller ayarlanıyor...
        publisher.attach((IObserver) agArayuzu);
        publisher.attach((IObserver) eyleyici);
        boolean girisYapilmadiMi = true;
        do {
            ekran.mesajGoruntule("Kullanıcı Adınızı Giriniz :" );
            String kullaniciAdi =
                    tusTakimi.stringVeriAl();
            ekran.mesajGoruntule("Şifrenizi Giriniz: ");
            String sifre = tusTakimi.stringVeriAl();

            if(veritabaniIslem.kullaniciDogrula(kullaniciAdi, sifre))
            {
                ekran.mesajGoruntule("Giriş yapıldı!  Menüye yönlendiriliyor... \n");
                girisYapilmadiMi = false;
            } else {
                ekran.hataMesajiGoruntule("Kullanıcı adınız veya şifreniz hatalı, tekrar deneyiniz...");
            }
        } while(girisYapilmadiMi);
        menuSecimleri();
    }
    public void menuSecimleri() {
        int secim;
        do {
            ekran.menuYazdir();
            secim = tusTakimi.intVeriAl();
            switch (secim) {
                case SICAKLIK_GORUNTULE:
                    String gelenSicaklik =
                            agArayuzu.sicaklikGonder(sicaklikAlgilayici);
                    ekran.mesajGoruntule("Anlık sıcaklık: " + gelenSicaklik);

                    if(Integer.parseInt(gelenSicaklik.substring(0, 2)) >= 30)
                        publisher.notify("Fazla sıcaklık uyarısı", gelenSicaklik);
                    break;
                case SOGUTUCUYU_AC:
                    agArayuzu.sogucutuAc(eyleyici);
                    break;
                case SOGUTUCUYU_KAPAT:
                    agArayuzu.sogutucuKapat(eyleyici);
                    break;
                case CIKIS:
                    ekran.mesajGoruntule("Çıkış yapılıyor...");
                    break;
                default:
                    ekran.hataMesajiGoruntule("1-4 arasında bir seçenek girmelisiniz");
                    break;
            }
        } while(secim != CIKIS);
    }
    public static class AkilliCihazBuilder {
        private String cihazIsmi;
        private String cihazSahibininAdı;
        private String cihazZamanDilimi;
        private int cihazOtoUykuModuSaat;
        public AkilliCihazBuilder(String cihazIsmi) {
            this.cihazIsmi = cihazIsmi;
        }
        public AkilliCihazBuilder sahibininAdı(String
                                                       cihazSahibininAdı) {
            this.cihazSahibininAdı = cihazSahibininAdı;
            return this;
        }
        public AkilliCihazBuilder ZamanDilimi(String
                                                      cihazZamanDilimi) {
            this.cihazZamanDilimi = cihazZamanDilimi;
            return this;
        }
        public AkilliCihazBuilder OtoUykuModuSaat(int
                                                          cihazOtoUykuModuSaat) {
            this.cihazOtoUykuModuSaat =
                    cihazOtoUykuModuSaat;
            return this;
        }
        public AkilliCihaz build() {
            return new AkilliCihaz(this);
        }
    }
}
