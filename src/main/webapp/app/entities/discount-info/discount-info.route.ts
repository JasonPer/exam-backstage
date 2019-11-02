import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DiscountInfo } from 'app/shared/model/discount-info.model';
import { DiscountInfoService } from './discount-info.service';
import { DiscountInfoComponent } from './discount-info.component';
import { DiscountInfoDetailComponent } from './discount-info-detail.component';
import { DiscountInfoUpdateComponent } from './discount-info-update.component';
import { DiscountInfoDeletePopupComponent } from './discount-info-delete-dialog.component';
import { IDiscountInfo } from 'app/shared/model/discount-info.model';

@Injectable({ providedIn: 'root' })
export class DiscountInfoResolve implements Resolve<IDiscountInfo> {
  constructor(private service: DiscountInfoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDiscountInfo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DiscountInfo>) => response.ok),
        map((discountInfo: HttpResponse<DiscountInfo>) => discountInfo.body)
      );
    }
    return of(new DiscountInfo());
  }
}

export const discountInfoRoute: Routes = [
  {
    path: '',
    component: DiscountInfoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'examBackstageApp.discountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DiscountInfoDetailComponent,
    resolve: {
      discountInfo: DiscountInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'examBackstageApp.discountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DiscountInfoUpdateComponent,
    resolve: {
      discountInfo: DiscountInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'examBackstageApp.discountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DiscountInfoUpdateComponent,
    resolve: {
      discountInfo: DiscountInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'examBackstageApp.discountInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const discountInfoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DiscountInfoDeletePopupComponent,
    resolve: {
      discountInfo: DiscountInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'examBackstageApp.discountInfo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
