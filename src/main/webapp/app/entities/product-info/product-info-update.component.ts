import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductInfo, ProductInfo } from 'app/shared/model/product-info.model';
import { ProductInfoService } from './product-info.service';
import { IDiscountInfo } from 'app/shared/model/discount-info.model';
import { DiscountInfoService } from 'app/entities/discount-info';

@Component({
  selector: 'jhi-product-info-update',
  templateUrl: './product-info-update.component.html'
})
export class ProductInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  discountinfos: IDiscountInfo[];

  editForm = this.fb.group({
    id: [],
    productName: [],
    productType: [],
    productStatus: [],
    originPrice: [],
    discountInfo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productInfoService: ProductInfoService,
    protected discountInfoService: DiscountInfoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productInfo }) => {
      this.updateForm(productInfo);
    });
    this.discountInfoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDiscountInfo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDiscountInfo[]>) => response.body)
      )
      .subscribe((res: IDiscountInfo[]) => (this.discountinfos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(productInfo: IProductInfo) {
    this.editForm.patchValue({
      id: productInfo.id,
      productName: productInfo.productName,
      productType: productInfo.productType,
      productStatus: productInfo.productStatus,
      originPrice: productInfo.originPrice,
      discountInfo: productInfo.discountInfo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const productInfo = this.createFromForm();
    if (productInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.productInfoService.update(productInfo));
    } else {
      this.subscribeToSaveResponse(this.productInfoService.create(productInfo));
    }
  }

  private createFromForm(): IProductInfo {
    return {
      ...new ProductInfo(),
      id: this.editForm.get(['id']).value,
      productName: this.editForm.get(['productName']).value,
      productType: this.editForm.get(['productType']).value,
      productStatus: this.editForm.get(['productStatus']).value,
      originPrice: this.editForm.get(['originPrice']).value,
      discountInfo: this.editForm.get(['discountInfo']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductInfo>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDiscountInfoById(index: number, item: IDiscountInfo) {
    return item.id;
  }
}
