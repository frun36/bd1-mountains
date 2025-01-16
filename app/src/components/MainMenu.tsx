import { Link, useNavigate } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import icon from '/icon.svg';
import ButtonGroup from 'react-bootstrap/ButtonGroup';

export default function MainMenu() {
    const navigate = useNavigate();

    return <div className="w-25 mx-auto p-2" style={{ minWidth: "500px" }}>
        <div className="w-75 mx-auto">
            <img src={icon} alt="Icon" className="w-100" />
            <Card className="text-center m-1">
                <Card.Body>
                    <Card.Title>Browse</Card.Title>
                    <Card.Text>See what other users are up to.</Card.Text>
                    <ButtonGroup className="w-100">
                        <Button variant="primary" onClick={() => navigate('/leaderboard')}>
                            Leaderboard
                        </Button>
                        <Button variant="primary" onClick={() => navigate('/route_explorer')}>
                            Route explorer
                        </Button>
                    </ButtonGroup>
                </Card.Body>
            </Card>
            <Card className="text-center m-1">
                <Card.Body>
                    <Card.Title>Account</Card.Title>
                    <Card.Text>View and modify your routes.</Card.Text>
                    <Link to="/login">
                        <Button variant="success" className="w-100">
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
                        <Button variant="warning" className="w-100">
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
                        <Button variant="danger" className="w-100">
                            Reset DB
                        </Button>
                    </Link>
                </Card.Body>
            </Card>
        </div>
    </div>
}