import * as React from "react";
import Terminal from "react-bash";

export interface TermProps {
    extensions?: object;
    history?: Array<Terminal.history>;
    prefix?: string;
    structure?: object;
    style?: object;
    theme?: string;
}

export default class Term extends React.Component<TermProps, any> {

    terminal = Terminal;

    constructor(props?: any, context?: any) {
        super(props, context);
    }

    render() {
        return (
            <div 
                style={{
                    height: "250px"
                }}
            >
                <Terminal
                    extensions={this.props.extensions}
                    history={this.props.history}
                    prefix={this.props.prefix} 
                    structure={this.props.structure}
                    style={this.props.style}
                    theme={this.props.theme}    
                />
            </div>
        );
    }
}
export { Terminal };
