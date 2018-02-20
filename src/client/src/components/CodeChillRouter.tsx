import * as React from "react";
// import { BrowserRouter as Router, Route } from "react-router-dom";
import { Route, Switch } from "react-router-dom";
import App from "../App";
import Term, { Terminal } from "./Term";
import UserConnection from "./user/UserConnection";
import Presentation from "./Presentation";
import NotFound from "./NotFound";
import withAuth from "./withAuth";

export default class CodeChillRouter extends React.Component<any, any> {

    Term = withAuth(Term);

    render() {
        return (
            <main>
                <Switch>
                    <Route 
                        exact={true} 
                        path="/" 
                        component={Presentation}
                    />
                    <Route 
                        exact={true} 
                        path="/home"
                        component={App}
                    />
                    <Route 
                        exact={true} 
                        path="/term" 
                        render={(props) => <Term prefix="code@chill" theme={Terminal.Themes.DARK} />} 
                    />
                    <Route 
                        exact={true}
                        path="/login" 
                        // render={(props) => <UserConnection props={...props} />}
                        component={UserConnection} 
                    />
                    <Route 
                        path="*"
                        component={NotFound}
                    />
                </Switch>
            </main>
        );
    }

}