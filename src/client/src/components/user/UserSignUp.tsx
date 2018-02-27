import * as React from "react";
import NavBar from "./NavBar";
import AuthService from "../../AuthService";

export default class UserSignUp extends React.Component<any, any> {

    Auth: AuthService;

    constructor(props?: any, context?: any) {
        super(props, context);
        this.Auth = new AuthService();
        this.state = {};
    }

    componentWillMount() {
        if (this.Auth.loggedIn()) {
            this.props.history.replace("/");
        }
    }

    render() {
        return(

        );
    }
}