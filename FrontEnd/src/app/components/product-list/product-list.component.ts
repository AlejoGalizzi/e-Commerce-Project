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
  currentKeywordSearched: string;
  currentCategoryId: number;
  currentCategoryName:string;

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
    this.currentKeywordSearched = this.route.snapshot.paramMap.get('keyword')!;
      this.productService.searchProductsKeyword(this.currentKeywordSearched).subscribe(
        data => {
          this.products = data;
        }
      )
  }

  handleListComponent(){
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
    if(hasCategoryId){
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
      this.currentCategoryName = this.route.snapshot.paramMap.get('name')!;
    }else {
      this.currentCategoryId = 1;
      this.currentCategoryName = "Books";
    }
    this.productService.getProductsList(this.currentCategoryId).subscribe(
      data => {
        this.products = data;
      }
    )
  }
}
