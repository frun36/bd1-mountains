import { Link } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import icon from '/icon.svg';

export default function MainMenu() {
    return <div className="w-25 mx-auto p-2">
        <div className="w-75 mx-auto">
            <img src={icon} alt="Icon" className="w-100" />
        </div>
        <Card className="text-center m-1">
            <Card.Body>
                <Card.Title>Browse</Card.Title>
                <Card.Text>See what other users are up to.</Card.Text>
                <Link to="/leaderboard">
                    <Button variant="primary">
                        Leaderboard
                    </Button>
                </Link>
            </Card.Body>
        </Card>
        <Card className="text-center m-1">
            <Card.Body>
                <Card.Title>Account</Card.Title>
                <Card.Text>View and modify your routes.</Card.Text>
                <Link to="/login">
                    <Button variant="success">
                        Login/register
                    </Button>
                </Link>
            </Card.Body>
        </Card>
        <Card className="text-center m-1">
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
        <Card className="text-center m-1">
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