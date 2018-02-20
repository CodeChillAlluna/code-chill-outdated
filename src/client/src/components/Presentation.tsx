import * as React from "react";

export default class Presentation extends React.Component<any, any> {
    
    constructor(props?: any, context?: any) {
        super(props, context);
        this.state = {};
    }

    render() {
        return (
            <div className="presentation">
                <h3>Presentation page</h3>
            </div>
        );
    }
}