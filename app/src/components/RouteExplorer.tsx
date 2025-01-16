import { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import Dropdown from "react-bootstrap/Dropdown";
import api from "../api";
import { Link } from "react-router-dom";

type Filter = "user_longest" | "highest_2000" | "three_colors";

interface RouteBrief {
    routeId: number;
    name: string;
    userId: number;
    username: string;
}

export default function RouteExplorer() {
    const [routes, setRoutes] = useState<RouteBrief[]>([]);
    const [filter, setFilter] = useState<Filter>("user_longest");

    const filterOptions = [
        { eventKey: "user_longest", text: "Longest route for each user" },
        { eventKey: "highest_2000", text: "Routes exceeding 2000 m" },
        { eventKey: "three_colors", text: "Routes using at least 3 different trail colors" },
    ];

    useEffect(() => {
        api.get(`/route_explorer`, { params: { filter: filter } })
            .then(r => setRoutes(r.data))
            .catch(e => alert(e + "\n" + e.response?.data))
    }, [filter]);

    return <div className="w-50 mx-auto" style={{ minWidth: "500px" }}>
        <h1>Route explorer</h1>
        <Dropdown onSelect={(eventKey: string | null) => {
            if (eventKey)
                setFilter(eventKey as Filter);
        }}>
            <Dropdown.Toggle>
                {"Filter by: " + filterOptions.find(option => option.eventKey === filter)?.text}
            </Dropdown.Toggle>

            <Dropdown.Menu>
                {filterOptions.map(option => (
                    <Dropdown.Item key={option.eventKey} eventKey={option.eventKey}>
                        {option.text}
                    </Dropdown.Item>
                ))}
            </Dropdown.Menu>
        </Dropdown>
        <Table>
            <thead>
                <tr>
                    <td>Name</td>
                    <td>Username</td>
                </tr>
            </thead>
            <tbody>
                {
                    routes.map((route, id) => <tr key={id}>
                        <td>
                            <Link to={`/routes/${route.routeId}?edit=false`}>{route.name}</Link>
                        </td>
                        <td>
                            <Link to={`/users/${route.userId}?loggedIn=false`}>{route.username}</Link>
                        </td>
                    </tr>)
                }
            </tbody>
        </Table>
    </div>
}