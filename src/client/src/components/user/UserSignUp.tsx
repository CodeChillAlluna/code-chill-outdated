import * as React from "react";
import NavBar from "../NavBar";
import AuthService from "../../AuthService";
import { Button, Form, Grid, Header, Image, Message, Segment } from "semantic-ui-react";
import { Link } from "react-router-dom";
import { formatRoute } from "react-router-named-routes";
import { HOME, LOGIN } from "../../Routes";
const logo = require("../../resources/logocodeandchill.png");

export default class UserSignUp extends React.Component<any, any> {

    Auth: AuthService;

    constructor(props?: any, context?: any) {
        super(props, context);
        this.Auth = new AuthService();
        this.handleChange = this.handleChange.bind(this);
        this.handleFormSubmit = this.handleFormSubmit.bind(this);
        this.state = {
            "user": {
                "enabled": true,
                "lastPasswordResetDate": new Date().getTime()
            },
            "message": false
        };
    }

    componentWillMount() {
        if (this.Auth.loggedIn()) {
            this.props.history.replace(formatRoute(HOME));
        }
    }

    render() {
        if (this.state.message) {
            return(
                <NavBar history={this.props.history} >
                    {this.state.message}
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
                            {" "}Register your account
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
                                        icon="user"
                                        iconPosition="left"
                                        placeholder="Username"
                                        name="username"
                                        onChange={this.handleChange}
                                    />
                                    <Form.Input
                                        required={true}
                                        fluid={true}
                                        icon="envelope"
                                        iconPosition="left"
                                        placeholder="Email"
                                        name="email"
                                        onChange={this.handleChange}
                                    />
                                    <Form.Input
                                        required={true}
                                        fluid={true}
                                        icon="user"
                                        iconPosition="left"
                                        placeholder="First name"
                                        name="firstname"
                                        onChange={this.handleChange}
                                    />
                                    <Form.Input
                                        required={true}
                                        fluid={true}
                                        icon="user"
                                        iconPosition="left"
                                        placeholder="Last name"
                                        name="lastname"
                                        onChange={this.handleChange}
                                    />
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

                                    <Button
                                        color="teal"
                                        fluid={true}
                                        size="large"
                                        type="submit"
                                    >
                                        Sign up
                                    </Button>
                                </Segment>
                            </Form>
                            <Message>
                                <p>Already have an account? <Link to={formatRoute(LOGIN)}>Login</Link></p>
                            </Message>
                        </Grid.Column>
                    </Grid>
                </NavBar>
            );
        }
    }

    private handleChange(e: any) {
        const newState = Object.assign({}, this.state);
        newState.user[e.target.name] = e.target.value;
        this.setState(newState);
    }

    private handleFormSubmit(e: any) {
        e.preventDefault();
        const user = this.state.user;
        this.Auth.createAccount(user).then((res) => {
            this.setState(
                {
                    "message": (
                        <Message positive={true}>
                            <Message.Header>Your user registration was successful</Message.Header>
                            <p>You may now <Link to={formatRoute(LOGIN)}>log-in</Link>
                             with the username you have chosen</p>
                        </Message>
                    )
                }
            );
        });
    }
}