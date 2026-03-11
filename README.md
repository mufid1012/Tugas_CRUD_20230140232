# CRUD KTP — SISTEM MANAJEMEN DATA KTP

---

## TAMPILAN WEBSITE

<img width="1290" height="902" alt="image" src="https://github.com/user-attachments/assets/cdbda2e8-fe29-4b35-96a3-58f8de6c1dd0" />

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
    "namaLengkap": "Ilham Subianto",
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
        "namaLengkap": "Ilham Subianto",
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
            "namaLengkap": "Ilham Subianto",
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
        "namaLengkap": "Ilham Subianto",
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
    "namaLengkap": "Ilham Subianto Updated",
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
