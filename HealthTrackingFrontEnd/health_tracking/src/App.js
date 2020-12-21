import './App.css';
import 'fontsource-roboto';
import LoginPage from "./View/Login";
import makeStyles from "@material-ui/core/styles/makeStyles";
import React from "react";


const useStyles = makeStyles({
    root: {
        width: "100vw",
        height: "100vh",
        display: "flex",
        marginTop: "5rem"
    }
});


function App() {
    const classes = useStyles();
    return (
        <div className={classes.root}>
            <LoginPage/>
        </div>
    );
}

export default App;
