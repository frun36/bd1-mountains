import { Link } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

export default function MainMenu() {
    return <div>
        <Card className="text-center">
            <Card.Body>
                <Card.Title>Account</Card.Title>
                <Card.Text>View and modify your routes.</Card.Text>
                <Link to="/login">
                    <Button variant="success">
                        Login
                    </Button>
                </Link>
            </Card.Body>
        </Card>

        <Card className="text-center">
            <Card.Body>
                <Card.Title>Raw operations</Card.Title>
                <Card.Text>
                    Perform CRUD database operations directly.
                </Card.Text>
                <Link to="/raw">
                    <Button variant="warning">
                        Raw operations
                    </Button>
                </Link>
            </Card.Body>
        </Card>


        <Card className="text-center">
            <Card.Body>
                <Card.Title>Reset DB</Card.Title>
                <Card.Text>
                    Reset the database to its initial (demo) state.
                </Card.Text>
                <Link to="/reset">
                    <Button variant="danger">
                        Reset DB
                    </Button>
                </Link>
            </Card.Body>
        </Card>
    </div>


}