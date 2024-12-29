import { Routes, Route } from 'react-router-dom';
import Raw from "./components/Raw";
import MainMenu from './components/MainMenu';
import Reset from './components/Reset';
import Login from './components/Login';
import User from './components/User';
import RouteViewer from './components/RouteEditor';

export default function App() {
    return <Routes>
        <Route path="/" element={<MainMenu />} />
        <Route path="/raw" element={<Raw />} />
        <Route path="/reset" element={<Reset />} />
        <Route path="/login" element={<Login />} />
        <Route path="/users/:id" element={<User />} />
        <Route path="/routes/:id" element={<RouteViewer />} />
    </Routes>

}
