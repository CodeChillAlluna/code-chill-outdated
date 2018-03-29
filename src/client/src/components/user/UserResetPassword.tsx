import * as React from "react";
import NavBar from "../NavBar";
import AuthService from "../../AuthService";
import { Button, Form, Grid, Header, Image, Message, Segment } from "semantic-ui-react";
import { formatRoute } from "react-router-named-routes";
import { HOME } from "../../Routes";
const logo = require("../../resources/logocodeandchill.png");

export default class UserResetPassword extends React.Component<any, any> {

    Auth: AuthService;

    constructor(props?: any, context?: any) {
        super(props, context);
        this.Auth = new AuthService();
        this.handleChange = this.handleChange.bind(this);
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
        this.displayErrors = this.displayErrors.bind(this);
        this.state = {
            "token": true,
            "message": {
                "state" : "",
                "content": ""
            }
        };
    }

    componentWillMount() {
        if (this.Auth.loggedIn()) {
            this.props.history.replace(formatRoute(HOME));
        }
        this.Auth.checkTokenPassword(this.props.match.params.token).then((res) => {
            this.setState(
                {
                    "token": true
                }
            );
        }).catch((err) => {
            this.setState(
                {
                    "token": false
                }
            );
        });
    }

    render() {
        if (!this.state.token) {
            return (
                <NavBar history={this.props.history} >
                    <Message negative={true}>
                        <Message.Header>Request failed</Message.Header>
                            <p>Your token has expired</p>
                    </Message>
                </NavBar>
            );
        } else if (this.state.message.state === "success") {
            return(
                <NavBar history={this.props.history} >
                    {this.state.message.content}
                </NavBar>
            );
        } else {
            return(
                <NavBar history={this.props.history} >
                    <Grid
                        textAlign="center"
                        style={{ height: "100%" }}
                        verticalAlign="middle"
                    >
                        <Grid.Column style={{ maxWidth: 450 }}>
                            <Header as="h2" color="teal" textAlign="center">
                                <Image src={logo} />
                            {" "}Reset your password
                            </Header>
                            <Form
                                error={true}
                                success={true}
                                size="large"
                                onSubmit={this.handleFormSubmit}
                            >
                                <Segment stacked={true}>
                                    <Form.Input
                                        required={true}
                                        fluid={true}
                                        icon="lock"
                                        iconPosition="left"
                                        placeholder="Password"
                                        type="password"
                                        name="password"
                                        onChange={this.handleChange}
                                    />
                                    <Form.Input
                                        success="true"
                                        required={true}
                                        fluid={true}
                                        icon="lock"
                                        iconPosition="left"
                                        placeholder="Confirm password"
                                        type="password"
                                        name="password2"
                                        onChange={this.handleChange}
                                    />
                                    {this.displayErrors}
                                    <Button
                                        color="teal"
                                        fluid={true}
                                        size="large"
                                        type="submit"
                                    >
                                        Submit
                                    </Button>
                                </Segment>
                            </Form>
                        </Grid.Column>
                    </Grid>
                </NavBar>
            );
        }
    }

    private handleChange(e: any) {
        this.setState(
            {
                [e.target.name]: e.target.value
            }
        );
    }

    private displayErrors() {
        if (this.state.message.state === "error") {
            return this.state.message.content;
        }
        return null;
    }

    private setMessage(state: string, content: string) {
        if (state === "success") {
            this.setState(
                {
                    "message": {
                        "state": state,
                        "content": (
                            <Message positive={true}>
                            <Message.Header>Password successfuly changed</Message.Header>
                                <p>{content}</p>
                            </Message>
                        )
                    }
                }
            );
        } else {
            this.setState(
                {
                    "message": {
                        "state": state,
                        "content": (
                            <Message negative={true}>
                            <Message.Header>Request failed</Message.Header>
                                <p>{content}</p>
                            </Message>
                        )
                    }
                }
            );
        }
    }

    private handleFormSubmit(e: any) {
        e.preventDefault();
        this.Auth.resetPassword(this.props.match.params.token, this.state.password).then((res) => {
            this.setMessage("success", `You can now <Link to="${formatRoute(HOME)}">log-in</Link> to your account`);
        }).catch((err) => {
            this.setMessage("error", err);
        });
    }

}