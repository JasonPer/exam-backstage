import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDiscountInfo, DiscountInfo } from 'app/shared/model/discount-info.model';
import { DiscountInfoService } from './discount-info.service';

@Component({
  selector: 'jhi-discount-info-update',
  templateUrl: './discount-info-update.component.html'
})
export class DiscountInfoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    discountName: [],
    discountType: [],
    discountValue: [],
    discountStatus: []
  });

  constructor(protected discountInfoService: DiscountInfoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ discountInfo }) => {
      this.updateForm(discountInfo);
    });
  }

  updateForm(discountInfo: IDiscountInfo) {
    this.editForm.patchValue({
      id: discountInfo.id,
      discountName: discountInfo.discountName,
      discountType: discountInfo.discountType,
      discountValue: discountInfo.discountValue,
      discountStatus: discountInfo.discountStatus
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const discountInfo = this.createFromForm();
    if (discountInfo.id !== undefined) {
      this.subscribeToSaveResponse(this.discountInfoService.update(discountInfo));
    } else {
      this.subscribeToSaveResponse(this.discountInfoService.create(discountInfo));
    }
  }

  private createFromForm(): IDiscountInfo {
    return {
      ...new DiscountInfo(),
      id: this.editForm.get(['id']).value,
      discountName: this.editForm.get(['discountName']).value,
      discountType: this.editForm.get(['discountType']).value,
      discountValue: this.editForm.get(['discountValue']).value,
      discountStatus: this.editForm.get(['discountStatus']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDiscountInfo>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
