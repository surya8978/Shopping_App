import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { apiUrl } from 'src/environments/environment';
import { TokenStorageService } from './token-storage.service';
import { Product } from './product';

// const apiUrl = 'http://18.188.121.17:8084/shopping';
const apiUrl = 'http://localhost:8084/shopping';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient,private tokenStorage:TokenStorageService) { }

  allProducts(): Observable<any> {
    // const url = `${apiUrl}/all`;
    const url = apiUrl + '/all';
    const token = this.tokenStorage.getToken();
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': token })
    };
    return this.http.get<[]>(url,httpOptions);
  }

  productbyname(productname:string): Observable<any>{
    // const url = `${apiUrl}/products/search/${productname}`;
    const url = apiUrl + '/products/search/'+ productname;
    const token = this.tokenStorage.getToken();
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': token })
    };
    return this.http.get<[]>(url,httpOptions); 
  }

  productbyid(productid:string): Observable<any> {
    // const url = `${apiUrl}/products/searchone/${productid}`;
    const url = apiUrl + '/products/searchone/'+ productid;
    const token = this.tokenStorage.getToken();
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': token })
    };
    return this.http.get<[]>(url,httpOptions);
  }

  addProducts(product:Product):Observable<any>{
    // const url = `${apiUrl}/product/add`;
    const url = apiUrl + '/product/add';
    const token = this.tokenStorage.getToken();
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': token })
    };
    console.log(product);
    return this.http.post(url,{
      "productId": product.productId,
      "productName":product.productName,
      "productPrice":product.productPrice,
      "productStatus":product.productStatus,
      "productDescription":product.productDescription,
      "productQuantity":product.productQuantity,
      "productFeatures":product.productFeatures
    },httpOptions);
  }

  UpdateProduct(product:Product):Observable<any>{
    // const url = `${apiUrl}/product/update/${product.productId}`;
    const url = apiUrl + '/product/update/'+ product.productId;
    const token = this.tokenStorage.getToken();
    const httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': token })
    };
    console.log(product);
    return this.http.put(url,{
      "productId": product.productId,
      "productName":product.productName,
      "productPrice":product.productPrice,
      "productStatus":product.productStatus,
      "productDescription":product.productDescription,
      "productQuantity":product.productQuantity,
      "productFeatures":product.productFeatures
    },httpOptions);
  }


  deleteProduct(productId:String): Observable<any> {
    // const url = `${apiUrl}/product/delete/${productId}`;
    const url = apiUrl + '/product/delete/'+ productId;
    const token = this.tokenStorage.getToken();
    console.log(token);
    const httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': token })
      };
    return this.http.delete(url,httpOptions);
  }
  

}
