# History

Mendapatkan semua History prediksi penyakit tanaman .

**URL** : `/fungsi/`

**Method** : `GET`

**Auth required** : YES

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "history": [
        {
            "id": 1,
            "result": "Mango sooty mould",
            "imageUrl": "http://linkgambar.com",
            "createdAt": "2023-06-14T20:38:09.000Z"
        },
        {
            "id": 2,
            "result": "Nama penyakit",
            "imageUrl": "link gambar",
            "createdAt": "2023-06-14T20:53:19.000Z"
        }
    ]
}
```

## Error Response

**Code** : `401 BAD REQUEST`

**Condition** : Ketika gunakan token yang sudah digunakan logout

**Content**
```json
{
    "statusCode": 401,
    "error": "Unauthorized",
    "message": "Harap login kembali"
}
```

**Condition** : Ketika token tidak ditemukan

**Content**
```json
{
    "statusCode": 401,
    "error": "Unauthorized",
    "message": "Token tidak ditemukan"
}
```

**Condition** : Ketika token tidak benar

**Content**
```json
{
    "statusCode": 401,
    "error": "Unauthorized",
    "message": "invalid token"
}
```
