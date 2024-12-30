import { useEffect, useState } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import api from "../api";
import RouteCard, { RouteInfo } from "./RouteCard";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

type UserParams = {
    id: string;
};

export interface UserInfo {
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

    const navigate = useNavigate();

    const [userInfo, setUserInfo] = useState<UserInfo>();
    const [routeList, setRouteList] = useState<RouteInfo[]>([]);
    const [newRouteName, setNewRouteName] = useState<string>("");

    const getRoutes = () => {
        api.get(`/users/${id}/routes`)
            .then((response) => setRouteList(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    }

    const getUserInfo = () => {
        api.get(`/users/${id}`)
            .then((response) => setUserInfo(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    }

    useEffect(() => {
        getRoutes();
        getUserInfo();
    }, [])

    const deleteRoute = (id: number) => {
        api.delete(`/routes/${id}`)
            .then(r => {
                console.log(r);
                getRoutes();
            })
            .catch(e => alert(e + "\n" + e.response?.data))
    }

    const createRoute = (name: string) => {
        api.post(`/routes`, { userId: id, name: name })
            .then(r => {
                const routeId = r.data;
                navigate(`/routes/${routeId}?edit=true`);
            })
            .catch(e => alert(e + "\n" + e.response?.data))
    }

    return <div className="w-25 mx-auto">
        <h1>{userInfo?.username}</h1>
        <h5 className="text-muted">GOT points: {userInfo?.totalGotPoints}</h5>
        <h3>Routes ({userInfo?.routeCount}):</h3>
        {
            routeList.map((route, id) => <div className="my-3">
                <RouteCard key={id} info={route} edit={loggedIn} view={true} deleteRoute={deleteRoute} />
            </div>)
        }
        {
            loggedIn &&
            <Form>
                <Form.Control type="text" onChange={e => setNewRouteName(e.target.value)} />
                <Button variant="success" onClick={_ => createRoute(newRouteName)}>New route</Button>
            </Form>
        }
    </div>;
}