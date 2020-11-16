import React from 'react';
import UserService from '../services/UserService';

class UserComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            users:[]
        }
    }

    componentDidMount(){
        UserService.getUsers().then((response) => {
            this.setState({ users: response.data})
        });
    }

    render (){
        return (
            <div>
                <h1 className = "text-center"> Patient Observation</h1>
                <table className = "table table-striped">
                    <thead>
                    <tr>

                        <td> User Id</td>
                        <td> User First Name</td>
                    </tr>

                    </thead>
                    <tbody>
                                <tr key = {this.state.users.firstName}>
                                    <td> {this.state.users.firstName}</td>
                                    <td> {this.state.users.lastName}</td>
                                </tr>

                    </tbody>
                </table>

            </div>

        )
    }
}

export default UserComponent