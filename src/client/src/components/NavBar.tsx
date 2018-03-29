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
import { Link } from "react-router-dom";
import { formatRoute } from "react-router-named-routes";
import { TERMINAL, HOME, LOGIN, PROFILE } from "../Routes";
const logo = require("../resources/logocodeandchill.png");

const NavBarChildren = ({ children }) => (
    <Container style={{ marginTop: "5em" }}>{children}</Container>
);

export default class NavBar extends React.Component<any, any> {

    Auth: AuthService;

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
        let leftItems: any;
        if (this.props.user) {
            rmenu = (
                <Dropdown item={true} text={this.props.user.username}>
                    <Dropdown.Menu>
                        <Dropdown.Item><Link to={formatRoute(PROFILE)}>Profile</Link></Dropdown.Item>
                        <Dropdown.Item
                            onClick={this.handleLogout}
                        >Log out
                        </Dropdown.Item>
                    </Dropdown.Menu>
                </Dropdown>
            );
            leftItems = [
                { as: Link, content: "Terminal", key: "terminal", to: formatRoute(TERMINAL) }
            ];
        } else {
            rmenu = (
                <Menu.Item>
                    <Link to={formatRoute(LOGIN)}>Login</Link>
                </Menu.Item>
            );
            leftItems = [];
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
                            items={leftItems}
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
                                    <Link to={formatRoute(HOME)}>
                                        <Image size="mini" src={logo} />
                                    </Link>
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
                            <Link to={formatRoute(HOME)}>
                                <Image size="mini" src={logo} />
                            </Link>
                            </Menu.Item>
                            {_.map(leftItems, (item) => <Menu.Item {...item} />)}
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
