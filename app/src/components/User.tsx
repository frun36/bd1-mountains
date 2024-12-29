import { useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";
import api from "../api";
import RouteCard, { RouteInfo } from "./RouteCard";

type UserParams = {
    id: string;
};

export default function User() {
    const { id } = useParams<UserParams>();
    const [searchParams] = useSearchParams();
    const loggedIn = searchParams.get('loggedIn') === 'true';

    const [routeList, setRouteList] = useState<RouteInfo[]>([]);

    useEffect(() => {
        api.get(`/users/${id}/routes`)
            .then((response) => setRouteList(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    }, [])

    return <div>
        {routeList.map((route, id) => <RouteCard key={id} info={route} editable={loggedIn} />)}
    </div>;
}