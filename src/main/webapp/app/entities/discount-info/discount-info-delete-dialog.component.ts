import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDiscountInfo } from 'app/shared/model/discount-info.model';
import { DiscountInfoService } from './discount-info.service';

@Component({
  selector: 'jhi-discount-info-delete-dialog',
  templateUrl: './discount-info-delete-dialog.component.html'
})
export class DiscountInfoDeleteDialogComponent {
  discountInfo: IDiscountInfo;

  constructor(
    protected discountInfoService: DiscountInfoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.discountInfoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'discountInfoListModification',
        content: 'Deleted an discountInfo'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-discount-info-delete-popup',
  template: ''
})
export class DiscountInfoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ discountInfo }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DiscountInfoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.discountInfo = discountInfo;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/discount-info', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/discount-info', { outlets: { popup: null } }]);
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
