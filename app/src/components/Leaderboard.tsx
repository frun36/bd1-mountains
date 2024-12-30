import { useEffect, useState } from "react";
import Dropdown from "react-bootstrap/Dropdown";
import api from "../api";
import { UserInfo } from "./User";
import Table from "react-bootstrap/Table";
import { Link } from "react-router-dom";

type Ordering = "total_got_points" | "avg_route_len" | "route_count";

export default function Leaderboard() {
    const [ordering, setOrdering] = useState<Ordering>("total_got_points");
    const [leaderboard, setLeaderboard] = useState<UserInfo[]>([]);

    const orderingOptions = [
        { eventKey: "total_got_points", text: "Total GOT points (main)" },
        { eventKey: "avg_route_len", text: "Average route GOT points" },
        { eventKey: "route_count", text: "Route count" },
    ];

    const getLeaderboard = (o: Ordering) => {
        api.get(`/users/leaderboard`, { params: { orderBy: o } })
            .then((response) => setLeaderboard(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    }

    useEffect(() => {
        getLeaderboard(ordering);
    }, [ordering])

    return <div className="w-50 mx-auto">
        <h1>User leaderboard</h1>
        <Dropdown onSelect={(eventKey: string | null) => {
            if (eventKey)
                setOrdering(eventKey as Ordering);
        }}>
            <Dropdown.Toggle>
                Order by: {orderingOptions.find(option => option.eventKey === ordering)?.text}
            </Dropdown.Toggle>

            <Dropdown.Menu>
                {orderingOptions.map(option => (
                    <Dropdown.Item key={option.eventKey} eventKey={option.eventKey}>
                        {option.text}
                    </Dropdown.Item>
                ))}
            </Dropdown.Menu>
        </Dropdown>
        <Table>
            <thead>
                <tr>
                    <td>Rank</td>
                    <td>Username</td>
                    <td>Route count</td>
                    <td>Average route GOT points</td>
                    <td>Total GOT points</td>
                </tr>
            </thead>
            <tbody>
                {
                    leaderboard.map((user, id) => <tr key={id}>
                        <td>{user.rank}</td>
                        <td><Link to={`/users/${user.id}?loggedIn=false`}>{user.username}</Link></td>
                        <td>{user.routeCount}</td>
                        <td>{user.avgRouteLen}</td>
                        <td>{user.totalGotPoints}</td>
                    </tr>)
                }
            </tbody>
        </Table>

    </div>
}