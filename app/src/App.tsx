import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Raw from "./components/Raw";
import MainMenu from './components/MainMenu';
import Reset from './components/Reset';

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainMenu />} />
                <Route path="/raw" element={<Raw />} />
                <Route path="/reset" element={<Reset />} />
            </Routes>
        </Router>
    );
}
