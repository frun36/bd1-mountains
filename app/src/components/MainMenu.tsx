import { Link } from 'react-router-dom';
import { Container, Row, Col, Button, Card } from 'react-bootstrap';

export default function MainMenu() {
    return <Container className="mt-5">
        <Row className="justify-content-center">
            <Col xs={12} md={6} lg={4} className="mb-4">
                <Card className="text-center">
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
            </Col>

            <Col xs={12} md={6} lg={4} className="mb-4">
                <Card className="text-center">
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
            </Col>
        </Row>
    </Container>
}