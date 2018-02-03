import * as React from "react";
import Terminal from "react-bash";

export default class App extends React.Component<any, any> {
    showMsg = () => "Hello World";
    render() {
        return (
            <div 
                style={{
                    height: "250px"
                }}
            >
                <Terminal
                    prefix="code@chill" 
                    theme={Terminal.Themes.DARK}    
                />
            </div>
        );
    }
}
