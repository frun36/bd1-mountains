import Button from "react-bootstrap/Button";
import ApiResponsePanel, { ApiResponse } from "./ApiResponsePanel";
import { useState } from "react";
import api from "../api";
import Spinner from "react-bootstrap/Spinner";

export default function Reset() {
    const [responses, setResponses] = useState<ApiResponse[]>([]);
    const [inProgress, setInProgress] = useState(false);

    const performReset = () => {
        setInProgress(true);
        api.get("/reset")
            .then((response) => {
                setResponses((oldResponses) => [...oldResponses, { status: response.status, body: response.data }]);
                setInProgress(false);
            })
            .catch((error) => {
                setResponses((oldResponses) => [...oldResponses, { status: error.response?.status || null, body: error.response?.data || "<empty>" }])
                setInProgress(false);
            });

    }

    return <div className="w-25 mx-auto">
        <h1>Reset DB</h1>
        <Button variant="danger" onClick={performReset} className="w-100 my-2">
            {inProgress && <Spinner size="sm" />}
            Perform reset
        </Button>
        <ApiResponsePanel responses={responses} />
    </div>
}