import React from 'react';
import LoginService from "../services/LoginService";
import axios from "axios";
class LoginComponent extends React.Component {

    constructor(props){
        super(props)

        this.state = {
            id: '',
            users: [],
            api:'',
            username: '',
        }
        this.myChangeHandler = this.myChangeHandler.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

    }

    componentDidMount(){
        LoginService.getUsers().then((response) => {
            this.setState({ users: response.data})
        });
    }
    handleSubmit = (e) => {


        alert('the response is '+this.state.id+ this.state.api)

    }

    myChangeHandler = (event) => {
        this.setState({id: event.target.value});
        var USERS_REST_API_URL = 'http://localhost:8080/get/patient?id='+event.target.value;

        axios.get(USERS_REST_API_URL).then((response) =>{
            this.setState({ api:(JSON.stringify(response.data)) })
            console.log('api'+this.state.api)
        }).catch(function (error) {
            console.log(error);
        });
    }
    render(){
        return <div className="drop-down">
            <form onSubmit={this.handleSubmit} >
                <table>   <tbody>
                <tr><td>
            <p>Select User</p></td></tr>
            <tr><td><select id="dropdown-basic-button" value={this.state.value} onChange={this.myChangeHandler}   >
                <option value="" selected disabled hidden>Choose here</option>
                {
                this.state.users.map((obj) => {
                    return <option key={obj.fhirID} value={obj.fhirID}>{obj.name}</option>
                })
            }</select></td></tr>
                <tr><td>
                <input type="submit" value="Submit"/></td></tr>
                <tr><td>                    UID: {this.state.id}</td></tr>
                    <tr><td>     {this.state.api}</td></tr></tbody></table>
            </form>
        </div>;
    }
}
export default LoginComponent