import Tabs from "react-bootstrap/Tabs";
import Tab from "react-bootstrap/Tab";
import Crud from "./Crud";
import { Link } from "react-router-dom";

interface AppUser {
    id: number;
    username: string | null;
    password: string | null;
    totalGotPoints: number | null;
}

interface Point {
    id: number;
    name: string | null;
    altitude: number | null;
    type: string | null;
}

interface Trail {
    id: number;
    startPointId: number | null;
    endPointId: number | null;
    gotPoints: number | null;
    color: string | null;
}

interface Route {
    id: number;
    name: string | null;
    userId: number | null;
    timeModified: string | null;
}

interface RouteTrail {
    id: number;
    routeId: number | null;
    trailId: number | null;
    prevId: number | null;
    nextId: number | null;
}

export default function Raw() {
    return <div>
            <h1>Raw operations</h1>
            <p><Link to="/">back</Link></p>
            <Tabs defaultActiveKey="appUser">
                <Tab eventKey="appUser" title="app_user">
                    <Crud<AppUser>
                        tableName="app_user"
                        defaultItem={{ username: null, password: null, totalGotPoints: null }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "text", key: "username", editable: true },
                            { type: "text", key: "password", editable: true },
                            { type: "number", key: "totalGotPoints", editable: true },
                        ]}
                    />
                </Tab>
                <Tab eventKey="point" title="point">
                    <Crud<Point>
                        tableName="point"
                        defaultItem={{ name: null, altitude: null, type: null }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "text", key: "name", editable: true },
                            { type: "number", key: "altitude", editable: true },
                            { type: "text", key: "type", editable: true },
                        ]}
                    />
                </Tab>
                <Tab eventKey="trail" title="trail">
                    <Crud<Trail>
                        tableName="trail"
                        defaultItem={{ startPointId: null, endPointId: null, gotPoints: null, color: null }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "number", key: "startPointId", editable: true },
                            { type: "number", key: "endPointId", editable: true },
                            { type: "number", key: "gotPoints", editable: true },
                            { type: "text", key: "color", editable: true },
                        ]}
                    />
                </Tab>
                <Tab eventKey="route" title="route">
                    <Crud<Route>
                        tableName="route"
                        defaultItem={{ name: null, userId: null, timeModified: null }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "text", key: "name", editable: true },
                            { type: "number", key: "userId", editable: true },
                            { type: "text", key: "timeModified", editable: true },
                        ]}
                    />
                </Tab>
                <Tab eventKey="routeTrail" title="route_trail">
                    <Crud<RouteTrail>
                        tableName="route_trail"
                        defaultItem={{ routeId: null, trailId: null, prevId: null, nextId: null }}
                        inputs={[
                            { type: "number", key: "id", editable: false },
                            { type: "number", key: "routeId", editable: true },
                            { type: "number", key: "trailId", editable: true },
                            { type: "number", key: "prevId", editable: true },
                            { type: "number", key: "nextId", editable: true },
                        ]}
                    />
                </Tab>
            </Tabs>
        </div>
}