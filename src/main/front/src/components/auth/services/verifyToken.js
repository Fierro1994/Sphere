import { jwtDecode } from "jwt-decode";
import authService from "./authService";import { Link, useNavigate } from "react-router-dom";
;

function verifyToken (token) {
    if (token) {
        try {
                const decodedJwt = jwtDecode(token);
                if (decodedJwt.exp * 1000 < Date.now()) {
                        authService.refresh();
                } 
                return true   
        } catch (error) {
                console.log(error);
                return false
        }
    }

}

export default verifyToken;