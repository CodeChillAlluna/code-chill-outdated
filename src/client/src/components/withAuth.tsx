import * as React from "react";
import AuthService from "../AuthService";
import NavBar from "./NavBar";
import { formatRoute } from "react-router-named-routes";
import { LOGIN } from "../Routes";

export default function withAuth(AuthComponent: any) {
    const Auth = new AuthService();
    return class AuthWrapped extends React.Component<any, any> {
        constructor(props?: any, context?: any) {
            super(props, context);
            this.state = {
                token: null,
                user: null
            };
        }
        componentWillMount() {
            if (!Auth.loggedIn()) {
                this.props.history.replace(formatRoute(LOGIN));
            } else {
                try {
                    const profile = Auth.getProfile();
                    Auth.getUserInfos().then((res) => {
                        this.setState({
                            token: profile,
                            user: res
                        });
                    });
                } catch (err) {
                    Auth.logout();
                    this.props.history.replace(formatRoute(LOGIN));
                }
            }
        }
        render() {
            if (this.state.token) {
                return (
                    <NavBar history={this.props.history} token={this.state.token} user={this.state.user}>
                        <AuthComponent history={this.props.history} token={this.state.token} user={this.state.user} />
                    </NavBar>
                );
            } else {
                return null;
            }
        }
    };
}
