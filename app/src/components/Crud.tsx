import { useEffect, useState } from "react";
import axios from "axios";

export interface AppUser {
    id: number;
    username: string;
    password: string;
    totalGotPoints: number;
}

export interface Point {
    id: number;
    name: string;
    altitude: number;
    type: string;
}

export interface Trail {
    id: number;
    startPointId: number; 
    endPointId: number; 
    gotPoints: number; 
    color: string;
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


export default function Crud<R extends WithId>({ tableName, defaultItem, inputs }: Props<R>) {
    const [items, setItems] = useState<R[]>([]);
    const [newItem, setNewItem] = useState(defaultItem);

    // GET
    const getItems = () => {
        console.log(inputs);
        axios.get<R[]>(`http://localhost:8080/${tableName}`)
            .then((response) => setItems(response.data))
            .catch((error) => console.error("Error fetching items:", error))
    };
    useEffect(getItems, []);

    // PUT
    const updateItem = (id: number, updatedItem: Omit<R, "id">) => {
        axios.put(`http://localhost:8080/${tableName}`, updatedItem, { params: { id: id } })
            .then(() => {
                getItems();
            })
            .catch((error) => console.error("Error updating item:", error));
    };

    // DELETE
    const deleteItem = (id: number) => {
        axios.delete(`http://localhost:8080/${tableName}`, { params: { id: id } })
            .then(() => {
                getItems();
            })
            .catch((error) => console.error("Error deleting item:", error));
    };

    // POST
    const addItem = () => {
        console.log(newItem);
        axios.post<R>(`http://localhost:8080/${tableName}`, newItem)
            .then(() => {
                getItems();
                setNewItem(defaultItem);
            })
            .catch((error) => console.error("Error adding item:", error));
    };

    const handleInputChange = (id: number, field: keyof R, value: string) => {
        setItems((prevItems) =>
            prevItems.map((item) => (item.id === id ? { ...item, [field]: value } : item))
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
                                                    value={value}
                                                    onChange={(e) => handleInputChange(item.id, input.key, e.target.value)}
                                                /> :
                                                value
                                        }
                                    </td>
                                })
                            }
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
                                        value={newItem[key] as string}
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
        </div>
    );
};
