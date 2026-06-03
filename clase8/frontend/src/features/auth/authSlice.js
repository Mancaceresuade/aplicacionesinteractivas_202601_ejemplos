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
