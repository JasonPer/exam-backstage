import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'discount-info',
        loadChildren: () => import('./discount-info/discount-info.module').then(m => m.ExamBackstageDiscountInfoModule)
      },
      {
        path: 'product-info',
        loadChildren: () => import('./product-info/product-info.module').then(m => m.ExamBackstageProductInfoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExamBackstageEntityModule {}
