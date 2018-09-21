import * as React from "react";
import { Terminal } from "xterm";
import * as attach from "xterm/lib/addons/attach/attach";
import * as fit from "xterm/lib/addons/fit/fit";
import * as ClassName from "classnames";
import AuthService from "../AuthService";
import withAuth from "./withAuth";
const config = require("../../package.json");

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
        this.msg = "";
        this.Auth = new AuthService();
        this.xterm = new Terminal();
        Terminal.applyAddon(attach);
        Terminal.applyAddon(fit);
        this.webSocket = new WebSocket(`${config.dockerApi}/containers/${this.props.user.dockers[0].name}/
attach/ws?logs=0&stream=1&stdin=1&stdout=1&stderr=1`);
        this.Auth.startDocker(this.props.user.dockers[0].name).then((res) => {
            console.log(res);
        });
    }

    componentDidMount() {
        let xt = this;
        this.xterm.open(this.refs.container);
        (this.xterm as any).fit();
        this.webSocket.addEventListener("open", function () {
            (xt.xterm as any).attach(xt.webSocket, xt.webSocket, false, false);
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
        const css = `.ui.container{
            width: 100%!important;
            margin-left: 0!important;
            margin-right: 0!important;
         }`;
        return (
            <div>
                <div ref={container} className={terminalClassName} />
                <style>{css}</style>
            </div>
        );
    }
}

export default withAuth(CodeChillXterm);
export {Terminal};
