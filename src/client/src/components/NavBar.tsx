import * as React from "react";
import * as _ from "lodash";
import AuthService from "../AuthService";
import {
    Container,
    Dropdown,
    Icon,
    Image,
    Menu,
    Sidebar,
    Responsive
  } from "semantic-ui-react";
  
const NavBarChildren = ({ children }) => (
    <Container style={{ marginTop: "5em" }}>{children}</Container>
);
  
export default class NavBar extends React.Component<any, any> {

    Auth: AuthService;

    leftItems = [
        { as: "a", content: "Terminal", key: "terminal", href: "/term" }
    ];

    constructor(props?: any, context?: any) {
        super(props, context);
        this.Auth = new AuthService();
        this.handleLogout = this.handleLogout.bind(this);
        this.handlePusher = this.handlePusher.bind(this);
        this.handleToggle = this.handleToggle.bind(this);
        this.state = {
            visible: false
        };
    }
  
    render() {
        const { children } = this.props;
        let rmenu: any;
        if (this.props.user !== null) {
            rmenu = (
                <Dropdown item={true} text={this.props.user.username}>
                    <Dropdown.Menu>
                        <Dropdown.Item><a href="/profile">Profile</a></Dropdown.Item>
                        <Dropdown.Item
                            onClick={this.handleLogout}
                        >Log out
                        </Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
            );
        } else {
            rmenu = (
                <Menu.Item>
                    <a href="/login">Login</a>
                </Menu.Item>
            );
        }
        return (
            <div>
                <Responsive {...Responsive.onlyMobile}>
                    <Sidebar.Pushable>
                        <Sidebar
                            as={Menu}
                            animation="overlay"
                            icon="labeled"
                            inverted={true}
                            items={this.leftItems}
                            vertical={true}
                            visible={this.state.visible}
                        />
                        <Sidebar.Pusher
                            dimmed={this.state.visible}
                            onClick={this.handlePusher}
                            style={{ minHeight: "100vh" }}
                        >
                            <Menu fixed="top" inverted={true}>
                                <Menu.Item>
                                    <Image size="mini" src="https://react.semantic-ui.com/logo.png" />
                                </Menu.Item>
                                <Menu.Item onClick={this.handleToggle}>
                                    <Icon name="sidebar" />
                                </Menu.Item>
                                <Menu.Menu position="right">
                                    {rmenu}
                                </Menu.Menu>
                            </Menu>
                            <NavBarChildren>{children}</NavBarChildren>
                        </Sidebar.Pusher>
                    </Sidebar.Pushable>
                </Responsive>
                <Responsive minWidth={Responsive.onlyTablet.minWidth}>
                        <Menu fixed="top" inverted={true}>
                            <Menu.Item>
                                <Image size="mini" src="https://react.semantic-ui.com/logo.png" />
                            </Menu.Item>
                            {_.map(this.leftItems, (item) => <Menu.Item {...item} />)}
                            <Menu.Menu position="right">
                                {rmenu}
                            </Menu.Menu>
                        </Menu>
                        <NavBarChildren>{children}</NavBarChildren>
                </Responsive>
            </div>
        );
    }

    private handleLogout() {
        this.Auth.logout();
        this.props.history.replace("/");
    }

    private handlePusher() {
        const { visible } = this.state;
  
        if (visible) {
            this.setState(
                { visible: false }
            );
        }
    }
  
    private handleToggle() {
        this.setState({ visible: !this.state.visible });
    }

}
