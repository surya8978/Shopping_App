import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/AuthService';

@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html',
  styleUrls: ['./changepassword.component.css']
})
export class ChangepasswordComponent implements OnInit {
  form: any = {
    newpassword: null,
    confirmpassword: null,
  };
  message:string = '';
  isupdated:boolean = false;
  constructor(private router: Router,private authService:AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.authService.updatepassword(this.form.newpassword).subscribe( u =>{
     this.message = u.message;
     this.isupdated = true;
    });
  }
}
