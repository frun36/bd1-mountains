import { useEffect, useState } from "react";
import { useParams, useSearchParams } from "react-router-dom";
import api from "../api";
import RouteCard, { RouteInfo } from "./RouteCard";

type UserParams = {
    id: string;
};

interface UserInfo {
    id: number;
    username: string;
    routeCount: number;
    avgRouteLen: number;
    totalGotPoints: number;
}

export default function User() {
    const { id } = useParams<UserParams>();
    const [searchParams] = useSearchParams();
    const loggedIn = searchParams.get('loggedIn') === 'true';

    const [userInfo, setUserInfo] = useState<UserInfo>();
    const [routeList, setRouteList] = useState<RouteInfo[]>([]);

    useEffect(() => {
        api.get(`/users/${id}`)
            .then((response) => setUserInfo(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));

        api.get(`/users/${id}/routes`)
            .then((response) => setRouteList(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    }, [])

    return <div className="w-25 mx-auto">
        <h1>{userInfo?.username}</h1>
        <h5 className="text-muted">GOT points: {userInfo?.totalGotPoints}</h5>
        <h3>Routes ({userInfo?.routeCount}):</h3>
        {
            routeList.map((route, id) => <div className="my-3">
                <RouteCard key={id} info={route} edit={loggedIn} view={true} />
            </div>)
        }
    </div>;
}