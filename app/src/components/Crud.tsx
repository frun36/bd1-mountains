import { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import ApiResponsePanel, { ApiResponse } from "./ApiResponsePanel";
import Form from "react-bootstrap/Form";
import axios from "axios";
import api from "../api";
import ButtonGroup from "react-bootstrap/ButtonGroup";

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

export default function Crud<R extends WithId>({ tableName, defaultItem, inputs }: Props<R>) {
    const [items, setItems] = useState<R[]>([]);
    const [newItem, setNewItem] = useState(defaultItem);
    const [responses, setResponses] = useState<ApiResponse[]>([]);

    const displayResponse = (newResponse: ApiResponse) => {
        setResponses((prevResponses) => [...prevResponses, newResponse]);
    }

    const displayError = (error: any) => {
        if (axios.isAxiosError(error)) {
            displayResponse({ status: error.response?.status || null, body: error.response?.data || "<empty>" });
        } else {
            displayResponse({ status: null, body: "Unexpected error" });
        }
        getItems();
    }

    // GET
    const getItems = () => {
        api.get<R[]>(`/raw/${tableName}`)
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
        api.put(`/raw/${tableName}/${id}`, updatedItem)
            .then((response) => {
                displayResponse({ status: response.status, body: response.data });
                getItems();
            })
            .catch(displayError)
    };

    // DELETE
    const deleteItem = (id: number) => {
        api.delete(`/raw/${tableName}/${id}`)
            .then((response) => {
                displayResponse({ status: response.status, body: response.data });
                getItems();
            })
            .catch(displayError)
    };

    // POST
    const addItem = () => {
        console.log(newItem);
        api.post<R>(`/raw/${tableName}`, newItem)
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
                                <ButtonGroup className="w-100">
                                    <Button variant="warning" onClick={() => {
                                        const { id, ...updatedItem } = item;
                                        updateItem(id, updatedItem);
                                    }}>Update</Button>
                                    <Button variant="danger" onClick={() => deleteItem(item.id)}>Delete</Button>
                                </ButtonGroup>
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
                            <Button className="w-100" variant="success" onClick={addItem}>Add</Button>
                        </td>
                    </tr>
                </tbody>
            </Table>
            <ApiResponsePanel responses={responses} />
        </div>
    );
};
