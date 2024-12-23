import { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import Alert from "react-bootstrap/Alert";
import Form from "react-bootstrap/Form";
import axios from "axios";

interface WithId {
    id: number;
}

interface Input<T extends WithId> {
    type: string;
    key: Extract<keyof T, string>;
    editable: boolean;
}

interface Props<T extends WithId> {
    tableName: string;
    defaultItem: Omit<T, "id">;
    inputs: Input<T>[];
}

interface ApiResponse {
    status: number | null;
    body: any;
}


export default function Crud<R extends WithId>({ tableName, defaultItem, inputs }: Props<R>) {
    const [items, setItems] = useState<R[]>([]);
    const [newItem, setNewItem] = useState(defaultItem);
    const [responses, setResponses] = useState<ApiResponse[]>([]);
    const [responseIdx, setResponseIdx] = useState<number>(-1);

    // GET
    const getItems = () => {
        axios.get<R[]>(`http://localhost:8080/raw/${tableName}`)
            .then((response) => {
                displayResponse({ status: response.status, body: response.data });
                if (response.data) {
                    setItems(response.data);
                }
            })
            .catch(displayError)
    };
    useEffect(getItems, []);

    // PUT
    const updateItem = (id: number, updatedItem: Omit<R, "id">) => {
        axios.put(`http://localhost:8080/raw/${tableName}/${id}`, updatedItem)
            .then((response) => {
                displayResponse({ status: response.status, body: response.data });
                getItems();
            })
            .catch(displayError)
    };

    // DELETE
    const deleteItem = (id: number) => {
        axios.delete(`http://localhost:8080/raw/${tableName}/${id}`)
            .then((response) => {
                displayResponse({ status: response.status, body: response.data });
                getItems();
            })
            .catch(displayError)
    };

    // POST
    const addItem = () => {
        console.log(newItem);
        axios.post<R>(`http://localhost:8080/raw/${tableName}`, newItem)
            .then((response) => {
                displayResponse({ status: response.status, body: response.data });
                getItems();
                setNewItem(defaultItem);
            })
            .catch(displayError)
    };

    const handleInputChange = (id: number, field: keyof R, value: string) => {
        setItems((prevItems) => {
            const parsedValue = (value == "" ? null : value);
            return prevItems.map((item) => (item.id === id ? { ...item, [field]: parsedValue } : item));
        }
        );

    };

    const displayResponse = (newResponse: ApiResponse) => {
        setResponses((prevResponses) => {
            const newResponses = [...prevResponses, newResponse];
            setResponseIdx(newResponses.length - 1);
            return newResponses;
        });
    }

    const displayError = (error: any) => {
        if (axios.isAxiosError(error)) {
            displayResponse({ status: error.response?.status || null, body: error.response?.data || "<empty>" });
        } else {
            displayResponse({ status: null, body: "Unexpected error" });
        }
        getItems();
    }

    return (
        <div className="w-50 p-3 mx-auto">
            <h1>{tableName}</h1>
            <Table>
                <thead>
                    <tr>
                        {
                            inputs.map((input, index) => (
                                <th key={index}>{input.key.toString()}</th>
                            ))
                        }
                        <td></td>
                        <td></td>
                    </tr>
                </thead>
                <tbody>
                    {items.map((item) => (
                        <tr key={item.id}>
                            {
                                inputs.map((input, index) => {
                                    const value = item[input.key] as string;
                                    return <td key={index}>
                                        {
                                            input.editable ?
                                                <Form.Group controlId={`input-${index}`}>
                                                    <Form.Control
                                                        type={input.type}
                                                        value={value ?? ""}
                                                        onChange={(e) => handleInputChange(item.id, input.key, e.target.value)}
                                                    />
                                                </Form.Group>
                                                : value
                                        }
                                    </td>
                                })
                            }
                            <td>
                                <Button onClick={() => {
                                    const { id, ...updatedItem } = item;
                                    updateItem(id, updatedItem);
                                }}>Update</Button>
                            </td>
                            <td>
                                <Button onClick={() => deleteItem(item.id)}>Delete</Button>
                            </td>
                        </tr>
                    ))}

                    <tr>
                        <td>New</td>
                        {
                            inputs.filter((input) => input.key != "id").map((input, index) => {
                                const key = input.key as unknown as keyof Omit<R, "id">;
                                return <td key={index}>
                                    <Form.Group controlId={`input-${index}`}>
                                        <Form.Control
                                            type={input.type}
                                            value={newItem[key] as string ?? ""}
                                            onChange={(e) => setNewItem({ ...newItem, [key]: e.target.value })}
                                        />
                                    </Form.Group>
                                </td>
                            })
                        }
                        <td>
                            <Button onClick={addItem}>Add</Button>
                        </td>

                        <td></td>
                    </tr>
                </tbody>
            </Table>
            <div>
                {
                    responseIdx != -1 ?
                        <div>
                            <h2>HTTP response ({responseIdx + 1}/{responses.length})</h2>
                            <Button onClick={() => setResponseIdx((currIdx) => currIdx > 0 ? currIdx - 1 : currIdx)}>Previous</Button>
                            <Button onClick={() => setResponseIdx((currIdx) => currIdx < responses.length - 1 ? currIdx + 1 : currIdx)}>Next</Button>
                            <Button onClick={() => { setResponses([]); setResponseIdx(-1) }}>Clear</Button>
                            <Alert variant={
                                responses[responseIdx].status && responses[responseIdx].status >= 200 && responses[responseIdx].status < 300
                                    ? "success"
                                    : "danger"}>

                                <h3
                                    style={{
                                        color: responses[responseIdx].status && responses[responseIdx].status >= 200 && responses[responseIdx].status < 300
                                            ? "green"
                                            : "red",
                                    }}>{responses[responseIdx].status ?? "Unexpected"}</h3>
                                <p>{JSON.stringify(responses[responseIdx].body) ?? "<empty>"}</p>
                            </Alert>
                        </div> : null
                }
            </div>
        </div>
    );
};
