import decode from "jwt-decode";
const config = require("../package.json");

export default class AuthService {
    // Initializing important variables

    user: Object;
    domain: string;

    constructor(domain?: string) {
        this.domain = domain || config.restApi; // API server domain
        this.user = Object;
        this.fetch = this.fetch.bind(this); // React binding stuff
        this.login = this.login.bind(this);
        this.getProfile = this.getProfile.bind(this);
        this.getUserInfos = this.getUserInfos.bind(this);
    }

    login(username: string, password: string) {
        // Get a token from api server using the fetch api
        return this.fetch(`${this.domain}/auth`, {
            method: "POST",
            body: JSON.stringify({
                username,
                password
            })
        }).then((res) => {
            this.setToken(res.token); // Setting the token in localStorage
            return Promise.resolve(res);
        });
    }

    loggedIn() {
        // Checks if there is a saved token and it"s still valid
        const token = this.getToken(); // GEtting token from localstorage
        return !!token && !this.isTokenExpired(token); // handwaiving here
    }

    isTokenExpired(token: any) {
        try {
            const decoded = decode(token);
            if (decoded.exp < Date.now() / 1000) { // Checking if token is expired. N
                return true;
            } else {
                return false;
            }
        } catch (err) {
            return false;
        }
    }

    setToken(idToken: any) {
        // Saves user token to localStorage
        localStorage.setItem("id_token", idToken);
    }

    getToken() {
        // Retrieves the user token from localStorage
        return localStorage.getItem("id_token");
    }

    logout() {
        // Clear user token and profile data from localStorage
        localStorage.removeItem("id_token");
    }

    getProfile() {
        // Using jwt-decode npm package to decode the token
        return decode(this.getToken());
    }

    getUserInfos () {
        return this.fetch(`${this.domain}/user`, {
            method: "GET",
         }).then((res) => {
           return Promise.resolve(res);
        });
    }

    editUser(user: Object) {
        return this.fetch(`${this.domain}/user`, {
            method: "PUT",
            body: JSON.stringify(user)
        }).then((res) => {
            return Promise.resolve(res);
        });
    }

    deleteUser() {
        return this.fetch(`${this.domain}/user`, {
            method: "DELETE",
        }).then((res) => {
            return Promise.resolve(res);
        });
    }

    createAccount(user: Object) {
        return this.fetch(`${this.domain}/user`, {
            method: "POST",
            body: JSON.stringify(user)
        }).then((res) => {
            return Promise.resolve(res);
        });
    }

    forgotPassword(email: string) {
        return this.fetch(`${this.domain}/user/forgottenpassword`, {
            method: "POST",
            body: email
        }).then((res) => {
            return Promise.resolve(res);
        });
    }

    checkTokenPassword(token: string) {
        return this.fetch(`${this.domain}/reset/${token}`, {
            method: "GET"
        }).then((res) => {
            return Promise.resolve(res);
        });
    }

    resetPassword(token: string, password: string) {
        return this.fetch(`${this.domain}/reset`, {
            method: "POST",
            body: JSON.stringify({"token": token, "password": password})
        }).then((res) => {
            return Promise.resolve(res);
        });
    }

    startDocker(id: string) {
        return this.fetch(`${this.domain}/containers/${id}/start`, {
            method: "POST",
        }).then((res) => {
            return Promise.resolve(res);
        });
    }

    stopDocker(id: string) {
        return this.fetch(`${this.domain}/containers/${id}/stop`, {
            method: "POST",
        }).then((res) => {
            return Promise.resolve(res);
        });
    }

    fetch(url: any, options: any) {
        // performs api calls sending the required authentication headers
        const headers = {
            "Accept": "application/json",
            "Content-Type": "application/json"
        };

        // Setting Authorization header
        // Authorization: Bearer xxxxxxx.xxxxxxxx.xxxxxx
        if (this.loggedIn()) {
            headers["Authorization"] = "Bearer " + this.getToken();
        }

        return fetch(url, {
            headers,
            ...options
        })
            .then(this._checkStatus)
            .then((response) => response.json());
    }

    _checkStatus(response: any) {
        // raises an error in case response status is not a success
        if (response.status >= 200 && response.status < 400) { // Success status lies between 200 to 300
            return response;
        } else {
            let error = new Error(response.statusText);
            // error.response = response
            throw error;
        }
    }
}
