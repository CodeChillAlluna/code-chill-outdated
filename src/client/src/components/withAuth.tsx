import * as React from "react";
import AuthService from "../AuthService";
import NavBar from "./NavBar";

export default function withAuth(AuthComponent: any) {
    const Auth = new AuthService();
    return class AuthWrapped extends React.Component<any, any> {
        constructor(props?: any, context?: any) {
            super(props, context);
            this.state = {
                user: null
            };
        }
        componentWillMount() {
            if (!Auth.loggedIn()) {
                this.props.history.replace("/login");
            } else {
                try {
                    const profile = Auth.getProfile();
                    this.setState({
                        user: profile
                    });
                } catch (err) {
                    Auth.logout();
                    this.props.history.replace("/login");
                }
            }
        }
        render() {
            if (this.state.user) {
                return (
                    <NavBar history={this.props.history} user={this.state.user}>
                        <AuthComponent history={this.props.history} user={this.state.user} />
                    </NavBar>
                );
            } else {
                return null;
            }                
        }
    };
}
