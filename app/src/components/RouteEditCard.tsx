import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import { Link } from "react-router-dom";


interface Props {
    id: number;
    name: string;
    timeModified: string;
}

export default function RouteEditCard({id, name, timeModified}: Props) {
    return <Card>
        <Card.Title>{name}</Card.Title>
        <Card.Subtitle>{timeModified}</Card.Subtitle>
        <Link to={`/routes/edit/${id}`}><Button variant="warning">Edit</Button></Link>
    </Card>
}