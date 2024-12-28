import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Raw from "./components/Raw";
import MainMenu from './components/MainMenu';
import Reset from './components/Reset';
import Login from './components/Login';

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainMenu />} />
                <Route path="/raw" element={<Raw />} />
                <Route path="/reset" element={<Reset />} />
                <Route path="/login" element={<Login callback={(result) => {
                    if (result) {
                        alert("Successfully logged in");
                    } else {
                        alert("Invalid login data");
                    }
                }} />} />
            </Routes>
        </Router>
    );
}
