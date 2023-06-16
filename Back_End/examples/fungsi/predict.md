# Predict

Melakukan prediksi penyakit tanaman (ALPA).

**URL** : `/fungsi/`

**Method** : `POST`

**Auth required** : YES

**Data Constraint**

```json
{
    "imageBase64": "[Base64]"
}
```
**Data Example**

```json
{
    "imageBase64": "/9j/4AAQSkZJRgABAQAAAQABAAD/"
}
```

## Success Response

**Code** : `200 OK`

**Content example**
```json
{
    "penyakit": "Mango sooty mould",
    "penanganan": "rawat dengan baik !",
    "imageUrl": "http://linkgambar.com"
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