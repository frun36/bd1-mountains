import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Crud, { AppUser, Point, Trail } from './components/Crud';


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
                <Route path="/trail" element={
                    <Crud<Trail>
                        tableName='trail'
                        defaultItem={{ startPointId: 0, endPointId: 0, gotPoints: 0, color: '', }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "number", key: "startPointId", editable: true },
                            { type: "number", key: "endPointId", editable: true },
                            { type: "number", key: "gotPoints", editable: true },
                            { type: "text", key: "color", editable: true },
                        ]} />
                } />
            </Routes>
        </Router>
    );
}

export default App;
