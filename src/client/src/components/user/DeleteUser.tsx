import * as React from "react";
import { Button, Confirm } from "semantic-ui-react";
import AuthService from "../../AuthService";
import withAuth from "../withAuth";

class DeleteUser extends React.Component<any, any> {
    Auth: AuthService;
    constructor(props: any) {
        super(props);
        this.deleteUser = this.deleteUser.bind(this);
        this.show = this.show.bind(this);
        this.close = this.close.bind(this);
        this.Auth = new AuthService();
        this.state = { open: false };
    }

    render() {
        return (
            <div>
                <Button
                    color="teal"
                    fluid={true}
                    size="medium"
                    onClick={this.show}
                >
                Delete my account
                </Button>
                <Confirm
                    open={this.state.open}
                    onCancel={this.close}
                    onConfirm={this.deleteUser}
                />
            </div>
        );
    }

    private show() {
        this.setState({
            "open": true
        });
    }

    private close() {
        this.setState({
            "open": false
        });
    }

    private deleteUser(event: any) {
        this.close();
        this.Auth.deleteUser().then((res) => {
            this.Auth.logout();
            this.props.history.replace("/");
        });
    }

}
export default withAuth(DeleteUser);