import { useEffect, useState } from "react";
import axios from "axios";

export interface AppUser {
    id: number;
    username: string | null;
    password: string | null;
    totalGotPoints: number | null;
}

export interface Point {
    id: number;
    name: string | null;
    altitude: number | null;
    type: string | null;
}

export interface Trail {
    id: number;
    startPointId: number | null;
    endPointId: number | null;
    gotPoints: number | null;
    color: string | null;
}

export interface Route {
    id: number;
    name: string | null;
    userId: number | null;
    timeModified: string | null;
}

export interface RoutePoint {
    id: number;
    routeId: number | null;
    currentPointId: number | null;
    previousPointId: number | null;
    nextPointId: number | null;
}

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
    }

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

    return (
        <div>
            <h1>{tableName}</h1>
            <table>
                <thead>
                    <tr>
                        {
                            inputs.map((input, index) => (
                                <th key={index}>{input.key.toString()}</th>
                            ))
                        }
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
                                                <input
                                                    type={input.type}
                                                    value={value ?? ""}
                                                    onChange={(e) => handleInputChange(item.id, input.key, e.target.value)}
                                                /> :
                                                value
                                        }
                                    </td>
                                })
                            }
                            <td>
                                <button onClick={() => {
                                    const { id, ...updatedItem } = item;
                                    updateItem(id, updatedItem);
                                }}>Update</button>
                            </td>
                            <td>
                                <button onClick={() => deleteItem(item.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}

                    <tr>
                        <td>New</td>
                        {
                            inputs.filter((input) => input.key != "id").map((input, index) => {
                                const key = input.key as unknown as keyof Omit<R, "id">;
                                return <td key={index}>
                                    <input
                                        type={input.type}
                                        value={newItem[key] as string ?? ""}
                                        onChange={(e) => setNewItem({ ...newItem, [key]: e.target.value })}
                                    />
                                </td>
                            })
                        }
                        <td>
                            <button onClick={addItem}>Add</button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div>
                {
                    responseIdx != -1 ?
                        <div>
                            <h2>HTTP response ({responseIdx + 1}/{responses.length})</h2>
                            <button onClick={() => setResponseIdx((currIdx) => currIdx > 0 ? currIdx - 1 : currIdx)}>Previous</button>
                            <button onClick={() => setResponseIdx((currIdx) => currIdx < responses.length - 1 ? currIdx + 1 : currIdx)}>Next</button>
                            <button onClick={() => { setResponses([]); setResponseIdx(-1) }}>Clear</button>
                            <h3
                                style={{
                                    color: responses[responseIdx].status && responses[responseIdx].status >= 200 && responses[responseIdx].status < 300
                                        ? "green"
                                        : "red",
                                }}>{responses[responseIdx].status ?? "Unexpected"}</h3>
                            <p>{JSON.stringify(responses[responseIdx].body) ?? "<empty>"}</p>
                        </div> : null
                }
            </div>
        </div>
    );
};
