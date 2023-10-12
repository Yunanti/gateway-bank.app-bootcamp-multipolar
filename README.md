# Dokumentasi Mini Project Back-End Developer Bootcamp Multipolar

Nama: Yunanti Moga Hasanah
Prodi: Matematika

##  Nama Aplikasi
Aplikasi ini bernama Bank Application

## Deskripsi Aplikasi
Aplikasi ini dibuat untuk mengelola layanan internet bank dari suatu client perbankan. Aplikasi ini menerapkan mikroservice agar mempermudah pengelolahan dan menghindari terjadinya error yang dapat mengganggu bagian-bagian fitur lainnya. Adapun untuk service yang akan dibuat terdapat 3 dalam aplikasi ini yaitu:
1. Product, berfungsi sebagai pengolahan informasi product bank seperti nama, tipe, rating ketertarikan, saldo minimal, jumlah pinjaman maksimal, syarat dan ketentuan, serta waktu dan tanggal transaksi.
2. Gateway, berfungsi sebagai pintu penghubung client atau pengguna dengan service-service yang ada di dalam aplikasi bank.
3. Logging, berfungsi sebagai penyimpan aktivitas apa saja yang dilakukan di service gateway.

## Pemakaian Gateway
1. Pengguna dapat mengakses service product melalui gateway terlebih dahulu.
2. Pengguna melakukan request membuat, membaca, memperbaiki, dan menghapus product pada gateway yang dapat menggunakan bantuan tools postman atau swagger. Pada gateway ini telah disediakan api dokumentasi melalui swagger yang dapat diakses melalui http://localhost:8080/swagger-ui/index.html.

## Tools Gateway
JAVA, SPRING BOOT, LOMBOK, SWAGGER, POSTMAN

# gateway-bank.app-bootcamp-multipolar
Pintu keluar-masuk client dan server dari Aplikasi Bank
