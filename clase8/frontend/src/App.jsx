import { useSelector } from 'react-redux'
import { LoginForm } from './LoginForm'
import { ListaClientes } from './ListaClientes'

export default function App() {
  const token = useSelector((state) => state.auth.token)

  if (!token) return <LoginForm />

  return <ListaClientes token={token} />
  // return <p>Terminar lista de clientes</p>
}
