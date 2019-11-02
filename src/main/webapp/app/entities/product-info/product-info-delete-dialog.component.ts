import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductInfo } from 'app/shared/model/product-info.model';
import { ProductInfoService } from './product-info.service';

@Component({
  selector: 'jhi-product-info-delete-dialog',
  templateUrl: './product-info-delete-dialog.component.html'
})
export class ProductInfoDeleteDialogComponent {
  productInfo: IProductInfo;

  constructor(
    protected productInfoService: ProductInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productInfoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productInfoListModification',
        content: 'Deleted an productInfo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-info-delete-popup',
  template: ''
})
export class ProductInfoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productInfo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productInfo = productInfo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/product-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/product-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
