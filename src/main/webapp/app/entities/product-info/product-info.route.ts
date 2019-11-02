import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductInfo } from 'app/shared/model/product-info.model';
import { ProductInfoService } from './product-info.service';
import { ProductInfoComponent } from './product-info.component';
import { ProductInfoDetailComponent } from './product-info-detail.component';
import { ProductInfoUpdateComponent } from './product-info-update.component';
import { ProductInfoDeletePopupComponent } from './product-info-delete-dialog.component';
import { IProductInfo } from 'app/shared/model/product-info.model';

@Injectable({ providedIn: 'root' })
export class ProductInfoResolve implements Resolve<IProductInfo> {
  constructor(private service: ProductInfoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductInfo> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductInfo>) => response.ok),
        map((productInfo: HttpResponse<ProductInfo>) => productInfo.body)
      );
    }
    return of(new ProductInfo());
  }
}

export const productInfoRoute: Routes = [
  {
    path: '',
    component: ProductInfoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'examBackstageApp.productInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductInfoDetailComponent,
    resolve: {
      productInfo: ProductInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'examBackstageApp.productInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductInfoUpdateComponent,
    resolve: {
      productInfo: ProductInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'examBackstageApp.productInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductInfoUpdateComponent,
    resolve: {
      productInfo: ProductInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'examBackstageApp.productInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productInfoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProductInfoDeletePopupComponent,
    resolve: {
      productInfo: ProductInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'examBackstageApp.productInfo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
