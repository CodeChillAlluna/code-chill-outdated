import * as React from "react";
import * as R from "../Routes";
import { Route, Switch, BrowserRouter } from "react-router-dom";
import App from "../App";
import CodeChillXterm from "./CodeChillXterm";
import UserConnection from "./user/UserConnection";
import UserSignUp from "./user/UserSignUp";
import UserProfile from "./user/UserProfile";
import NotFound from "./NotFound";
import UserForgotPassword from "./user/UserForgotPassword";
import UserResetPassword from "./user/UserResetPassword";
const config = require("../../package.json");

export default class CodeChillRouter extends React.Component<any, any> {

    render() {
        return (
            <main>
                <BrowserRouter basename={config.homepage}>
                    <Switch>

                        <Route
                            exact={true}
                            path={R.HOME}
                            component={App}
                        />
                        <Route
                            exact={true}
                            path={R.PROFILE}
                            component={UserProfile}
                        />
                        <Route
                            exact={true}
                            path={R.FORGOT_PASSWORD}
                            component={UserForgotPassword}
                        />
                        <Route
                            exact={true}
                            path={R.RESET_PASSWORD}
                            component={UserResetPassword}
                        />
                        <Route
                            exact={true}
                            path={R.LOGIN}
                            component={UserConnection}
                        />
                        <Route
                            exact={true}
                            path={R.SIGNUP}
                            // render={(props) => <UserConnection props={...props} />}
                            component={UserSignUp}
                        />
                        <Route
                            exact={true}
                            path={R.TERMINAL}
                            component={CodeChillXterm}
                        />
                        <Route
                            path={R.NOTFOUND}
                            component={NotFound}
                        />
                    </Switch>
                </BrowserRouter>
            </main>
        );
    }

}