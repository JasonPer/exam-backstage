import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ExamBackstageSharedModule } from 'app/shared';
import {
  DiscountInfoComponent,
  DiscountInfoDetailComponent,
  DiscountInfoUpdateComponent,
  DiscountInfoDeletePopupComponent,
  DiscountInfoDeleteDialogComponent,
  discountInfoRoute,
  discountInfoPopupRoute
} from './';

const ENTITY_STATES = [...discountInfoRoute, ...discountInfoPopupRoute];

@NgModule({
  imports: [ExamBackstageSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DiscountInfoComponent,
    DiscountInfoDetailComponent,
    DiscountInfoUpdateComponent,
    DiscountInfoDeleteDialogComponent,
    DiscountInfoDeletePopupComponent
  ],
  entryComponents: [
    DiscountInfoComponent,
    DiscountInfoUpdateComponent,
    DiscountInfoDeleteDialogComponent,
    DiscountInfoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExamBackstageDiscountInfoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
