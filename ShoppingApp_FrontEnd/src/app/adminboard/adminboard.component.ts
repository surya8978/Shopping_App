import { ChangeDetectorRef, Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Product } from '../services/product';
import { ProductService } from '../services/productservice';

@Component({
  selector: 'app-adminboard',
  templateUrl: './adminboard.component.html',
  styleUrls: ['./adminboard.component.css']
})
export class AdminboardComponent implements OnInit {
  formgroup = new FormGroup({});
   products:any = [];
   errorMessage = '';
   responses:any = [];
   form: any = { 
  }; 
  constructor(private productService:ProductService,private cdref: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.productService.allProducts().subscribe(responses =>{
      this.responses = responses;
      for (let index = 0; index < this.responses.length; index++) {
        const element = this.responses[index];
        console.log("element"+element);
        this.formgroup.addControl(element.productId,new FormControl());
      }
  });

  }

OnDelete(productId:string){
this.productService.deleteProduct(productId).subscribe(d =>{
window.location.reload();
})
}


  ngAfterContentChecked() {
    this.cdref.detectChanges();
  }

}
