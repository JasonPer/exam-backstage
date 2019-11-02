import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ExamBackstageSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ExamBackstageSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ExamBackstageSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ExamBackstageSharedModule {
  static forRoot() {
    return {
      ngModule: ExamBackstageSharedModule
    };
  }
}
