import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api";
import RouteEditCard from "./RouteEditCard";

type UserParams = {
    id: string;
};

interface Route {
    id: number;
    name: string;
    userId: number;
    timeModified: string;
}

export default function User() {
    const { id } = useParams<UserParams>();

    const [routeList, setRouteList] = useState<Route[]>([]);

    useEffect(() => {
        api.get(`/routes/user/${id}`)
            .then((response) => setRouteList(response.data))
            .catch((e) => alert(e));
    })

    return <div>
        {routeList.map((route) => <RouteEditCard id={route.id} name={route.name} timeModified={route.timeModified} />)}
    </div>;
}