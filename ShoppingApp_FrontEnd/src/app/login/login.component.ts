import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { NavComponent } from '../nav/nav.component';
import { AuthService } from '../services/AuthService';
import { TokenStorageService } from '../services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  model: any = {
    userid: null,
    password: null
  };
  isLogout = false;
  isInvalid = false;
  errorMessage = '';
  returnUrl = '/';

  constructor(private authService:AuthService,
              private router: Router,
              private appcomp :AppComponent,
              private tokenStorage:TokenStorageService) { }

  ngOnInit(): void {

  }
 
  onSubmit(): void {
    const { userid, password } = this.model;
    this.authService.login(userid, password).subscribe(
      user => {
        this.isInvalid = false;
        if (user) {
          if (user.role == "ROLE_ADMIN") {
              this.returnUrl = '/admin';
          }else if(user.role == "ROLE_CUSTOMER"){
              this.returnUrl = '/products';
          }
          this.appcomp.auto_logout()
          this.router.navigateByUrl(this.returnUrl);
      }
        this.tokenStorage.saveToken(user.authToken);
        this.tokenStorage.saveUser(user);
      },
      err => {
        this.isInvalid = true;
        this.errorMessage = err.error.message;
      }
    );
  }


}
