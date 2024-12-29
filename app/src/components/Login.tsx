import { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import api from "../api";

interface LoginData {
    username: string;
    password: string;
}

interface Props {
    callback: (result: number | null) => void;
}

export default function Login({ callback }: Props) {
    const [loginData, setLoginData] = useState<LoginData>({ username: "", password: "" });

    const login = () => {
        api.get("/login", { params: loginData })
            .then((response) => callback(response.data))
            .catch((e) => alert(e + "\n" + e.response?.data));
    }

    const setUsername = (newUsername: string) => {
        setLoginData({ ...loginData, username: newUsername });
    }

    const setPassword = (newPassword: string) => {
        setLoginData({ ...loginData, password: newPassword });
    }

    return <div>
        <Form.Group className="w-25 mx-auto">
            <h1>Login</h1>
            <Form.Label>Username: </Form.Label>
            <Form.Control type="text" value={loginData.username} onChange={(e) => setUsername(e.target.value)} />
            <Form.Label>Password: </Form.Label>
            <Form.Control type="password" value={loginData.password} onChange={(e) => setPassword(e.target.value)} />
            <Button variant="success" type="submit" onClick={login}>Login</Button>
        </Form.Group>
    </div>
}