# CRUD KTP — Spring Boot & jQuery

Aplikasi CRUD (Create, Read, Update, Delete) untuk manajemen data **Kartu Tanda Penduduk (KTP)** menggunakan **Spring Boot** (backend) dan **HTML/CSS/jQuery Ajax** (frontend).

---

## Teknologi

| Layer    | Teknologi                          |
|----------|------------------------------------|
| Backend  | Spring Boot 3.2, Spring Data JPA   |
| Database | MySQL (schema: `spring`)           |
| Frontend | HTML5, CSS3, JavaScript, jQuery    |

---

## Struktur Proyek

```
src/
├── main/
│   ├── java/com/example/ktp/
│   │   ├── KtpApplication.java              # Main class
│   │   ├── controller/
│   │   │   └── KtpController.java           # REST Controller
│   │   ├── dto/
│   │   │   ├── KtpRequestDto.java           # Request DTO
│   │   │   └── KtpResponseDto.java          # Response DTO
│   │   ├── entity/
│   │   │   └── Ktp.java                     # JPA Entity
│   │   ├── exception/
│   │   │   ├── DuplicateResourceException.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── ResourceNotFoundException.java
│   │   ├── mapper/
│   │   │   └── KtpMapper.java               # Entity <-> DTO Mapper
│   │   ├── repository/
│   │   │   └── KtpRepository.java           # Data Access Layer
│   │   ├── service/
│   │   │   ├── KtpService.java              # Service Interface
│   │   │   └── impl/
│   │   │       └── KtpServiceImpl.java      # Service Implementation
│   │   └── util/
│   │       └── ApiResponse.java             # Generic API Response
│   └── resources/
│       ├── application.properties           # Konfigurasi database
│       └── static/
│           ├── index.html                   # Halaman web
│           ├── style.css                    # Styling
│           └── app.js                       # jQuery Ajax
```

---

## Persiapan

### 1. Database MySQL

Buat database `spring` di MySQL:

```sql
CREATE DATABASE IF NOT EXISTS spring;
```

> Tabel `ktp` akan otomatis dibuat oleh Hibernate saat aplikasi pertama kali dijalankan.

### 2. Konfigurasi Database

Edit file `src/main/resources/application.properties` sesuai kredensial MySQL Anda:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring
spring.datasource.username=root
spring.datasource.password=
```

### 3. Menjalankan Aplikasi

```bash
./mvnw spring-boot:run
```

Buka browser di **http://localhost:8080**

---

## API Documentation

Base URL: `http://localhost:8080`

Semua response API menggunakan format wrapper `ApiResponse`:

```json
{
    "status": 200,
    "message": "Pesan sukses/error",
    "data": { ... }
}
```

### 1. Tambah Data KTP

```
POST /ktp
Content-Type: application/json
```

**Request Body:**
```json
{
    "nomorKtp": "3201234567890001",
    "namaLengkap": "Budi Santoso",
    "alamat": "Jl. Merdeka No. 10, Jakarta",
    "tanggalLahir": "1990-05-15",
    "jenisKelamin": "Laki-laki"
}
```

**Response:** `201 Created`
```json
{
    "status": 201,
    "message": "Data KTP berhasil ditambahkan",
    "data": {
        "id": 1,
        "nomorKtp": "3201234567890001",
        "namaLengkap": "Budi Santoso",
        "alamat": "Jl. Merdeka No. 10, Jakarta",
        "tanggalLahir": "1990-05-15",
        "jenisKelamin": "Laki-laki"
    }
}
```

---

### 2. Ambil Semua Data KTP

```
GET /ktp
```

**Response:** `200 OK`
```json
{
    "status": 200,
    "message": "Berhasil mengambil data KTP",
    "data": [
        {
            "id": 1,
            "nomorKtp": "3201234567890001",
            "namaLengkap": "Budi Santoso",
            "alamat": "Jl. Merdeka No. 10, Jakarta",
            "tanggalLahir": "1990-05-15",
            "jenisKelamin": "Laki-laki"
        }
    ]
}
```

---

### 3. Ambil Data KTP berdasarkan ID

```
GET /ktp/{id}
```

**Response:** `200 OK`
```json
{
    "status": 200,
    "message": "Berhasil mengambil data KTP",
    "data": {
        "id": 1,
        "nomorKtp": "3201234567890001",
        "namaLengkap": "Budi Santoso",
        "alamat": "Jl. Merdeka No. 10, Jakarta",
        "tanggalLahir": "1990-05-15",
        "jenisKelamin": "Laki-laki"
    }
}
```

---

### 4. Perbarui Data KTP

```
PUT /ktp/{id}
Content-Type: application/json
```

**Request Body:**
```json
{
    "nomorKtp": "3201234567890001",
    "namaLengkap": "Budi Santoso Updated",
    "alamat": "Jl. Sudirman No. 20, Jakarta",
    "tanggalLahir": "1990-05-15",
    "jenisKelamin": "Laki-laki"
}
```

**Response:** `200 OK`

---

### 5. Hapus Data KTP

```
DELETE /ktp/{id}
```

**Response:** `200 OK`
```json
{
    "status": 200,
    "message": "Data KTP dengan id 1 berhasil dihapus",
    "data": null
}
```

---

## Error Responses

| Status | Deskripsi                        | Contoh                                                    |
|--------|----------------------------------|-----------------------------------------------------------|
| 400    | Validasi gagal                   | Field kosong atau format tidak valid                      |
| 404    | Data tidak ditemukan             | `Data KTP dengan id 99 tidak ditemukan`                  |
| 409    | Duplikasi data                   | `Nomor KTP 3201234567890001 sudah terdaftar`             |
| 500    | Kesalahan server                 | `Terjadi kesalahan pada server`                          |

**Contoh Error Response:**
```json
{
    "timestamp": "2026-03-11T10:00:00",
    "status": 404,
    "error": "Not Found",
    "message": "Data KTP dengan id 99 tidak ditemukan"
}
```

---

## Schema Database

Tabel `ktp` di database `spring`:

| Kolom          | Tipe         | Constraint              |
|----------------|--------------|-------------------------|
| id             | INT          | Primary Key, Auto Inc.  |
| nomor_ktp      | VARCHAR(255) | NOT NULL, UNIQUE        |
| nama_lengkap   | VARCHAR(255) | NOT NULL                |
| alamat         | VARCHAR(255) | NOT NULL                |
| tanggal_lahir  | DATE         | NOT NULL                |
| jenis_kelamin  | VARCHAR(255) | NOT NULL                |
