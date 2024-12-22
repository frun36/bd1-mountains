import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import Crud, { AppUser, Point, Trail, Route as RouteTable, RoutePoint } from './components/Crud';

function MainMenu() {
    return <div>
        <p><Link to="/raw">Raw operations</Link></p>
    </div>
}

function RawMenu() {
    return <div>
        <p><Link to="/raw/app_user">app_user</Link></p>
        <p><Link to="/raw/point">point</Link></p>
        <p><Link to="/raw/trail">trail</Link></p>
        <p><Link to="/raw/route">route</Link></p>
        <p><Link to="/raw/route_point">route_point</Link></p>
    </div>
}

export default function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainMenu />} />
                <Route path="/raw" element={<RawMenu />} />
                <Route path="/raw/app_user" element={
                    <Crud<AppUser>
                        tableName='app_user'
                        defaultItem={{ username: null, password: null, totalGotPoints: null, }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "text", key: "username", editable: true },
                            { type: "text", key: "password", editable: true },
                            { type: "number", key: "totalGotPoints", editable: true },
                        ]} />
                } />
                <Route path="/raw/point" element={
                    <Crud<Point>
                        tableName='point'
                        defaultItem={{ name: null, altitude: null, type: null, }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "text", key: "name", editable: true },
                            { type: "number", key: "altitude", editable: true },
                            { type: "text", key: "type", editable: true },
                        ]} />
                } />
                <Route path="/raw/trail" element={
                    <Crud<Trail>
                        tableName='trail'
                        defaultItem={{ startPointId: null, endPointId: null, gotPoints: null, color: null, }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "number", key: "startPointId", editable: true },
                            { type: "number", key: "endPointId", editable: true },
                            { type: "number", key: "gotPoints", editable: true },
                            { type: "text", key: "color", editable: true },
                        ]} />
                } />
                <Route path="/raw/route" element={
                    <Crud<RouteTable>
                        tableName='route'
                        defaultItem={{ name: null, userId: null, timeModified: null }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "text", key: "name", editable: true },
                            { type: "number", key: "userId", editable: true },
                            { type: "text", key: "timeModified", editable: true },
                        ]} />
                } />
                <Route path="/raw/route_point" element={
                    <Crud<RoutePoint>
                        tableName='route_point'
                        defaultItem={{ routeId: null, currentPointId: null, previousPointId: null, nextPointId: null }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "number", key: "routeId", editable: true },
                            { type: "number", key: "currentPointId", editable: true },
                            { type: "number", key: "previousPointId", editable: true },
                            { type: "number", key: "nextPointId", editable: true },
                        ]} />
                } />
            </Routes>
        </Router>
    );
}
