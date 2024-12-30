import ButtonGroup from "react-bootstrap/ButtonGroup";
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import { useNavigate } from "react-router-dom";

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
    const navigate = useNavigate();

    return <Card>
        <Card.Header>
            <Card.Title>{info.name}</Card.Title>
            <Card.Subtitle className="text-muted">{info.username}</Card.Subtitle>
            <Card.Text className="text-secondary">{info.timeModified}</Card.Text>
        </Card.Header>
        <Card.Body>
            <Card.Text>GOT points: {info.totalGotPoints}</Card.Text>
            <ButtonGroup className="w-100">
                {view && (
                    <Button variant="primary" onClick={() => navigate(`/routes/${info.id}?edit=false`)}>
                        View
                    </Button>
                )}
                {edit && (
                    <Button variant="warning" onClick={() => navigate(`/routes/${info.id}?edit=true`)}>
                        Edit
                    </Button>
                )}
                {edit && <Button variant="danger" onClick={_ => deleteRoute && deleteRoute(info.id)}>Delete</Button>}
            </ButtonGroup>
        </Card.Body>
    </Card>
}