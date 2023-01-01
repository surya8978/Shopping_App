import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService';
import { User } from '../services/User';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  user: User;
  isRegisted:boolean = false;

errorMessage = '';
  constructor(private authService:AuthService,
              private router: Router) { 
                this.user = new User();
              }

  ngOnInit(): void {
  }
  
  onSubmit() {
    console.log(this.user);
    this.authService.register(this.user).subscribe(u => {
      this.isRegisted = true;
    },
    err => {
      this.errorMessage = err.error;
    }
    );
  }
}
