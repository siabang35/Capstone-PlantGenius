
const validatePassword = (password, errors) => {
  if (!password) {
    errors.set('password', { message: 'Password tidak boleh kosong' })
  }

  if (password.length < 6) {
    errors.set('password', { message: 'Password harus memiliki setidaknya 6 karakter' })
  }

  if (password.length > 23) {
    errors.set('password', { message: 'Password tidak boleh lebih dari 23 karakter' })
  }

  const passwordRegex = /^(?=.*\d)(?=.*[a-zA-Z])/
  if (!passwordRegex.test(password)) {
    errors.set('password', { message: 'Password tidak boleh kosong atau harus mengandung kombinasi huruf dan angka' })
  }
}

const validateEmail = (email, errors) => {
  if (!email) {
    errors.set('email', { message: 'email tidak boleh kosong' })
  } else {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    if (!emailRegex.test(email)) {
      errors.set('email', { message: 'email tidak valid' })
    }
  }
}

const validateName = (name, errors) => {
  if (!name) {
    errors.set('name', { message: 'name tidak boleh kosong' })
  } else {
    const nameRegex = /^[a-zA-Z\s]+$/
    if (!nameRegex.test(name)) {
      errors.set('name', { message: 'name tidak valid' })
    }
  }
}

module.exports = { validatePassword, validateEmail, validateName }
