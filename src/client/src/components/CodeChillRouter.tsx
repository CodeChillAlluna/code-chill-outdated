import * as React from "react";
// import { BrowserRouter as Router, Route } from "react-router-dom";
import { Route, Switch } from "react-router-dom";
import App from "../App";
import CodeChillXterm from "./CodeChillXterm";
import Term, { Terminal } from "./Term";
import UserConnection from "./user/UserConnection";
import UserSignUp from "./user/UserSignUp";
import Presentation from "./Presentation";
import NotFound from "./NotFound";
import withAuth from "./withAuth";

export default class CodeChillRouter extends React.Component<any, any> {

    Term = withAuth(Term);
    
    render() {
        const ccxterm = "CodeChillXterm";
        const url = "ws://localhost:2375/containers/code-chill/attach/ws?logs=0&stream=1&stdin=0&stdout=0&stderr=0";
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
                        exact={true}
                        path="/signup" 
                        // render={(props) => <UserConnection props={...props} />}
                        component={UserSignUp} 
                    />
                    <Route
                        exact={true}
                        path="/xtermtest"
                        render={(props) => 
                            <CodeChillXterm 
                                ref={ccxterm} 
                                style={{overflow: "hidden", position: "relative", width: "100%", height: "100%"}}
                                url={url}
                            />}
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