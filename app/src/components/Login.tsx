import { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import api from "../api";
import { useNavigate } from "react-router-dom";
import ButtonGroup from "react-bootstrap/ButtonGroup";

interface LoginData {
    username: string;
    password: string;
}

export default function Login() {
    const navigate = useNavigate();

    const [loginData, setLoginData] = useState<LoginData>({ username: "", password: "" });

    const login = () => {
        api.get("/login", { params: loginData })
            .then((response) => callback(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    }

    const register = () => {
        api.get("/register", { params: loginData })
            .then((response) => callback(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    }

    const setUsername = (newUsername: string) => {
        setLoginData({ ...loginData, username: newUsername });
    }

    const setPassword = (newPassword: string) => {
        setLoginData({ ...loginData, password: newPassword });
    }

    const callback = (result: number) => {
        if (result) {
            const id: number = result;
            navigate(`/users/${id}?loggedIn=true`);
        } else {
            alert("Invalid login data");
        }
    }

    return <div>
        <Form.Group className="w-25 mx-auto" style={{ minWidth: "300px" }}>
            <h1>Login</h1>
            <Form.Label className="my-2">Username: </Form.Label>
            <Form.Control type="text" value={loginData.username} onChange={(e) => setUsername(e.target.value)} />
            <Form.Label className="my-2">Password: </Form.Label>
            <Form.Control type="password" value={loginData.password} onChange={(e) => setPassword(e.target.value)} />
            <ButtonGroup className="w-100 my-3">
                <Button variant="primary" type="submit" onClick={login}>Login</Button>
                <Button variant="success" type="submit" onClick={register}>Register</Button>
            </ButtonGroup>
        </Form.Group>
    </div>
}