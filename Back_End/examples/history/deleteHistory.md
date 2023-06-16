# DeleteHistory

Menghapus History prediksi penyakit tanaman (BETA).

**URL** : `/deleteHistory/`

**Method** : `POST`

**Auth required** : YES

**Data Constraint**

```json
{
    "historyIds": "[idHistory, idHistory]"
}
```
**Data Example**

```json
{
    "historyIds":[14,15]
}
```

## Success Response

**Code** : `200 OK`

**Content example**

{
    "message": "History berhasil dihapus !"
}

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