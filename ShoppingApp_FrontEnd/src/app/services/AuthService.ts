import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { apiUrl } from 'src/environments/environment';
import { User } from './User';
import { TokenStorageService } from './token-storage.service';



// const apiUrl = 'http://18.188.121.17:8084/shopping';
const apiUrl = 'http://localhost:8084/shopping';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  constructor(private http: HttpClient,private tokenStorage:TokenStorageService) { }

  login(loginId: string, password: string): Observable<any> {
    // const url = `${apiUrl}/login`;
    const url = apiUrl + '/login';
    return this.http.post(url,{
      "loginId":loginId,
      "password":password
    },httpOptions);
  }


  register(user: User):Observable<any>{
    // const value = '/register';
    const url = apiUrl + '/register';
    return this.http.post(url,{
      "loginId":user.loginId,
      "email":user.email,
      "firstName":user.firstName,
      "lastName":user.lastName,
      "password":user.password,
      "confirmPassword":user.confirmPassword,
      "contactNumber":user.contactNumber,
      "gender":user.gender
    },httpOptions);
  }

  updatepassword(password:string):Observable<any>{
    const token = this.tokenStorage.getToken();
    const user = this.tokenStorage.getUser();
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': token })
    };
    console.log(user.loginId)
    console.log(password)
    // const url = `${apiUrl}/${user.loginId}/updatepassword`;
    const url = apiUrl +'/' + user.loginId+ '/updatepassword';
    return this.http.post(url,{
      "password" : password
    },httpOptions);
  }
}
