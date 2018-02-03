import * as React from "react";
import Term, { Terminal } from "./components/Term";

export default class App extends React.Component<any, any> {
    render() {
        return (
            <div>
                <Term
                    prefix="code@chill" 
                    theme={Terminal.Themes.DARK}    
                />
            </div>
        );
    }
}
