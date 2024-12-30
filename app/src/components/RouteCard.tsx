import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import { Link } from "react-router-dom";

export interface RouteInfo {
    id: number;
    name: string;
    username: string;
    timeModified: string;
    totalGotPoints: number;
}

interface Props {
    info: RouteInfo;
    view: boolean;
    edit: boolean;
    deleteRoute?: (id: number) => void;
}

export default function RouteCard({ info, view, edit, deleteRoute }: Props) {
    return <Card>
        <Card.Header>
            <Card.Title>{info.name}</Card.Title>
            <Card.Subtitle className="text-muted">{info.username}</Card.Subtitle>
            <Card.Text className="text-secondary">{info.timeModified}</Card.Text>
        </Card.Header>
        <Card.Body>
            <Card.Text>GOT points: {info.totalGotPoints}</Card.Text>
            {view && <Link to={`/routes/${info.id}?edit=false`}><Button variant="primary">View</Button></Link>}
            {edit && <Link to={`/routes/${info.id}?edit=true`}><Button variant="warning">Edit</Button></Link>}
            {edit && <Button variant="danger" onClick={_ => deleteRoute && deleteRoute(info.id)}>Delete</Button>}
        </Card.Body>
    </Card>
}