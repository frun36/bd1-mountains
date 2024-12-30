import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import { useEffect, useState } from "react";
import ButtonGroup from "react-bootstrap/ButtonGroup";

export interface ApiResponse {
    status: number | null;
    body: any;
}

interface Props {
    responses: ApiResponse[];
}

export default function ApiResponsePanel({ responses }: Props) {
    const [responseIdx, setResponseIdx] = useState<number>(-1);

    useEffect(() => {
        setResponseIdx(responses.length > 1 ? responses.length - 2 : responses.length - 1);
    }, [responses]);

    const isSuccess = responseIdx != -1 && responses[responseIdx].status && responses[responseIdx].status >= 200 && responses[responseIdx].status < 300;

    return responseIdx != -1 ?
        <div>
            <h2>API response ({responseIdx + 1}/{responses.length})</h2>
            <ButtonGroup>
                <Button variant="dark" onClick={() => setResponseIdx((currIdx) => currIdx > 0 ? currIdx - 1 : currIdx)}>◀</Button>
                <Button variant="dark" onClick={() => setResponseIdx((currIdx) => currIdx < responses.length - 1 ? currIdx + 1 : currIdx)}>▶</Button>
            </ButtonGroup>

            <Alert variant={isSuccess ? "success" : "danger"}>
                <h3
                    style={{
                        color: isSuccess ? "green" : "red",
                    }}>{responses[responseIdx].status ?? "Unexpected"}</h3>
                <p>{isSuccess ? (JSON.stringify(responses[responseIdx].body, null, 2) ?? "<empty>") : responses[responseIdx].body}</p>
            </Alert>
        </div> : null
}