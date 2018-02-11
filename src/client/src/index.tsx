import * as React from "react";
import * as ReactDOM from "react-dom";
import { BrowserRouter as Router, Route } from "react-router-dom";
import App from "./App";
import registerServiceWorker from "./registerServiceWorker";
import "./index.css";
import Term, { Terminal } from "./components/Term";

ReactDOM.render(
  <Router>
    <div>
      <Route exact="true" path="/" component={App} />
      <Route 
        exact="true" 
        path="/term" 
        render={(props) => <Term prefix="code@chill" theme={Terminal.Themes.DARK} />} 
      />
    </div>
  </Router>,
  document.getElementById("root") as HTMLElement
);
registerServiceWorker();
