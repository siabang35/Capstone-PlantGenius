# Login

Used to collect a Token for a registered User.

**URL** : `/login/`

**Method** : `POST`

**Auth required** : NO

**Data constraints**

```json
{
    "email": "[valid email address]",
    "password": "[password in plain text]"
}
```

**Data example**

```json
{
    "email": "Bambang@example.com",
    "password": "abdulX@zy/"
}
```

## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "token": "93144b288eb1fdccbe46d6fc0f241a51766ecd3d",
    "email": "sutrisno@email.com",
    "name": "sutrisno"
}
```

## Error Response

**Code** : `400 BAD REQUEST`

**Condition** : Jika Email dan password tidak benar.

**Content** :

```json
{
    "message": "Email tidak terdaftar"
}
```
```json
{
    "message": "Password salah!"
}
```
**Condition** : Jika email kosong.
**Content** :
```json
{
    "message": "Email diperlukan"
}
```
