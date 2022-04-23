import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/common/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[];
  previouseKeywordSearched:string;
  currentCategoryId: number = 1;
  previousCategoryId: number = 1;
  pageNumber: number = 1;
  pageSize: number = 10;
  totalElements: number = 0;

  

  constructor(private productService: ProductService,private route: ActivatedRoute) { }

  ngOnInit(){
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
  }

  listProducts(){
    
    const hasSearchKeyword: boolean = this.route.snapshot.paramMap.has('keyword');
    if(hasSearchKeyword){
      this.handleSearchComponent();
    }else{
      this.handleListComponent();
    }   
  }

  handleSearchComponent(){
    const currentKeywordSearched = this.route.snapshot.paramMap.get('keyword')!;
    if(this.previouseKeywordSearched != currentKeywordSearched){
      this.pageNumber = 1;
    }
    this.previouseKeywordSearched = currentKeywordSearched;
      this.productService.searchProductsKeywordPaginate(this.pageNumber - 1,this.pageSize,currentKeywordSearched).subscribe(
        this.processResult());
  }

  handleListComponent(){
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
    if(hasCategoryId){
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
    }else {
      this.currentCategoryId = 1;
    }

    if(this.currentCategoryId != this.previousCategoryId){
      this.pageNumber = 1
    }
    this.previousCategoryId = this.currentCategoryId;
    this.productService.getProductListPaginate(this.pageNumber - 1,this.pageSize,this.currentCategoryId).subscribe(
      this.processResult());
  }

  private processResult() {
    return (data:any) => {
      this.products = data._embedded.products;
      this.pageNumber = data.page.number+1;
      this.pageSize = data.page.size;
      this.totalElements = data.page.totalElements;
    };
  }

  updateSize(size:number){
    this.pageSize = size;
    this.pageNumber = 1;
    this.listProducts()
  }

  addToCart(product: Product){
    console.log(`Adding to cart: ${product.name} , ${product.unitPrice}`);
    
  }
}
