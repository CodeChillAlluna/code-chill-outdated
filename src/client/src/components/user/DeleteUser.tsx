import * as React from "react";
/*import axios from "axios";*/
import { Button, Form } from "semantic-ui-react";
import AuthService from "../../AuthService";
import withAuth from "../withAuth";

class DeleteUser extends React.Component<any, any> {
    token: String =
    `eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuYXRob3UiLCJhdWQiOiJ3ZWIiLCJleHAiOjE1MjAyNjYyMzksImlhdCI6MTUxOTY2MTQzOX0
    .JZ89AmCqUP3DNszYLvBnugUi80kRiLGqskwsW5K_sbk3OvOsaIa9GKZ7cwIAjKqreKD7yRLUx8CiaXaa-q6fXg`;
    Auth: AuthService;
    constructor(props: any) {
        super(props);
        this.deleteUser = this.deleteUser.bind(this);
        this.Auth = new AuthService();
    }

    deleteUser(event: any) {
        
        /*axios.delete(`http://localhost:8080/user/4`, {headers : {"Authorization" : "Bearer ${token}"}});*/
        this.Auth.deleteUser(4).then((res) => {
            console.log(res);
        });
    }

    render() {
        return (
            <Form>
                <Button 
                    color="teal"
                    fluid={true}
                    size="medium"
                    type="submit"
                    onClick={this.deleteUser}
                >
                Suppression
                </Button>
            </Form>
        );
    }

}
export default withAuth(DeleteUser);