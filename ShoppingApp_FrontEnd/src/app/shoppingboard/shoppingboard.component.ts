import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ProductService } from '../services/productservice';

@Component({
  selector: 'app-shoppingboard',
  templateUrl: './shoppingboard.component.html',
  styleUrls: ['./shoppingboard.component.css']
})
export class ShoppingboardComponent implements OnInit {

  formgroup = new FormGroup({});
   products:any = [];
   errorMessage = '';
   responses:any = [];
   form: any = { 
  }; 
  constructor(private productService:ProductService,private cdref: ChangeDetectorRef) { }

  ngOnInit(): void {
this.onSearchAll();

  }

  onSearchAll(){
    this.productService.allProducts().subscribe(responses =>{
      this.responses = responses;
      for (let index = 0; index < this.responses.length; index++) {
        const element = this.responses[index];
        this.formgroup.addControl(element.productId,new FormControl());
      }
  });
  }

  productsname:any;
  OnSearch(){
    if(this.productsname == ''){
        this.onSearchAll();
    }else {
    this.productService.productbyname(this.productsname).subscribe(responses =>{
      this.responses = responses;
      for (let index = 0; index < this.responses.length; index++) {
        const element = this.responses[index];
        this.formgroup.addControl(element.productId,new FormControl());
      }
  });
 }
  }
  ngAfterContentChecked() {
    this.cdref.detectChanges();
  }

}
