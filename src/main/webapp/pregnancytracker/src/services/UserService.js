import axios from 'axios'

const USERS_REST_API_URL = 'http://localhost:8080/get/patient?id=';

class UserService {

    getDetails(id){
        return axios.get(USERS_REST_API_URL+id);
    }
}

export default new UserService();