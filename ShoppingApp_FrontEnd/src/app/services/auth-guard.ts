import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';


@Injectable()
export class AuthGuardService implements CanActivate {

    constructor(public token:TokenStorageService, public router: Router) { }

    canActivate(route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
            const user = this.token.getUser();
            console.log(user);
            if (this.token.isUserLoggedIn()) {
                console.log(route.data.role);
                if (route.data.role && user.role == 'ROLE_ADMIN') {
                    console.log(user.role == 'ROLE_ADMIN')
                    
                    return true;
                }else if(!route.data.role){
                    return true;
                }
                this.router.navigate(['products']);
                return false;
            }
            this.router.navigate(['/login']);
            return false;
        }




        // if (!this.token.isUserLoggedIn()) {
        //     this.router.navigate(['login']);
        //     return false;
        // }
        // else if(next.routeConfig?.path =='products' && user.role == 'ROLE_CUSTOMER'){
        //         // console.log(user.role)
        //         // this.router.navigate(['products']);
        //         return true;
        // }else{
        //         // console.log(next.routeConfig?.path);
        //         // console.log(state.url);
        //         // console.log(state.root);
        //         return false;
             
        //     }
        // return true;
       
    }

// }