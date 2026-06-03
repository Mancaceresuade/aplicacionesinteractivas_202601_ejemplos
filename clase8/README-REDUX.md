# Guía: Redux Toolkit para login (JWT)

Esta guía es un **extra** del proyecto y complementa el `README.md` principal.

La idea es implementar el **login** contra el backend de este repo usando **Redux Toolkit** para guardar el token JWT en un **store global**.

---

## Qué es Redux

**Redux** es una librería para manejar **estado global** de forma predecible usando un **store** central.

- El estado se lee con `useSelector`.
- Los cambios se hacen despachando acciones con `useDispatch`.
- Con **Redux Toolkit** se reduce muchísimo el “boilerplate” (lo recomendado hoy).

---

## Para qué sirve (y cuándo conviene)

Conviene cuando el estado (por ejemplo **autenticación**, usuario, permisos, etc.) se usa en muchas pantallas y querés un flujo claro de “qué pasó y por qué”.

Si el proyecto es chico, también podés resolverlo con estado local o Context.

---

## Alternativas (para mencionar en clase)

- **React Context + hooks**: simple para casos chicos/medios.
- **Zustand**: muy liviano y directo para estado global.
- **TanStack Query**: ideal para “estado de servidor” (fetch/caché/sync). Se puede combinar con Redux si hace falta.

---

## Guía paso a paso: login con Redux Toolkit

> Esta guía asume que ya tenés el proxy del `README.md` (Vite) configurado y por eso el front llama al backend con rutas que empiezan con `/api`.

### Paso A: Instalar dependencias

En `frontend/`:

```bash
npm install @reduxjs/toolkit react-redux
```

---

### Paso B: Crear el store

Creá `src/store.js`:

```js
import { configureStore } from '@reduxjs/toolkit'
import authReducer from './features/auth/authSlice'

export const store = configureStore({
  reducer: {
    auth: authReducer,
  },
})
```

---

### Paso C: Conectar Redux a React (Provider)

En `src/main.jsx`, envolvé `App` con `Provider`:

```jsx
import React from 'react'
import ReactDOM from 'react-dom/client'
import { Provider } from 'react-redux'
import { store } from './store'
import App from './App.jsx'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
)
```

---

### Paso D: Crear el slice de autenticación (login/logout)

Creá `src/features/auth/authSlice.js`.

Este backend expone `POST /auth/login` y devuelve `{ token: "..." }`. Con proxy, desde el front lo llamás como `POST /api/auth/login`.

```js
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit'

const TOKEN_KEY = 'token'

export const loginThunk = createAsyncThunk('auth/login', async ({ username, password }) => {
  const res = await fetch('/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password }),
  })

  if (!res.ok) {
    throw new Error('Credenciales inválidas')
  }

  const data = await res.json()
  return data.token
})

const initialState = {
  token: sessionStorage.getItem(TOKEN_KEY),
  status: 'idle', // idle | loading | succeeded | failed
  error: '',
}

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout: (state) => {
      state.token = null
      state.status = 'idle'
      state.error = ''
      sessionStorage.removeItem(TOKEN_KEY)
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginThunk.pending, (state) => {
        state.status = 'loading'
        state.error = ''
      })
      .addCase(loginThunk.fulfilled, (state, action) => {
        state.status = 'succeeded'
        state.token = action.payload
        sessionStorage.setItem(TOKEN_KEY, action.payload)
      })
      .addCase(loginThunk.rejected, (state, action) => {
        state.status = 'failed'
        state.error = action.error?.message ?? 'Error de login'
      })
  },
})

export const { logout } = authSlice.actions
export default authSlice.reducer
```

---

### Paso E: Usar Redux desde el formulario de login

Tu `LoginForm` ahora despacha `loginThunk` y lee `status/error` desde el store:

```jsx
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
```

En `App.jsx`, en vez de guardar el token en `useState`, lo leés con `useSelector`:

```jsx
import { useSelector } from 'react-redux'
import { LoginForm } from './LoginForm'
// import { ListaClientes } from './ListaClientes'

export default function App() {
  const token = useSelector((state) => state.auth.token)

  if (!token) return <LoginForm />

  // return <ListaClientes token={token} />
  return <p>Terminar lista de clientes</p>
}
```

---

### Paso F: Consumir endpoints protegidos usando el token del store

Tu API puede seguir recibiendo `token` por parámetro (como en el `README.md`), o podés leerlo desde Redux en el componente y pasarlo a `getClientes(token)`.

Ejemplo:

```js
const token = useSelector((state) => state.auth.token)
// luego: getClientes(token)
```

---

## Redux DevTools (depuración)

**Redux DevTools** es una extensión del navegador que te permite ver el estado del store, las acciones que se despachan y “viajar en el tiempo” entre estados anteriores. Es muy útil para entender qué hace tu slice de `auth` cuando hacés login o logout.

### Instalar la extensión

Instalá **Redux DevTools** en tu navegador:

- [Chrome](https://chromewebstore.google.com/detail/redux-devtools/lmhkpmbekcpmknklioeibfkpmmfibljd)
- [Firefox](https://addons.mozilla.org/firefox/addon/reduxdevtools/)
- [Edge](https://microsoftedge.microsoft.com/addons/detail/redux-devtools/nnkgneoiohoecpdiaponcejilbhhikei)

No hace falta instalar paquetes npm extra: **Redux Toolkit ya conecta DevTools** cuando usás `configureStore` en desarrollo (`devTools: true` por defecto).

### Ver el store en las DevTools

1. Levantá el frontend en modo desarrollo (`npm run dev` en `frontend/`).
2. Abrí la app en el navegador.
3. Abrí las herramientas de desarrollo (**F12** o clic derecho → Inspeccionar).
4. Buscá la pestaña **Redux** (aparece solo si la extensión está instalada y el store está activo).

Ahí vas a ver algo como:

| Panel | Qué muestra |
|-------|-------------|
| **State** | El estado actual, por ejemplo `auth: { token, status, error }` |
| **Action** | Cada acción despachada (`auth/login/pending`, `auth/login/fulfilled`, `logout`, etc.) |
| **Diff** | Qué campos cambiaron entre una acción y la siguiente |
| **Trace** | (opcional) De dónde se disparó la acción en el código |

### Probar el flujo de login

Con la app abierta y la pestaña Redux visible:

1. Ingresá usuario y contraseña y enviá el formulario.
2. Deberías ver una secuencia parecida a:
   - `auth/login/pending` → `status` pasa a `"loading"`.
   - `auth/login/fulfilled` → `token` se guarda y `status` a `"succeeded"`.
3. Si las credenciales fallan: `auth/login/rejected` → `status` `"failed"` y `error` con el mensaje.
4. Si implementás logout, al despachar `logout` el `token` vuelve a `null` y podés ver ese cambio en **Diff**.

Podés hacer clic en cualquier acción de la lista para ver el **state en ese momento** (time travel). El botón **Skip** / **Commit** sirve para explorar sin romper la app; en la práctica de clase alcanza con mirar **State** y la lista de **Action**.

### Si no aparece la pestaña Redux

- Confirmá que la extensión está instalada y habilitada.
- La app tiene que estar en **desarrollo** (build de producción suele desactivar DevTools).
- Tenés que tener el `<Provider store={store}>` envolviendo la app (Paso C).
- Recargá la página después de instalar la extensión.

### Configuración opcional del store

Por defecto no tenés que cambiar nada. Si querés un nombre más claro en DevTools o limitar el historial:

```js
export const store = configureStore({
  reducer: {
    auth: authReducer,
  },
  devTools: import.meta.env.DEV && {
    name: 'Clase7 - Auth',
    maxAge: 25,
  },
})
```

- `name`: cómo se llama la instancia en la extensión (útil si tenés varias apps abiertas).
- `maxAge`: cuántas acciones guarda en el historial.

Para desactivar DevTools (por ejemplo en un build concreto): `devTools: false`.

### Relación con React DevTools

Son complementarias:

- **React DevTools**: componentes, props, hooks.
- **Redux DevTools**: estado global y acciones del store.

Para depurar login conviene tener ambas: React para ver que `LoginForm` re-renderiza con `loading`/`error`, Redux para ver que `loginThunk` despachó bien y el `token` quedó en el store.

