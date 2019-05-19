import React from 'react';
import { Container , Row, Col } from 'react-bootstrap';

export default class GridComponent extends React.Component {
    constructor(props) {
        super(props);

    }
    render() {
        return (
            <Container>
                <Row>
                    <Col sm={8}>sm=8</Col>
                    <Col sm={4}>sm=4</Col>
                </Row>
            </Container>
        );
    }
}