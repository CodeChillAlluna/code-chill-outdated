import * as React from "react";
import AuthService from "./AuthService";
import withAuth from "./components/withAuth";

class App extends React.Component<any, any> {
    Auth: AuthService;

    constructor(props?: any, context?: any) {
        super(props, context);
        this.Auth = new AuthService();
    }

    render() {
        return (
            <div>
                Welcome {this.props.user.username}
            </div>
        );
    }

}
export default withAuth(App);
