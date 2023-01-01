import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from './services/token-storage.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'ShoppingApp';


  constructor(private router: Router,
    private tokenStorage:TokenStorageService) { }

  auto_logout(){
    setTimeout(() => {
      this.tokenStorage.signOut();
      window.location.reload();
      this.router.navigate(['/login']);
   },900000);
  }
}
