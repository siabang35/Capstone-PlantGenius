# Login

Memblokir token yang sudah melakukan logout.

**URL** : `/logout/`

**Method** : `POST`

**Auth required** : YES

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "message": "Logout Berhasil"
}
```

## Error Response

**Code** : `401 BAD REQUEST`

**Condition** : Ketika token yang sama digunakan setelah logout berhasil

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