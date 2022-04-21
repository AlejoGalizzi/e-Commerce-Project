import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/common/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {

  product: Product = new Product();
  currentProductId:number;

  constructor(private productService: ProductService,private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.getProduct();
    });
  }
  getProduct() {
    this.currentProductId = +this.route.snapshot.paramMap.get('id')!;
    this.productService.getProductById(this.currentProductId).subscribe(
      data => {
        this.product = data;
      }
    )
  }

}
