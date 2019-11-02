import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDiscountInfo } from 'app/shared/model/discount-info.model';

@Component({
  selector: 'jhi-discount-info-detail',
  templateUrl: './discount-info-detail.component.html'
})
export class DiscountInfoDetailComponent implements OnInit {
  discountInfo: IDiscountInfo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ discountInfo }) => {
      this.discountInfo = discountInfo;
    });
  }

  previousState() {
    window.history.back();
  }
}
