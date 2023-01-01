import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/services/product';
import { ProductService } from 'src/app/services/productservice';

@Component({
  selector: 'app-addoredit',
  templateUrl: './addoredit.component.html',
  styleUrls: ['./addoredit.component.css']
})
export class AddoreditComponent implements OnInit {
product:Product;
isAdded:boolean = false;
errorMessage = '';
productId!: string ;
isEdit = false;
Edit = false;
  constructor(private productService:ProductService,private route: ActivatedRoute) {
    this.product = new Product();
   }

  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('id');
    console.log(this.productId);
    if (this.productId && this.productId != 'new') {
        this.Edit = true;
        this.productService.productbyid(this.productId).subscribe(prod => {
          this.product = prod;
        });
    }
  }
  onSubmit() {
    console.log(this.product);
    if(this.Edit){
      this.productService.UpdateProduct(this.product).subscribe( u =>{
        this.isEdit = false;
      },
      err => {
        this.isEdit = true;
      }
      )

    }else{
      this.productService.addProducts(this.product).subscribe(u => {
        this.isAdded = false;
      },
      err => {
        this.errorMessage = err.error;
        this.isAdded = true;
      }
      );
    }
    }

}

