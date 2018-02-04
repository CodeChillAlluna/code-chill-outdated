import TextEncoding from "text-encoding";

export default class DockerTerm {
    private socket: WebSocket;

    constructor(url: string) {
        this.socket = new WebSocket(url);
        this.socket.addEventListener("message", this.receiveData);
    }

    sendData (data: string): void {
        if (this.socket.readyState === 1) {
            this.socket.send(data);
        }
    }

    receiveData (event: MessageEvent) {
        let str: string = "";
        let decoder = new TextEncoding.TextDecoder();
        if (typeof event.data === "object" && event.data instanceof ArrayBuffer) {
            str = decoder.decode(event.data);
        } else {
            str = "Error receiving data.";
        }
        return str;
    }
}