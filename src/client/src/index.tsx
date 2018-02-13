import * as React from "react";
import * as ReactDOM from "react-dom";
import { Route, BrowserRouter as Router, Switch } from "react-router-dom";
import App from "./App";
import registerServiceWorker from "./registerServiceWorker";
import "./index.css";
import Term, { Terminal } from "./components/Term";
import NotFound from "./components/NotFound";

ReactDOM.render(
  <Router>
    <Switch>
      <Route exact="true" path="/" component={App} />
      <Route
          path="/term" 
          render={(props) => <Term prefix="code@chill" theme={Terminal.Themes.WHITE} />} 
      />
      <Route component={NotFound} />
    </Switch>
  </Router>
, document.getElementById("root") as HTMLElement);
registerServiceWorker();
