# History

Mendapatkan History prediksi penyakit tanaman berdasarkan Id.

**URL** : `/getHistory/:id`

**Method** : `GET`

**Auth required** : YES

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "id": 2,
    "penyakit": "Nama penyakitnya",
    "penanganan": "Penanganannya",
    "imageUrl": "link untuk gambarnya"
}
```

## Error Response

**Code** : `404 NOT FOUND`

**Condition** : Ketika History yang dicari tidak ada

**Content** 
```json
{
    "statusCode": 404,
    "error": "Not Found",
    "message": "History tidak ditemukan !"
}
```

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
