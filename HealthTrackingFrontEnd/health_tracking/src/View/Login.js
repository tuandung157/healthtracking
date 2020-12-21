import React, {Component} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Container from "@material-ui/core/Container";
import TextField from "@material-ui/core/TextField";

// const root = {
//     minWidth: 275,
//     padding: "2rem"
// };

// const action = {
//     display: "flex",
//     justifyContent:"flex-end",
//     alignItems: "center"
// };

class LoginCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            usersName: "",
            password: ""
        }
        this.handleChange = this.handleChange.bind(this);
        this.login = this.login.bind(this);
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value});
    }

    login = () => {
        fetch("http://localhost:8080/users/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(this.state),
            credentials: "same-origin"
        })
            .then(response => {
                if (response.ok) {
                    return response;
                }
                else {
                    throw new Error(response.status);
                }
            })
            .then(response => response.json())
            .then(response => alert('success'))
            .catch(error => alert('fail'));
    }

    render() {
        return (
            <Card>
                <CardContent>
                    <Typography gutterBottom variant="h5">
                        Login
                    </Typography>
                    <form noValidate autoComplete="off">
                        <TextField id="username" label="Username"
                                   name="usersName"
                                   required fullWidth
                                   onChange={this.handleChange}
                        />
                        <TextField id="password" label="Password"
                                   name="password"
                                   required fullWidth
                                   onChange={this.handleChange}
                        />
                    </form>
                </CardContent>
                <CardActions>
                    <Button variant="outlined" color="primary" onClick={this.login}>
                        Login
                    </Button>
                </CardActions>
            </Card>
        );
    }
}

class LoginPage extends Component {
    render() {
        return (
            <Container maxWidth="sm">
                <LoginCard/>
            </Container>
        );
    }
}

export default LoginPage;