import { Routes, Route, useNavigate } from 'react-router-dom';
import Raw from "./components/Raw";
import MainMenu from './components/MainMenu';
import Reset from './components/Reset';
import Login from './components/Login';
import User from './components/User';
import RouteEditor from './components/RouteEditor';

export default function App() {
    const navigate = useNavigate();

    return <Routes>
        <Route path="/" element={<MainMenu />} />
        <Route path="/raw" element={<Raw />} />
        <Route path="/reset" element={<Reset />} />
        <Route path="/login" element={<Login callback={(result) => {
            if (result) {
                const id: number = result;
                navigate(`/users/${id}`);
            } else {
                alert("Invalid login data");
            }
        }} />} />
        <Route path="/users/:id" element={<User />} />
        <Route path="/routes/edit/:id" element={<RouteEditor />} />
    </Routes>

}
