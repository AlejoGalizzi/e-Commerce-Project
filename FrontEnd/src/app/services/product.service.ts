import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../common/product';
import { map } from 'rxjs/operators'
import { ProductCategory } from '../common/product-category';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productsUrl = "http://localhost:8080/api/products";
  private categoryUrl = "http://localhost:8080/api/product-category";
  constructor(private httpClient: HttpClient) { }

  getProductsList(categoryId: number): Observable<Product[]> {
    const url = `${this.productsUrl}/search/findByCategoryId?id=${categoryId}`;
     
    return this.getProducts(url);
  }

  searchProductsKeyword(keyword: string): Observable<Product[]> {
    const url = `${this.productsUrl}/search/findByNameContaining?name=${keyword}`;

    return this.getProducts(url);
  }

  getProductById(id: number): Observable<Product> {
    const url = `${this.productsUrl}/${id}`;

    return this.httpClient.get<Product> (url);

  }

  getProductsCategories(): Observable<ProductCategory[]> {
    return this.httpClient.get<GetResponseProductCategory> (this.categoryUrl).pipe(
          map(response => response._embedded.category));
  }

  private getProducts(url: string): Observable<Product[]>{
    return this.httpClient.get<GetResponseProducts> (url).pipe(
      map(response => response._embedded.products)
    );
  }
}

interface GetResponseProducts{
  _embedded: {
    products: Product[];
  }
}

interface GetResponseProductCategory{
  _embedded: {
    category: ProductCategory[];
  }
}

interface GetResponseProduct{
  _embedded: {
    product: Product;
  }
}
