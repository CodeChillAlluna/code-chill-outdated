import * as React from "react";
import * as Terminal from "xterm/build/xterm";
import * as attach from "xterm/build/addons/attach/attach";
import * as ClassName from "classnames";
import AuthService from "../AuthService";
import withAuth from "./withAuth";

export interface IxTermProps extends React.DOMAttributes<{}> {
    onChange?: (e: any) => void;
    onInput?: (e: any) => void;
    onFocusChange?: Function;
    addons?: string[];
    onScroll?: (e: any) => void;
    onContextMenu?: (e: any) => void;
    options?: any;
    path?: string;
    value?: string;
    className?: string;
    style?: React.CSSProperties;
    // url: string;
    history?: any;
    token?: any;
    user?: any;
    
}

export interface IxTermState {
    isFocused: boolean;
}

class CodeChillXterm extends React.Component<IxTermProps, IxTermState> {
    xterm: Terminal;
    Auth: AuthService;
    refs: {
        [s: string]: any;
        container: HTMLDivElement;
    };
    webSocket: WebSocket;
    msg: string;

    constructor(props: IxTermProps, context?: any) {
        super(props, context);
        this.state = {
            isFocused: false
        };
        // this.props.options
        this.msg = "";
        this.Auth = new AuthService();
        this.xterm = new Terminal();
    }

    componentDidMount() {
        var xt = this;
        Terminal.applyAddon(attach);
        this.webSocket = new WebSocket(`ws://localhost:2375/containers/${this.props.user.dockers[0].name}/
attach/ws?logs=0&stream=1&stdin=1&stdout=1&stderr=1`);
        this.Auth.startDocker(this.props.user.dockers[0].name).then((res) => {
            console.log(res);
        });
        this.xterm.open(this.refs.container);
        this.webSocket.addEventListener("open", function () {
            (xt.xterm as any).attach(xt.webSocket, xt.webSocket, false, false);
        });

        if (this.props.onContextMenu) {
            this.xterm.element.addEventListener("contextmenu", this.onContextMenu.bind(this));
        }

        if (this.props.onInput) {
            this.xterm.on("data", this.onInput);
        }

        if (this.props.value) {
            this.xterm.write(this.props.value);
        }

        this.getTerminal().on("key", function(key: string, e: KeyboardEvent) {
            // e: KeyboardEvent; e.key: string; e.which: number
            if (e.key === "Backspace") {
                xt.msg = xt.msg.substring(0, xt.msg.length - 1);
                xt.xterm.write("\b \b");
            } else if (e.key === "Enter") {
                xt.msg = xt.msg + key;
                xt.webSocket.send(xt.msg);   
                lastCommand = xt.msg;  
                xt.msg = "";
                xt.xterm.write("\r\n");
            } else if (e.key === "Escape" || e.key === "ArrowUp" || e.key === "ArrowDown") {
                xt.webSocket.send(key);
            } else {
                xt.msg = xt.msg + key;
                xt.xterm.write(key);
            }
            // else if (e.ctrlKey === true) {
                // xt.webSocket.send(key ou xt.msg + key)
                // a voir comment faire !
                /**
                 * TODO CTRL+KEY HANDLER (block ctrl+w) ctrl+a ctrl+c etc.
                 */
            // } else if (e.altKey === true) {
                /**
                 * TODO ALT+KEY HANDLER
                 */
            // } else if (e.shiftKey === true) {
                /**
                 *  TODO SHIFT+KEY HANDLER
                 */
            // } else if (e.key === "Tab") {
                /**
                 *  TODO TAB HANDLER
                 */
        });
    }

    componentWillUnmount() {
        if (this.xterm) {
            this.xterm.destroy();
            delete this.xterm;
        }
        this.Auth.stopDocker(this.props.user.dockers[0].name).then((res) => {
            console.log(res);
        });
    }

    getTerminal() {
        return this.xterm;
    }
    render() {
        const terminalClassName = ClassName(
            "CodeChillXterm",
            this.state.isFocused ? "CodeChillXterm--focused" : null,
            this.props.className
        );
        const container = "container";
        return (
            <div ref={container} className={terminalClassName} />
        );
    }
}

export default withAuth(CodeChillXterm);
export {Terminal};
