import axios from 'axios'

const LOGIN_REST_API_URL = 'http://localhost:8080/loginuser';

class LoginService {

    getUsers(){
        return axios.get(LOGIN_REST_API_URL);
    }
}

export default new LoginService();