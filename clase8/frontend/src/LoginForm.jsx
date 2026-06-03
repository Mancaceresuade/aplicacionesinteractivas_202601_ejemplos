import { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { loginThunk } from './features/auth/authSlice'

export function LoginForm() {
  const dispatch = useDispatch()
  const status = useSelector((state) => state.auth.status)
  const error = useSelector((state) => state.auth.error)

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  async function handleSubmit(e) {
    e.preventDefault()
    await dispatch(loginThunk({ username, password }))
  }

  const loading = status === 'loading'

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="username">Usuario</label>
        <input
          id="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          disabled={loading}
        />
      </div>
      <div>
        <label htmlFor="password">Contraseña</label>
        <input
          id="password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          disabled={loading}
        />
      </div>
      {error ? <p role="alert">{error}</p> : null}
      <button type="submit" disabled={loading}>
        {loading ? 'Ingresando…' : 'Ingresar'}
      </button>
    </form>
  )
}
