import Button from "react-bootstrap/Button";
import ApiResponsePanel, { ApiResponse } from "./ApiResponsePanel";
import { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function Reset() {
    const [responses, setResponses] = useState<ApiResponse[]>([]);

    const performReset = () => {
        axios.get("http://localhost:8080/reset")
            .then((response) => setResponses((oldResponses) => [...oldResponses, { status: response.status, body: response.data }]))
            .catch((error) => setResponses((oldResponses) => [...oldResponses, { status: error.response?.status || null, body: error.response?.data || "<empty>" }]));
    }

    return <div>
        <h1>Reset DB</h1>
        <p><Link to="/">back</Link></p>
        <Button variant="danger" onClick={performReset}>Perform reset</Button>
        <ApiResponsePanel responses={responses} />
    </div>
}