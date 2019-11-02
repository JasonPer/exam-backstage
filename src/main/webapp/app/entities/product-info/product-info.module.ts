import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ExamBackstageSharedModule } from 'app/shared';
import {
  ProductInfoComponent,
  ProductInfoDetailComponent,
  ProductInfoUpdateComponent,
  ProductInfoDeletePopupComponent,
  ProductInfoDeleteDialogComponent,
  productInfoRoute,
  productInfoPopupRoute
} from './';

const ENTITY_STATES = [...productInfoRoute, ...productInfoPopupRoute];

@NgModule({
  imports: [ExamBackstageSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductInfoComponent,
    ProductInfoDetailComponent,
    ProductInfoUpdateComponent,
    ProductInfoDeleteDialogComponent,
    ProductInfoDeletePopupComponent
  ],
  entryComponents: [ProductInfoComponent, ProductInfoUpdateComponent, ProductInfoDeleteDialogComponent, ProductInfoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExamBackstageProductInfoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
