# Register

Digunakan untuk mendaftarkan User

**URL** : `/register/`

**Method** : `POST`

**Auth required** : NO

**Data constraints**

```json
{
    "name": "[valid nama]",
    "email": "[valid alamat email]",
    "password": "[valid password]"
}
```

**Data example**

```json
{
    "name": "admin",
    "email": "admin@email.com",
    "password": "admin123"
}
```

## Success Response

**Code** : `201 Created`

**Content example**

```json
{
    "User": {
        "id": 1,
        "name": "admin",
        "email": "admin@email.com",
        "password": "$2b$10$FmSo.eRtImLh2UsDpE/Jm.Qdv3.Unvez6oFn7CCVPggRd423eOE2m",
        "updatedAt": "2023-06-02T02:38:38.490Z",
        "createdAt": "2023-06-02T02:38:38.490Z"
    }
}
```

## Error Response

**Code** : `400 BAD REQUEST`

**Condition** : Jika Menggunakan Email yang sama.

**Content** :

```json
{
    "message": "Validation Error",
    "details": {
        "email": {
            "message": "Email already registered"
        }
    }
}
```

**Condition** : Jika mengosongkan Email atau nomor telefon yang tidak sesuai format.

**Content** :
```json
{
    "message": "Validation Error",
    "details": {
        "email": {
            "message": "email tidak valid"
        }
    }
}
```

**Condition** : Jika nama, email dan password kosong.

**Content** :
```json
{
    "message": "Validation Error",
    "details": {
        "email": {
            "message": "email tidak boleh kosong"
        },
        "name": {
            "message": "name tidak boleh kosong"
        },
        "password": {
            "message": "Password tidak boleh kosong atau harus mengandung kombinasi huruf dan angka"
        }
    }
}
```

**Condition** : Jika password tidak berupa kombinasi.

**Content** :
```json
{
    "message": "Validation Error",
    "details": {
        "password": {
            "message": "Password tidak boleh kosong atau harus mengandung kombinasi huruf dan angka"
        }
    }
}
```

**Condition** : Jika password kurang dari 6 karakter dan lebih dari 23 karakter.

**Content** :
```json
{
    "message": "Validation Error",
    "details": {
        "password": {
            "message": "Password harus memiliki setidaknya 6 karakter"
        }
    }
}
```
```json
{
    "message": "Validation Error",
    "details": {
        "password": {
            "message": "Password tidak boleh lebih dari 23 karakter"
        }
    }
}
```
