import axios from 'axios';

const API_URL = "http://localhost:8080/mma/data"

class FighterDataService {
    retrieveAllFighters(){
        return axios.get(`${API_URL}/fighters`);
    }
    retrieveAllFightersDTO(){
        return axios.get(`${API_URL}/fightersdto`);
    }
    deleteFighter(idFighter){
        return axios.delete(`${API_URL}/deleteFighter/${idFighter}`)
    }
    retrieveFighter(idFighter) {
        return axios.get(`${API_URL}/fighters/${idFighter}`);
    }
    updateFighter(idFighter, fighter) {
        return axios.put(`${API_URL}/fighters/${idFighter}`, fighter);
    }

    createFighter(fighter) {
        return axios.post(`${API_URL}/fighters/`, fighter);
    }

    testFighters(dateTimeStart){
        return axios.put(`${API_URL}/testfighters/${dateTimeStart}`);
    }
}

export default new FighterDataService()