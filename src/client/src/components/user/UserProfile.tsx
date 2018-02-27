import * as React from "react";
import { Button, Form, Message } from "semantic-ui-react";
import AuthService from "../../AuthService";
import withAuth from "../withAuth";

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
                            content="Your email have been successfully updated !"
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
        );
    }

}
export default withAuth(UserProfile);
