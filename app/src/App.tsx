import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Crud, { AppUser, Point } from './components/Crud';


const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/app_user" element={
                    <Crud<AppUser>
                        tableName='app_user'
                        defaultItem={{ username: '', password: '', totalGotPoints: 0, }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "text", key: "username", editable: true },
                            { type: "text", key: "password", editable: true },
                            { type: "number", key: "totalGotPoints", editable: true },
                        ]} />
                } />
                <Route path="/point" element={
                    <Crud<Point>
                        tableName='point'
                        defaultItem={{ name: '', altitude: 0, type: '', }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "text", key: "name", editable: true },
                            { type: "number", key: "altitude", editable: true },
                            { type: "text", key: "type", editable: true },
                        ]} />
                } />

            </Routes>
        </Router>
    );
}

export default App;
