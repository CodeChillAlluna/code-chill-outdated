import * as React from "react";
import { Button, Form, Message, Grid } from "semantic-ui-react";
import AuthService from "../../AuthService";
import withAuth from "../withAuth";
import DeleteUser from "./DeleteUser";
import NavBar from "../NavBar";

class UserProfile extends React.Component<any, any> {
    Auth: AuthService;
    userUpdate: Object;
    constructor(props: any) {
        super(props);
        this.editUser = this.editUser.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.state = {
            "firstname": this.props.user.firstname,
            "lastname": this.props.user.lastname,
            "email": this.props.user.email,
            "message": ""
        };
        this.Auth = new AuthService();
    }

    editUser(event: any) {
        let userUpdate = this.props.user;
        userUpdate.firstname = this.state.firstname;
        userUpdate.lastname = this.state.lastname;
        userUpdate.email = this.state.email;
        this.Auth.editUser(userUpdate).then((res) => {
            this.setState(
                {
                    "message": (
                        <Message
                            success={true}
                            content="Your informations have been successfully updated !"
                        />
                    ),
                }
            );
        });
    }

    handleChange(e: any) {
        this.setState(
            {
                [e.target.name]: e.target.value
            }

        );
    }

    render() {
        return (
            <NavBar history={this.props.history} user={this.props.user}>
                <Grid>
                    <Grid.Column width={12}>
                        <Form
                            success={true}
                            error={true}
                            size="tiny"
                        >
                            <Form.Input
                                value={this.state.lastname}
                                required={true}
                                placeholder="lastname"
                                name="lastname"
                                onChange={this.handleChange}
                            />
                            <Form.Input
                                value={this.state.firstname}
                                required={true}
                                placeholder="firstname"
                                name="firstname"
                                onChange={this.handleChange}
                            />
                            <Form.Input
                                value={this.state.email}
                                required={true}
                                placeholder="email"
                                name="email"
                                onChange={this.handleChange}
                            />
                            <Button
                                color="teal"
                                fluid={true}
                                size="large"
                                type="submit"
                                onClick={this.editUser}
                            >
                                Valider
                            </Button>
                            {this.state.message}
                        </Form>
                    </Grid.Column>
                    <Grid.Column width={4}>
                        <DeleteUser
                            history={this.props.history}
                            user={this.props.user}
                        />
                    </Grid.Column>
                </Grid>
            </NavBar>
        );
    }

}
export default withAuth(UserProfile);
