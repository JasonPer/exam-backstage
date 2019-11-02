import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductInfo } from 'app/shared/model/product-info.model';

@Component({
  selector: 'jhi-product-info-detail',
  templateUrl: './product-info-detail.component.html'
})
export class ProductInfoDetailComponent implements OnInit {
  productInfo: IProductInfo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productInfo }) => {
      this.productInfo = productInfo;
    });
  }

  previousState() {
    window.history.back();
  }
}
